package cn.idev.excel.converters.floatconverter;

import java.text.ParseException;

import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.util.NumberUtils;
import cn.idev.excel.converters.Converter;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;

/**
 * Float and string converter
 *
 * @author Jiaju Zhuang
 */
public class FloatStringConverter implements Converter<Float> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Float.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Float convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                   GlobalConfiguration globalConfiguration) throws ParseException {
        return NumberUtils.parseFloat(cellData.getStringValue(), contentProperty);
    }

    @Override
    public WriteCellData<?> convertToExcelData(Float value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        return NumberUtils.formatToCellDataString(value, contentProperty);
    }
}
