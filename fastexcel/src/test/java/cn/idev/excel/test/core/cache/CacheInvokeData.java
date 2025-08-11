package cn.idev.excel.test.core.cache;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
@EqualsAndHashCode
public class CacheInvokeData {
    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("年龄")
    private Long age;
}
