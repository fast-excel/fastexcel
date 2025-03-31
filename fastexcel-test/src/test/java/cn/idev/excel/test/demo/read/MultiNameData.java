package cn.idev.excel.test.demo.read;

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
public class MultiNameData {
    @ExcelProperty("日期标题,日期标题2")
    private Date date;

    @ExcelProperty("数学标题2,数学标题,数学标题3")
    private String doubleData;

    @ExcelProperty("字符串标题2")
    private String title;

   /* @ExcelProperty(index = 1)
    private Date date;
    *//**
     * 强制读取第三个 这里不建议 index 和 name 同时用，要么一个对象只用index，要么一个对象只用name去匹配
     *//*
    @ExcelProperty(index = 2)
    private String doubleData;
    *//**
     * 用名字去匹配，这里需要注意，如果名字重复，会导致只有一个字段读取到数据
     *//*
    @ExcelProperty("字符串标题2")
    private String title;*/

}
