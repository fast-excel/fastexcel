package cn.idev.excel.converters.booleanconverter;

import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.converters.Converter;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;

/**
 * Boolean and string converter
 *
 * @author Jiaju Zhuang
 */
public class BooleanStringConverter implements Converter<Boolean> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Boolean.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Boolean convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                     GlobalConfiguration globalConfiguration) {
        return Boolean.valueOf(cellData.getStringValue());
    }

    @Override
    public WriteCellData<?> convertToExcelData(Boolean value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(value.toString());
    }

}
