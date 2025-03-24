package cn.idev.excel.test.demo.write;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Basic data class
 *
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
public class DemoData {
    
    /**
     * String Title
     */
    @ExcelProperty("String Title")
    private String string;
    
    /**
     * Date Title
     */
    @ExcelProperty("Date Title")
    private Date date;
    
    /**
     * Number Title
     */
    @ExcelProperty("Number Title")
    private Double doubleData;
    
    /**
     * Ignore this field
     */
    @ExcelIgnore
    private String ignore;
}
