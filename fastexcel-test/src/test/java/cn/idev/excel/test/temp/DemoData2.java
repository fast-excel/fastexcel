package cn.idev.excel.test.temp;

import cn.idev.excel.annotation.ExcelIgnore;
import cn.idev.excel.annotation.ExcelProperty;
import java.math.BigDecimal;
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
public class DemoData2 {
    @ExcelProperty("字符串标题")
    private String string;

    @ExcelProperty("日期标题")
    private Date date;

    @ExcelProperty("数字标题")
    private Double doubleData;

    @ExcelProperty("数字标题2")
    private BigDecimal bigDecimal;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}
