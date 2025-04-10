package cn.idev.excel.converters.localdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.util.DateUtils;
import cn.idev.excel.util.WorkBookUtil;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;

/**
 * LocalDate and date converter
 *
 * @author Jiaju Zhuang
 */
public class LocalDateDateConverter implements Converter<LocalDate> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return LocalDate.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(LocalDate value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) throws Exception {
        LocalDateTime localDateTime = value == null ? null : value.atTime(0, 0);
        WriteCellData<?> cellData = new WriteCellData<>(localDateTime);
        String format = null;
        if (contentProperty != null && contentProperty.getDateTimeFormatProperty() != null) {
            format = contentProperty.getDateTimeFormatProperty().getFormat();
        }
        WorkBookUtil.fillDataFormat(cellData, format, DateUtils.defaultLocalDateFormat);
        return cellData;
    }
}
