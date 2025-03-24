package cn.idev.excel.test.core.fill.annotation;

import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.format.DateTimeFormat;
import cn.idev.excel.annotation.format.NumberFormat;
import cn.idev.excel.annotation.write.style.ContentLoopMerge;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.converters.string.StringImageConverter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
@ContentRowHeight(100)
public class FillAnnotationData {
    
    @ExcelProperty("Date")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date date;
    
    @ExcelProperty(value = "Number")
    @NumberFormat("#.##%")
    private Double number;
    
    @ContentLoopMerge(columnExtend = 2)
    @ExcelProperty("String 1")
    private String string1;
    
    @ExcelProperty("String 2")
    private String string2;
    
    @ExcelProperty(value = "Image", converter = StringImageConverter.class)
    private String image;
}
