package cn.idev.excel.test.demo.write;

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
     * 这里是读的时候会调用 不用管
     *
     * @return
     */
    @Override
    public String convertToJavaData(ReadConverterContext<?> context) {
        return context.getReadCellData().getStringValue();
    }

    /**
     * 这里是写的时候会调用 不用管
     *
     * @return
     */
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<String> context) {
        return new WriteCellData<>("自定义：" + context.getValue());
    }

}
