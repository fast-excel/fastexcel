package cn.idev.excel.write.handler;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.validation.ExcelDateValidation;
import cn.idev.excel.write.metadata.holder.WriteSheetHolder;
import cn.idev.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Excel日期验证处理器
 * 处理带有@ExcelDateValidation注解的字段，为其生成Excel日期验证
 *
 * @author TianYufeng
 * @author FastExcel Team
 * @since 1.2.1
 * @date 2025-07-30
 */
public class ExcelDateValidationHandler implements SheetWriteHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(ExcelDateValidationHandler.class);
    
    private final Class<?> clazz;
    private final Map<Integer, ExcelDateValidation> validationMap = new HashMap<>();
    
    public ExcelDateValidationHandler(Class<?> clazz) {
        this.clazz = clazz;
        initValidationMap();
    }
    
    /**
     * 初始化日期验证配置映射
     * 扫描类中的字段，找出带有@ExcelDateValidation注解的字段及其列位置
     */
    private void initValidationMap() {
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
                // 检查是否有ExcelDateValidation注解
                ExcelDateValidation dateValidation = field.getAnnotation(ExcelDateValidation.class);
                if (dateValidation != null) {
                    validationMap.put(columnIndex, dateValidation);
                    logger.info("发现日期验证字段: {} 在列 {}, 格式: {}, 范围: {}-{}",
                               field.getName(), columnIndex, dateValidation.format(),
                               dateValidation.minDate(), dateValidation.maxDate());
                }
                columnIndex++;
            }
        }
        
        logger.info("初始化完成，共发现 {} 个日期验证字段", validationMap.size());
    }
    
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        logger.info("开始处理日期验证，共发现 {} 个日期验证字段", validationMap.size());
        
        if (validationMap.isEmpty()) {
            logger.warn("没有发现需要处理的日期验证字段");
            return;
        }
        
        Sheet sheet = writeSheetHolder.getSheet();
        DataValidationHelper helper = sheet.getDataValidationHelper();
        
        for (Map.Entry<Integer, ExcelDateValidation> entry : validationMap.entrySet()) {
            try {
                logger.info("正在为列 {} 创建日期验证，格式: {}, 范围: {}-{}", 
                          entry.getKey(), entry.getValue().format(), 
                          entry.getValue().minDate(), entry.getValue().maxDate());
                createDateValidation(sheet, helper, entry.getKey(), entry.getValue());
            } catch (Exception e) {
                logger.error("创建日期验证失败，列索引: {}, 错误: {}", entry.getKey(), e.getMessage(), e);
            }
        }
        
        logger.info("日期验证处理完成，共处理 {} 个字段", validationMap.size());
    }
    
    /**
     * 为指定列创建日期验证
     */
    private void createDateValidation(Sheet sheet, DataValidationHelper helper,
                                    int columnIndex, ExcelDateValidation validation) {
        try {
            // 解析日期范围
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date minDate = sdf.parse(validation.minDate());
            Date maxDate = sdf.parse(validation.maxDate());
            
            // 创建日期约束
            DataValidationConstraint constraint = helper.createDateConstraint(
                DataValidationConstraint.OperatorType.BETWEEN,
                sdf.format(minDate),
                sdf.format(maxDate),
                validation.format()
            );

            // 设置日期验证的单元格范围
            CellRangeAddressList regions = new CellRangeAddressList(
                validation.firstRow(),
                validation.lastRow(),
                columnIndex,
                columnIndex
            );

            // 创建数据验证
            DataValidation dataValidation = helper.createValidation(constraint, regions);

            // 配置验证属性
            dataValidation.setShowErrorBox(validation.showErrorBox());
            if (validation.showErrorBox()) {
                dataValidation.setErrorStyle(DataValidation.ErrorStyle.STOP);
                String errorContent = formatMessage(validation.errorContent(), validation);
                dataValidation.createErrorBox(validation.errorTitle(), errorContent);
            }

            // 配置输入提示
            dataValidation.setShowPromptBox(validation.showPromptBox());
            if (validation.showPromptBox()) {
                String promptContent = formatMessage(validation.promptContent(), validation);
                dataValidation.createPromptBox(validation.promptTitle(), promptContent);
            }

            // 应用验证到工作表
            sheet.addValidationData(dataValidation);

            logger.debug("已为列 {} 创建日期验证，格式: {}, 范围: {}-{}",
                       columnIndex, validation.format(), validation.minDate(), validation.maxDate());

        } catch (ParseException e) {
            logger.error("日期格式解析失败，列索引: {}, 错误: {}", columnIndex, e.getMessage(), e);
        } catch (Exception e) {
            logger.error("创建日期验证失败，列索引: {}, 错误: {}", columnIndex, e.getMessage(), e);
        }
    }
    
    /**
     * 格式化消息，替换占位符
     */
    private String formatMessage(String message, ExcelDateValidation validation) {
        return message
            .replace("{format}", validation.format())
            .replace("{minDate}", validation.minDate())
            .replace("{maxDate}", validation.maxDate());
    }
}
