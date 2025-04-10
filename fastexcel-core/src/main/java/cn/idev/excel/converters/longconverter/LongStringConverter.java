package cn.idev.excel.converters.longconverter;

import java.text.ParseException;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.util.NumberUtils;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;

/**
 * Long and string converter
 *
 * @author Jiaju Zhuang
 */
public class LongStringConverter implements Converter<Long> {

    @Override
    public Class<Long> supportJavaTypeKey() {
        return Long.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Long convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                  GlobalConfiguration globalConfiguration) throws ParseException {
        return NumberUtils.parseLong(cellData.getStringValue(), contentProperty);
    }

    @Override
    public WriteCellData<?> convertToExcelData(Long value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        return NumberUtils.formatToCellDataString(value, contentProperty);
    }
}
