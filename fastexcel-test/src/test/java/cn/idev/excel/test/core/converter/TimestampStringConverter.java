package cn.idev.excel.test.core.converter;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;
import cn.idev.excel.util.DateUtils;
import java.sql.Timestamp;

public class TimestampStringConverter implements Converter<Timestamp> {
    @Override
    public Class<Timestamp> supportJavaTypeKey() {
        return Timestamp.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<?> convertToExcelData(
            Timestamp value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        WriteCellData<String> cellData = new WriteCellData<>();
        String cellValue;
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            cellValue = DateUtils.format(value.toLocalDateTime(), null, globalConfiguration.getLocale());
        } else {
            cellValue = DateUtils.format(
                    value.toLocalDateTime(),
                    contentProperty.getDateTimeFormatProperty().getFormat(),
                    globalConfiguration.getLocale());
        }
        cellData.setType(CellDataTypeEnum.STRING);
        cellData.setStringValue(cellValue);
        cellData.setData(cellValue);
        return cellData;
    }
}
