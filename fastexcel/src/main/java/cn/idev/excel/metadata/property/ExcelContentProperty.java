package cn.idev.excel.metadata.property;

import cn.idev.excel.converters.Converter;
import java.lang.reflect.Field;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
@EqualsAndHashCode
public class ExcelContentProperty {
    public static final ExcelContentProperty EMPTY = new ExcelContentProperty();

    /**
     * Java field
     */
    private Field field;
    /**
     * Custom defined converters
     */
    private Converter<?> converter;
    /**
     * date time format
     */
    private DateTimeFormatProperty dateTimeFormatProperty;
    /**
     * number format
     */
    private NumberFormatProperty numberFormatProperty;
    /**
     * Content style
     */
    private StyleProperty contentStyleProperty;
    /**
     * Content font
     */
    private FontProperty contentFontProperty;
}
