package cn.idev.excel.converters.localdatetime;

import java.text.ParseException;
import java.time.LocalDateTime;

import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.util.DateUtils;
import cn.idev.excel.converters.Converter;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;

/**
 * LocalDateTime and string converter
 *
 * @author Jiaju Zhuang
 */
public class LocalDateTimeStringConverter implements Converter<LocalDateTime> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return LocalDateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public LocalDateTime convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                           GlobalConfiguration globalConfiguration) throws ParseException {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return DateUtils.parseLocalDateTime(cellData.getStringValue(), null, globalConfiguration.getLocale());
        } else {
            return DateUtils.parseLocalDateTime(cellData.getStringValue(),
                contentProperty.getDateTimeFormatProperty().getFormat(), globalConfiguration.getLocale());
        }
    }

    @Override
    public WriteCellData<?> convertToExcelData(LocalDateTime value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return new WriteCellData<>(DateUtils.format(value, null, globalConfiguration.getLocale()));
        } else {
            return new WriteCellData<>(
                DateUtils.format(value, contentProperty.getDateTimeFormatProperty().getFormat(),
                    globalConfiguration.getLocale()));
        }
    }
}
