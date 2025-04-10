package cn.idev.excel.converters.doubleconverter;

import java.text.ParseException;

import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.util.NumberUtils;
import cn.idev.excel.converters.Converter;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;

/**
 * Double and string converter
 *
 * @author Jiaju Zhuang
 */
public class DoubleStringConverter implements Converter<Double> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Double.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Double convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                    GlobalConfiguration globalConfiguration) throws ParseException {
        return NumberUtils.parseDouble(cellData.getStringValue(), contentProperty);
    }

    @Override
    public WriteCellData<?> convertToExcelData(Double value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        return NumberUtils.formatToCellDataString(value, contentProperty);
    }
}
