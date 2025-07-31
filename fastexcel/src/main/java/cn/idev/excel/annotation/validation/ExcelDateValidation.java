package cn.idev.excel.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel日期验证注解
 * 用于在Excel模板中生成日期验证规则
 *
 * @author TianYufeng
 * @author FastExcel Team
 * @since 1.2.1
 * @date 2025-07-30
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelDateValidation {
    
    /**
     * 日期格式
     * @return 日期格式，默认为yyyy-MM-dd
     */
    String format() default "yyyy-MM-dd";
    
    /**
     * 最小日期（格式：yyyy-MM-dd）
     * @return 最小日期，默认为1900-01-01
     */
    String minDate() default "1900-01-01";
    
    /**
     * 最大日期（格式：yyyy-MM-dd）
     * @return 最大日期，默认为2099-12-31
     */
    String maxDate() default "2099-12-31";
    
    /**
     * 日期验证生效的起始行号（从0开始计数）
     * 默认从第1行开始（数据第一行）
     * @return 起始行号
     */
    int firstRow() default 1;
    
    /**
     * 日期验证生效的结束行号
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
    String errorTitle() default "日期输入错误";
    
    /**
     * 错误提示内容（可以使用占位符）
     * {format} - 日期格式
     * {minDate} - 最小日期
     * {maxDate} - 最大日期
     * @return 错误提示内容
     */
    String errorContent() default "请输入{format}格式的日期，范围：{minDate}到{maxDate}";
    
    /**
     * 是否显示输入提示框
     * @return true显示，false不显示
     */
    boolean showPromptBox() default true;
    
    /**
     * 输入提示标题
     * @return 输入提示标题
     */
    String promptTitle() default "日期输入";
    
    /**
     * 输入提示内容（可以使用占位符）
     * @return 输入提示内容
     */
    String promptContent() default "请输入{format}格式的日期，范围：{minDate}到{maxDate}";
}
