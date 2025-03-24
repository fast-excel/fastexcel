package cn.idev.excel.test.temp.csv;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * TODO
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class CsvData {
    
    @ExcelProperty("字符串标题")
    private String string;
    
    @ExcelProperty("日期标题")
    private Date date;
    
    @ExcelProperty("数字标题")
    private Double doubleData;
    
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}
