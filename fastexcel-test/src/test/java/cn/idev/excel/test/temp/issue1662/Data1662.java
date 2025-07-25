package cn.idev.excel.test.temp.issue1662;

import cn.idev.excel.annotation.ExcelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Data1662 {
    @ExcelProperty(index = 0)
    private String str;

    @ExcelProperty(index = 1)
    private Date date;

    @ExcelProperty(index = 2)
    private double r;
}
