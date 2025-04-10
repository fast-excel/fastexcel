package cn.idev.excel.test.demo.read;

import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.format.DateTimeFormat;
import cn.idev.excel.annotation.format.NumberFormat;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Basic data class. The order here is consistent with the order in the Excel file.
 *
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
public class ConverterData {

    /**
     * I use a custom converter. No matter what is passed from the database, I prepend "Custom:".
     */
    @ExcelProperty(converter = CustomStringStringConverter.class)
    private String string;

    /**
     * I use a string to receive the date so that it can be formatted. I want to receive the date in the format of yyyy-MM-dd HH:mm:ss.
     */
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private String date;

    /**
     * I want to receive a number in percentage format.
     */
    @NumberFormat("#.##%")
    private String doubleData;
}
