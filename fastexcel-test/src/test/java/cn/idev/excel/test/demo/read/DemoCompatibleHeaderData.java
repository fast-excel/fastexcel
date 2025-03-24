package cn.idev.excel.test.demo.read;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

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
