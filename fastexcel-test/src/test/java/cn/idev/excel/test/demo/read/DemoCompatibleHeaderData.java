package cn.idev.excel.test.demo.read;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 多种表头上传支持的测试实体类
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
