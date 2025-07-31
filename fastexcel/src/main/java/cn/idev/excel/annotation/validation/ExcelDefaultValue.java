package cn.idev.excel.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excel默认值注解
 * 用于为Excel模板字段设置默认值并可选择锁定单元格
 *
 * @author TianYufeng
 * @author FastExcel Team
 * @since 1.2.1
 * @date 2025-07-30
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelDefaultValue {
    
    /**
     * 默认值
     * 支持固定值和动态值：
     * - 固定值：直接填写字符串，如 "默认值"
     * - 动态值：使用占位符，如 "${currentUser.orgName}" 表示当前用户的单位名称
     * @return 默认值
     */
    String value();
    

    
    /**
     * 默认值生效的起始行号（从0开始计数）
     * 默认从第1行开始（跳过表头）
     * @return 起始行号
     */
    int startRow() default 1;

    /**
     * 默认值生效的结束行号
     * 默认到第5行（适合小量示例数据）
     * @return 结束行号
     */
    int endRow() default 5;
    
    /**
     * 提示信息
     * @return 提示信息
     */
    String message() default "此字段已设置默认值";
}
