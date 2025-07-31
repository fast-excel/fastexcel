package cn.idev.excel.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel下拉框注解
 * 用于在Excel模板中生成下拉选择框
 *
 * @author TianYufeng
 * @author FastExcel Team
 * @since 1.2.1
 * @date 2025-07-30
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelSelect {
    
    /**
     * 下拉框选项值
     * @return 选项数组
     */
    String[] value() default {};
    
    /**
     * 下拉框生效的起始行号（从0开始计数）
     * 默认从第1行开始（数据第一行）
     * @return 起始行号
     */
    int firstRow() default 1;
    
    /**
     * 下拉框生效的结束行号
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
    String errorTitle() default "输入错误";
    
    /**
     * 错误提示内容
     * @return 错误提示内容
     */
    String errorContent() default "请从下拉列表中选择有效选项";
}
