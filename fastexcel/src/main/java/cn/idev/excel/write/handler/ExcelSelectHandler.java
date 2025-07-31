package cn.idev.excel.write.handler;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.validation.ExcelSelect;
import cn.idev.excel.write.handler.SheetWriteHandler;
import cn.idev.excel.write.metadata.holder.WriteSheetHolder;
import cn.idev.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excel下拉框处理器
 * 处理带有@ExcelSelect注解的字段，为其生成Excel下拉验证
 *
 * @author TianYufeng
 * @author FastExcel Team
 * @since 1.2.1
 * @date 2025-07-30
 */
public class ExcelSelectHandler implements SheetWriteHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(ExcelSelectHandler.class);
    
    private final Class<?> clazz;
    private final Map<Integer, ExcelSelect> selectMap = new HashMap<>();
    
    public ExcelSelectHandler(Class<?> clazz) {
        this.clazz = clazz;
        initSelectMap();
    }
    
    /**
     * 初始化下拉框配置映射
     * 扫描类中的字段，找出带有@ExcelSelect注解的字段及其列位置
     */
    private void initSelectMap() {
        Field[] fields = clazz.getDeclaredFields();
        int columnIndex = 0;

        for (Field field : fields) {
            // 检查字段是否被@ExcelIgnore标记，如果是则跳过
            ExcelIgnore excelIgnore = field.getAnnotation(ExcelIgnore.class);
            if (excelIgnore != null) {
                logger.debug("跳过被@ExcelIgnore标记的字段: {}", field.getName());
                continue;
            }

            // 检查字段是否有ExcelProperty注解（确定是Excel列）
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (excelProperty != null) {
                // 检查是否有ExcelSelect注解
                ExcelSelect excelSelect = field.getAnnotation(ExcelSelect.class);
                if (excelSelect != null && excelSelect.value().length > 0) {
                    selectMap.put(columnIndex, excelSelect);
                    logger.info("发现下拉框字段: {} 在列 {}, 选项: [{}]",
                               field.getName(), columnIndex, String.join(", ", excelSelect.value()));
                }
                columnIndex++;
            }
        }

        logger.info("初始化完成，共发现 {} 个下拉框字段", selectMap.size());
        if (selectMap.isEmpty()) {
            logger.warn("没有发现任何带@ExcelSelect注解的字段，请检查注解配置");
        }
    }
    
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        logger.info("开始处理下拉框，共发现 {} 个下拉框字段", selectMap.size());

        if (selectMap.isEmpty()) {
            logger.warn("没有发现需要处理的下拉框字段");
            return;
        }

        Sheet sheet = writeSheetHolder.getSheet();
        DataValidationHelper helper = sheet.getDataValidationHelper();

        for (Map.Entry<Integer, ExcelSelect> entry : selectMap.entrySet()) {
            try {
                logger.info("正在为列 {} 创建下拉框，选项: {}",
                          entry.getKey(), String.join(",", entry.getValue().value()));
                createDropdownValidation(sheet, helper, entry.getKey(), entry.getValue());
            } catch (Exception e) {
                logger.error("创建下拉框失败，列索引: {}, 错误: {}", entry.getKey(), e.getMessage(), e);
            }
        }

        logger.info("下拉框处理完成，共处理 {} 个字段", selectMap.size());
    }
    
    /**
     * 为指定列创建下拉框验证
     */
    private void createDropdownValidation(Sheet sheet, DataValidationHelper helper,
                                        int columnIndex, ExcelSelect excelSelect) {
        try {
            // 创建下拉框约束
            DataValidationConstraint constraint = helper.createExplicitListConstraint(excelSelect.value());

            // 动态计算数据起始行（适应标题行）
            int dataStartRow = calculateDataStartRow(sheet);
            int endRow = Math.max(excelSelect.lastRow(), dataStartRow + 1000); // 确保有足够的行数

            // 设置下拉框的单元格范围
            CellRangeAddressList regions = new CellRangeAddressList(
                dataStartRow,
                endRow,
                columnIndex,
                columnIndex
            );

            // 创建数据验证
            DataValidation dataValidation = helper.createValidation(constraint, regions);

            // 配置验证属性 - 使用gcsj-service的成功实现
            dataValidation.setShowErrorBox(excelSelect.showErrorBox());
            if (excelSelect.showErrorBox()) {
                dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
                dataValidation.createErrorBox(excelSelect.errorTitle(), excelSelect.errorContent());
            }

            dataValidation.setShowPromptBox(true);
            dataValidation.createPromptBox("提示", "请从下拉列表中选择");

            // 应用验证到工作表
            sheet.addValidationData(dataValidation);

            logger.info("✅ 下拉框创建成功 - 列: {}, 行范围: {}-{}, 选项: [{}]",
                       columnIndex, dataStartRow, endRow,
                       String.join(", ", excelSelect.value()));

            logger.debug("已为列 {} 创建下拉框，选项数量: {}",
                       columnIndex, excelSelect.value().length);

        } catch (Exception e) {
            logger.error("创建下拉框失败，列索引: {}, 错误: {}", columnIndex, e.getMessage(), e);
        }
    }

    /**
     * 动态计算数据起始行（适应标题行）
     */
    private int calculateDataStartRow(Sheet sheet) {
        // 检查工作表的行数和内容
        int lastRowNum = sheet.getLastRowNum();

        if (lastRowNum == 0) {
            // 只有一行，可能是表头
            return 1;
        }

        // 检查第一行是否为合并单元格（通常是标题）
        Row firstRow = sheet.getRow(0);
        if (firstRow != null) {
            // 检查是否有合并区域
            int mergedRegions = sheet.getNumMergedRegions();
            if (mergedRegions > 0) {
                // 有合并区域，可能有标题，数据从第2行开始
                logger.info("检测到合并区域，数据起始行设为第2行");
                return 2;
            }
        }

        // 默认情况：数据从第1行开始（紧跟表头）
        return 1;
    }
}
