package cn.idev.excel.parameter;

import cn.idev.excel.annotation.ExcelProperty;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class ParameterData {
    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("日期")
    private Date date;
}
