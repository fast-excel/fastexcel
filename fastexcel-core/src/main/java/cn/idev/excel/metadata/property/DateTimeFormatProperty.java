package cn.idev.excel.metadata.property;

import cn.idev.excel.annotation.format.DateTimeFormat;
import cn.idev.excel.util.BooleanUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Configuration from annotations
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class DateTimeFormatProperty {
    private String format;
    private Boolean use1904windowing;

    public DateTimeFormatProperty(String format, Boolean use1904windowing) {
        this.format = format;
        this.use1904windowing = use1904windowing;
    }

    public static DateTimeFormatProperty build(DateTimeFormat dateTimeFormat) {
        if (dateTimeFormat == null) {
            return null;
        }
        return new DateTimeFormatProperty(dateTimeFormat.value(),
            BooleanUtils.isTrue(dateTimeFormat.use1904windowing().getBooleanValue()));
    }
}
