package cn.idev.excel.converters.integer;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.converters.WriteConverterContext;
import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.util.NumberUtils;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;

/**
 * Integer and number converter
 *
 * @author Jiaju Zhuang
 */
public class IntegerNumberConverter implements Converter<Integer> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                     GlobalConfiguration globalConfiguration) {
        return cellData.getNumberValue().intValue();
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) {
        return NumberUtils.formatToCellData(context.getValue(), context.getContentProperty());
    }

}
