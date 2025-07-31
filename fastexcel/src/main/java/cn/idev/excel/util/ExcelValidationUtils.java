package cn.idev.excel.util;

import cn.idev.excel.annotation.validation.ExcelRequired;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.ExcelIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel验证工具类
 * 提供Excel数据验证的通用方法
 *
 * @author TianYufeng
 * @author FastExcel Team
 * @since 1.2.1
 * @date 2025-07-30
 */
public class ExcelValidationUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(ExcelValidationUtils.class);
    
    /**
     * 验证对象的必填字段
     * 
     * @param obj 要验证的对象
     * @return 验证结果列表，如果为空则表示验证通过
     */
    public static List<String> validateRequired(Object obj) {
        List<String> errors = new ArrayList<>();
        
        if (obj == null) {
            errors.add("验证对象不能为空");
            return errors;
        }
        
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        
        for (Field field : fields) {
            // 跳过被@ExcelIgnore标记的字段
            if (field.isAnnotationPresent(ExcelIgnore.class)) {
                continue;
            }
            
            // 检查是否有@ExcelProperty注解（确定是Excel列）
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (excelProperty == null) {
                continue;
            }
            
            // 检查是否有@ExcelRequired注解
            ExcelRequired excelRequired = field.getAnnotation(ExcelRequired.class);
            if (excelRequired == null) {
                continue;
            }
            
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                
                // 检查字段值是否为空
                if (isEmpty(value)) {
                    String fieldName = getFieldDisplayName(field, excelProperty);
                    String errorMessage = String.format("[%s] %s", fieldName, excelRequired.message());
                    errors.add(errorMessage);
                    logger.debug("必填字段验证失败: {}", errorMessage);
                }
                
            } catch (IllegalAccessException e) {
                logger.error("访问字段失败: {}", field.getName(), e);
                errors.add(String.format("字段 %s 访问失败", field.getName()));
            }
        }
        
        return errors;
    }
    
    /**
     * 批量验证对象列表的必填字段
     * 
     * @param objList 要验证的对象列表
     * @return 验证结果映射，key为行号（从0开始），value为错误信息列表
     */
    public static List<ValidationResult> validateRequiredBatch(List<?> objList) {
        List<ValidationResult> results = new ArrayList<>();
        
        if (objList == null || objList.isEmpty()) {
            return results;
        }
        
        for (int i = 0; i < objList.size(); i++) {
            Object obj = objList.get(i);
            List<String> errors = validateRequired(obj);
            
            if (!errors.isEmpty()) {
                ValidationResult result = new ValidationResult();
                result.setRowIndex(i);
                result.setErrors(errors);
                results.add(result);
            }
        }
        
        return results;
    }
    
    /**
     * 判断值是否为空
     */
    private static boolean isEmpty(Object value) {
        if (value == null) {
            return true;
        }
        
        if (value instanceof String) {
            return ((String) value).trim().isEmpty();
        }
        
        if (value instanceof Number) {
            return false; // 数字类型不为空
        }
        
        return false;
    }
    
    /**
     * 获取字段的显示名称
     */
    private static String getFieldDisplayName(Field field, ExcelProperty excelProperty) {
        String[] values = excelProperty.value();
        if (values.length > 0 && !values[0].isEmpty()) {
            // 取最后一个值作为显示名称
            return values[values.length - 1];
        }
        return field.getName();
    }
    
    /**
     * 验证结果类
     */
    public static class ValidationResult {
        private int rowIndex;
        private List<String> errors;
        
        public int getRowIndex() {
            return rowIndex;
        }
        
        public void setRowIndex(int rowIndex) {
            this.rowIndex = rowIndex;
        }
        
        public List<String> getErrors() {
            return errors;
        }
        
        public void setErrors(List<String> errors) {
            this.errors = errors;
        }
        
        @Override
        public String toString() {
            return String.format("第%d行: %s", rowIndex + 1, String.join(", ", errors));
        }
    }
}
