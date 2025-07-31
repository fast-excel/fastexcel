package cn.idev.excel.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel必填字段注解
 * 用于标记Excel模板中的必填字段
 *
 * @author TianYufeng
 * @author FastExcel Team
 * @since 1.2.1
 * @date 2025-07-30
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelRequired {
    
    /**
     * 错误提示信息
     * @return 错误提示信息
     */
    String message() default "此字段为必填项，不能为空";
    
    /**
     * 是否显示错误提示框
     * @return true显示，false不显示
     */
    boolean showErrorBox() default true;
    
    /**
     * 错误提示标题
     * @return 错误提示标题
     */
    String errorTitle() default "必填字段验证";
}
