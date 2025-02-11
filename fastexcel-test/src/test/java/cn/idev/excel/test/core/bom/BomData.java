package cn.idev.excel.test.core.bom;

import cn.idev.excel.annotation.ExcelProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class BomData {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("年纪")
    private Long age;
}
