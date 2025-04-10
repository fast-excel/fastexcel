package cn.idev.excel.converters.biginteger;

import java.math.BigInteger;
import java.text.ParseException;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.util.NumberUtils;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;

/**
 * BigDecimal and string converter
 *
 * @author Jiaju Zhuang
 */
public class BigIntegerStringConverter implements Converter<BigInteger> {

    @Override
    public Class<BigInteger> supportJavaTypeKey() {
        return BigInteger.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public BigInteger convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                        GlobalConfiguration globalConfiguration) throws ParseException {
        return NumberUtils.parseBigDecimal(cellData.getStringValue(), contentProperty).toBigInteger();
    }

    @Override
    public WriteCellData<?> convertToExcelData(BigInteger value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        return NumberUtils.formatToCellDataString(value, contentProperty);
    }
}
