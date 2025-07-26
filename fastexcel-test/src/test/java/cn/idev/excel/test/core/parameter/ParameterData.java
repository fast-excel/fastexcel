package cn.idev.excel.test.core.parameter;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 */
@Getter
@Setter
@EqualsAndHashCode
public class ParameterData {
    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("日期")
    private Date date;
}
