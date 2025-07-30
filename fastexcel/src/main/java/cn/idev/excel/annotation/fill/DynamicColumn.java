package cn.idev.excel.annotation.fill;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DynamicColumn {

}
