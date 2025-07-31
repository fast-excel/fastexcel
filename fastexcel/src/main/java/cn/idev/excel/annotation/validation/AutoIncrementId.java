package cn.idev.excel.annotation.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自动递增ID注解
 * 用于在Excel写入时自动生成递增序号
 *
 * @author TianYufeng
 * @author FastExcel Team
 * @since 1.2.1
 * @date 2025-07-30
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoIncrementId {
    /**
     * 序号起始值（默认为1）
     * @return 起始值
     */
    int start() default 1;
}
