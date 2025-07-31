package cn.idev.excel.write.handler;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.validation.ExcelNumberValidation;
import cn.idev.excel.write.metadata.holder.WriteSheetHolder;
import cn.idev.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Excel数值验证处理器
 * 处理带有@ExcelNumberValidation注解的字段，为其生成Excel数值验证
 *
 * @author TianYufeng
 * @author FastExcel Team
 * @since 1.2.1
 * @date 2025-07-30
 */
public class ExcelNumberValidationHandler implements SheetWriteHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(ExcelNumberValidationHandler.class);
    
    private final Class<?> clazz;
    private final Map<Integer, ExcelNumberValidation> validationMap = new HashMap<>();
    
    public ExcelNumberValidationHandler(Class<?> clazz) {
        this.clazz = clazz;
        initValidationMap();
    }
    
    /**
     * 初始化数值验证配置映射
     * 扫描类中的字段，找出带有@ExcelNumberValidation注解的字段及其列位置
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
                // 检查是否有ExcelNumberValidation注解
                ExcelNumberValidation numberValidation = field.getAnnotation(ExcelNumberValidation.class);
                if (numberValidation != null) {
                    validationMap.put(columnIndex, numberValidation);
                    logger.info("发现数值验证字段: {} 在列 {}, 范围: {}-{}, 小数位: {}",
                               field.getName(), columnIndex, numberValidation.min(),
                               numberValidation.max(), numberValidation.decimalPlaces());
                }
                columnIndex++;
            }
        }
        
        logger.info("初始化完成，共发现 {} 个数值验证字段", validationMap.size());
    }
    
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        logger.info("开始处理数值验证，共发现 {} 个数值验证字段", validationMap.size());
        
        if (validationMap.isEmpty()) {
            logger.warn("没有发现需要处理的数值验证字段");
            return;
        }
        
        Sheet sheet = writeSheetHolder.getSheet();
        DataValidationHelper helper = sheet.getDataValidationHelper();
        
        for (Map.Entry<Integer, ExcelNumberValidation> entry : validationMap.entrySet()) {
            try {
                logger.info("正在为列 {} 创建数值验证，范围: {}-{}, 小数位: {}", 
                          entry.getKey(), entry.getValue().min(), entry.getValue().max(), 
                          entry.getValue().decimalPlaces());
                createNumberValidation(sheet, helper, entry.getKey(), entry.getValue());
            } catch (Exception e) {
                logger.error("创建数值验证失败，列索引: {}, 错误: {}", entry.getKey(), e.getMessage(), e);
            }
        }
        
        logger.info("数值验证处理完成，共处理 {} 个字段", validationMap.size());
    }
    
    /**
     * 为指定列创建数值验证
     */
    private void createNumberValidation(Sheet sheet, DataValidationHelper helper,
                                      int columnIndex, ExcelNumberValidation validation) {
        try {
            // 创建数值约束 - 使用POI兼容的API
            DataValidationConstraint constraint = helper.createNumericConstraint(
                DataValidationConstraint.ValidationType.DECIMAL,
                DataValidationConstraint.OperatorType.BETWEEN,
                String.valueOf(validation.min()),
                String.valueOf(validation.max())
            );

            // 设置数值验证的单元格范围
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

            // 设置是否允许空值
            dataValidation.setEmptyCellAllowed(validation.allowEmpty());

            // 应用验证到工作表
            sheet.addValidationData(dataValidation);

            logger.debug("已为列 {} 创建数值验证，范围: {}-{}, 小数位: {}",
                       columnIndex, validation.min(), validation.max(), validation.decimalPlaces());

        } catch (Exception e) {
            logger.error("创建数值验证失败，列索引: {}, 错误: {}", columnIndex, e.getMessage(), e);
        }
    }
    
    /**
     * 格式化消息，替换占位符
     */
    private String formatMessage(String message, ExcelNumberValidation validation) {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(validation.decimalPlaces());
        df.setMinimumFractionDigits(0);
        
        return message
            .replace("{min}", df.format(validation.min()))
            .replace("{max}", df.format(validation.max()))
            .replace("{decimal}", String.valueOf(validation.decimalPlaces()))
            .replace("{unit}", validation.unit());
    }
}
