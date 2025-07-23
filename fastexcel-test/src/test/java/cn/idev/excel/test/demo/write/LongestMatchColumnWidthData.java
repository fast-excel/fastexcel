package cn.idev.excel.test.demo.write;

import cn.idev.excel.annotation.ExcelProperty;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 基础数据类
 *
 *
 **/
@Getter
@Setter
@EqualsAndHashCode
public class LongestMatchColumnWidthData {
    @ExcelProperty("字符串标题")
    private String string;

    @ExcelProperty("日期标题很长日期标题很长日期标题很长很长")
    private Date date;

    @ExcelProperty("数字")
    private Double doubleData;
}
