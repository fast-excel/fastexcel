package cn.idev.excel.test.core.sort;

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
public class SortData {
    private String column5;
    private String column6;

    @ExcelProperty(order = 100)
    private String column4;

    @ExcelProperty(order = 99)
    private String column3;

    @ExcelProperty(value = "column2", index = 1)
    private String column2;

    @ExcelProperty(value = "column1", index = 0)
    private String column1;
}
