package cn.idev.excel.converters.bigdecimal;

import java.math.BigDecimal;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;

/**
 * BigDecimal and boolean converter
 *
 * This converter is responsible for converting between Java type BigDecimal and Excel's boolean type.
 * It treats Excel's true as BigDecimal.ONE, and false as BigDecimal.ZERO.
 *
 * @author Jiaju Zhuang
 */
public class BigDecimalBooleanConverter implements Converter<BigDecimal> {

    /**
     * Returns the Java type key supported by this converter.
     *
     * @return Returns the class type of BigDecimal.
     */
    @Override
    public Class<BigDecimal> supportJavaTypeKey() {
        return BigDecimal.class;
    }

    /**
     * Returns the Excel cell data type key supported by this converter.
     *
     * @return Returns the cell data type enumeration of boolean type.
     */
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.BOOLEAN;
    }

    /**
     * Converts Excel cell data to Java type BigDecimal.
     * If the cell data represents true, returns BigDecimal.ONE, otherwise returns BigDecimal.ZERO.
     *
     * @param cellData               Excel cell data.
     * @param contentProperty        Excel content property.
     * @param globalConfiguration    Global configuration.
     * @return                       Returns the converted BigDecimal object.
     */
    @Override
    public BigDecimal convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
                                        GlobalConfiguration globalConfiguration) {
        if (cellData.getBooleanValue()) {
            return BigDecimal.ONE;
        }
        return BigDecimal.ZERO;
    }

    /**
     * Converts Java type BigDecimal to Excel cell data.
     * If the value is BigDecimal.ONE, returns a WriteCellData containing true, otherwise returns a WriteCellData containing false.
     *
     * @param value                  Java type BigDecimal value.
     * @param contentProperty        Excel content property.
     * @param globalConfiguration    Global configuration.
     * @return                       Returns the converted Excel cell data.
     */
    @Override
    public WriteCellData<?> convertToExcelData(BigDecimal value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        if (BigDecimal.ONE.equals(value)) {
            return new WriteCellData<>(Boolean.TRUE);
        }
        return new WriteCellData<>(Boolean.FALSE);
    }

}
