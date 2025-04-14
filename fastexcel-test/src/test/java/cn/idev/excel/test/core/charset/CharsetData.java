package cn.idev.excel.test.core.charset;

import cn.idev.excel.annotation.ExcelProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class CharsetData {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("年纪")
    private Integer age;
}
