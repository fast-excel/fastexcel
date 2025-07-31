package cn.idev.excel.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel数值验证注解
 * 用于在Excel模板中生成数值验证规则
 *
 * @author TianYufeng
 * @author FastExcel Team
 * @since 1.2.1
 * @date 2025-07-30
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelNumberValidation {
    
    /**
     * 最小值
     * @return 最小值，默认为0
     */
    double min() default 0.0;
    
    /**
     * 最大值
     * @return 最大值，默认为999999999999.99（万亿级别）
     */
    double max() default 999999999999.99;
    
    /**
     * 小数位数
     * @return 允许的小数位数，默认为2位
     */
    int decimalPlaces() default 2;
    
    /**
     * 数值验证生效的起始行号（从0开始计数）
     * 默认从第1行开始（数据第一行）
     * @return 起始行号
     */
    int firstRow() default 1;
    
    /**
     * 数值验证生效的结束行号
     * 默认到第1000行
     * @return 结束行号
     */
    int lastRow() default 1000;
    
    /**
     * 是否显示错误提示框
     * @return true显示，false不显示
     */
    boolean showErrorBox() default true;
    
    /**
     * 错误提示标题
     * @return 错误提示标题
     */
    String errorTitle() default "数值输入错误";
    
    /**
     * 错误提示内容（可以使用占位符）
     * {min} - 最小值
     * {max} - 最大值
     * {decimal} - 小数位数
     * @return 错误提示内容
     */
    String errorContent() default "请输入{min}到{max}之间的数值，最多{decimal}位小数";
    
    /**
     * 是否显示输入提示框
     * @return true显示，false不显示
     */
    boolean showPromptBox() default true;
    
    /**
     * 输入提示标题
     * @return 输入提示标题
     */
    String promptTitle() default "数值输入";
    
    /**
     * 输入提示内容（可以使用占位符）
     * @return 输入提示内容
     */
    String promptContent() default "请输入{min}到{max}之间的数值，最多{decimal}位小数";
    
    /**
     * 单位说明（用于提示信息）
     * @return 单位说明，如"元"、"万元"等
     */
    String unit() default "元";
    
    /**
     * 是否允许空值
     * @return true允许，false不允许
     */
    boolean allowEmpty() default true;
}
