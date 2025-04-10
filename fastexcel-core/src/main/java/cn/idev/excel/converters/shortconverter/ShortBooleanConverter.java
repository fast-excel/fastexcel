package cn.idev.excel.converters.shortconverter;

import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.converters.Converter;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;

/**
 * Short and boolean converter
 *
 * @author Jiaju Zhuang
 */
public class ShortBooleanConverter implements Converter<Short> {
    private static final Short ONE = 1;
    private static final Short ZERO = 0;

    @Override
    public Class<Short> supportJavaTypeKey() {
        return Short.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.BOOLEAN;
    }

    @Override
    public Short convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                   GlobalConfiguration globalConfiguration) {
        if (cellData.getBooleanValue()) {
            return ONE;
        }
        return ZERO;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Short value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        if (ONE.equals(value)) {
            return new WriteCellData<>(Boolean.TRUE);
        }
        return new WriteCellData<>(Boolean.FALSE);
    }

}
