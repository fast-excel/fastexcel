package cn.idev.excel.test.demo.read;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.converters.ReadConverterContext;
import cn.idev.excel.converters.WriteConverterContext;
import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.metadata.data.WriteCellData;

/**
 * String and string converter
 *
 * @author Jiaju Zhuang
 */
public class CustomStringStringConverter implements Converter<String> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * This method is called when reading data from Excel.
     *
     * @param context The context containing the cell data read from Excel.
     * @return The converted Java data.
     */
    @Override
    public String convertToJavaData(ReadConverterContext<?> context) {
        return "Custom：" + context.getReadCellData().getStringValue();
    }

    /**
     * This method is called when writing data to Excel.
     * (This is not relevant in this context and can be ignored.)
     *
     * @param context The context containing the Java data to be written.
     * @return The converted Excel data.
     */
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<String> context) {
        return new WriteCellData<>(context.getValue());
    }

}
