package cn.idev.excel.test.core.fill;

import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.format.NumberFormat;
import cn.idev.excel.converters.doubleconverter.DoubleStringConverter;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class FillData {
    private String name;
    @NumberFormat("#")
    @ExcelProperty(converter = DoubleStringConverter.class)
    private Double number;
    private String empty;
}
