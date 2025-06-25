package cn.idev.excel.test.core.cache;

import cn.idev.excel.annotation.ExcelProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class CacheData {
    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("年龄")
    private Long age;
}
