package cn.idev.excel.write.handler;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.validation.ExcelDefaultValue;
import cn.idev.excel.write.handler.SheetWriteHandler;
import cn.idev.excel.write.handler.WorkbookWriteHandler;
import cn.idev.excel.write.metadata.holder.WriteSheetHolder;
import cn.idev.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Excel默认值处理器
 * 处理带有@ExcelDefaultValue注解的字段，为其设置默认值并可选择锁定单元格
 *
 * @author TianYufeng
 * @author FastExcel Team
 * @since 1.2.1
 * @date 2025-07-30
 */
public class ExcelDefaultValueHandler implements SheetWriteHandler, WorkbookWriteHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(ExcelDefaultValueHandler.class);
    
    private final Class<?> clazz;
    private final Map<Integer, ExcelDefaultValue> defaultValueMap = new HashMap<>();
    
    public ExcelDefaultValueHandler(Class<?> clazz) {
        this.clazz = clazz;
        initDefaultValueMap();
    }
    
    /**
     * 初始化默认值配置映射
     * 扫描类中的字段，找出带有@ExcelDefaultValue注解的字段及其列位置
     */
    private void initDefaultValueMap() {
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
                // 检查是否有ExcelDefaultValue注解
                ExcelDefaultValue defaultValue = field.getAnnotation(ExcelDefaultValue.class);
                if (defaultValue != null) {
                    defaultValueMap.put(columnIndex, defaultValue);
                    logger.info("发现默认值字段: {} 在列 {}, 默认值: {}",
                               field.getName(), columnIndex, defaultValue.value());
                }
                columnIndex++;
            }
        }
        
        logger.info("初始化完成，共发现 {} 个默认值字段", defaultValueMap.size());
    }
    
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        // 延迟处理，等待数据写入完成后再决定是否填充默认值
    }

    @Override
    public void afterWorkbookDispose(WriteWorkbookHolder writeWorkbookHolder) {
        // 在工作簿完成后处理默认值
        processDefaultValues(writeWorkbookHolder);
    }

    private void processDefaultValues(WriteWorkbookHolder writeWorkbookHolder) {
        logger.info("开始处理默认值，共发现 {} 个默认值字段", defaultValueMap.size());

        if (defaultValueMap.isEmpty()) {
            logger.warn("没有发现需要处理的默认值字段");
            return;
        }

        // 获取第一个工作表（假设只有一个工作表）
        Sheet sheet = writeWorkbookHolder.getWorkbook().getSheetAt(0);

        // 检查是否有数据行（除了表头）
        boolean hasDataRows = sheet.getLastRowNum() > 0;

        if (hasDataRows) {
            logger.info("检测到有数据行，跳过默认值填充");
            return;
        }

        logger.info("检测到空模板，开始填充默认值");

        for (Map.Entry<Integer, ExcelDefaultValue> entry : defaultValueMap.entrySet()) {
            try {
                logger.info("正在为列 {} 设置默认值: {}", entry.getKey(), entry.getValue().value());
                setDefaultValue(sheet, entry.getKey(), entry.getValue());
            } catch (Exception e) {
                logger.error("设置默认值失败，列索引: {}, 错误: {}", entry.getKey(), e.getMessage(), e);
            }
        }

        logger.info("默认值处理完成，共处理 {} 个字段", defaultValueMap.size());
    }
    
    /**
     * 为指定列设置默认值
     */
    private void setDefaultValue(Sheet sheet, int columnIndex, ExcelDefaultValue defaultValue) {
        try {
            String value = processDefaultValue(defaultValue.value());

            // 为指定行范围设置默认值
            for (int rowIndex = defaultValue.startRow(); rowIndex <= defaultValue.endRow(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    row = sheet.createRow(rowIndex);
                }

                Cell cell = row.getCell(columnIndex);
                if (cell == null) {
                    cell = row.createCell(columnIndex);
                }

                // 设置默认值
                cell.setCellValue(value);
            }

            logger.debug("已为列 {} 设置默认值: {}, 行范围: {}-{}",
                       columnIndex, value, defaultValue.startRow(), defaultValue.endRow());

        } catch (Exception e) {
            logger.error("设置默认值失败，列索引: {}, 错误: {}", columnIndex, e.getMessage(), e);
        }
    }
    
    /**
     * 处理默认值，支持简单的占位符替换
     * 目前支持固定值，后续可扩展支持动态值
     */
    private String processDefaultValue(String value) {
        // 目前只支持固定值，后续可以扩展支持 ${currentUser.orgName} 等动态值
        if (value.startsWith("${") && value.endsWith("}")) {
            // 动态值处理逻辑，暂时返回占位符本身
            logger.warn("动态默认值暂未实现，返回原值: {}", value);
            return value;
        }
        
        return value;
    }
    

}
