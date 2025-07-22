package cn.idev.excel.converters.bigdecimal;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;
import cn.idev.excel.util.NumberUtils;
import java.math.BigDecimal;

/**
 * Converter for handling the conversion between BigDecimal and Excel number types.
 *
 *
 */
public class BigDecimalNumberConverter implements Converter<BigDecimal> {

    /**
     * Specifies the Java type supported by this converter.
     *
     * @return The class type of BigDecimal.
     */
    @Override
    public Class<BigDecimal> supportJavaTypeKey() {
        return BigDecimal.class;
    }

    /**
     * Specifies the Excel cell data type supported by this converter.
     *
     * @return The cell data type enumeration for numbers.
     */
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.NUMBER;
    }

    /**
     * Converts Excel cell data to a BigDecimal object.
     *
     * @param cellData               The Excel cell data to be converted.
     * @param contentProperty        The content property associated with the cell.
     * @param globalConfiguration    The global configuration for the conversion process.
     * @return                       The converted BigDecimal value from the cell data.
     */
    @Override
    public BigDecimal convertToJavaData(
            ReadCellData<?> cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return cellData.getNumberValue();
    }

    /**
     * Converts a BigDecimal object to Excel cell data.
     *
     * @param value                  The BigDecimal value to be converted.
     * @param contentProperty        The content property associated with the cell.
     * @param globalConfiguration    The global configuration for the conversion process.
     * @return                       The WriteCellData object containing the formatted number.
     */
    @Override
    public WriteCellData<?> convertToExcelData(
            BigDecimal value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
        return NumberUtils.formatToCellData(value, contentProperty);
    }
}
