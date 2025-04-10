package cn.idev.excel.converters.bytearray;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;

/**
 * Boxing Byte array and image converter
 *
 * @author Jiaju Zhuang
 */
public class BoxingByteArrayImageConverter implements Converter<Byte[]> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return Byte[].class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Byte[] value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) {
        byte[] byteValue = new byte[value.length];
        for (int i = 0; i < value.length; i++) {
            byteValue[i] = value[i];
        }
        return new WriteCellData<>(byteValue);
    }

}
