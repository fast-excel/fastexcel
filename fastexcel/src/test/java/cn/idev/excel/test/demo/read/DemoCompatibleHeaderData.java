package cn.idev.excel.test.demo.read;

import cn.idev.excel.annotation.ExcelProperty;
import java.util.Date;
import lombok.Data;

/**
 * Compatible header data class.
 */
@Data
public class DemoCompatibleHeaderData {

    @ExcelProperty("String")
    private String string;

    @ExcelProperty("Date")
    private Date date;

    @ExcelProperty("DoubleData")
    private Double doubleData;
}
