package cn.idev.excel.converters.inputstream;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;
import cn.idev.excel.util.IoUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * File and image converter
 *
 * @author Jiaju Zhuang
 */
public class InputStreamImageConverter implements Converter<InputStream> {
    
    @Override
    public Class<?> supportJavaTypeKey() {
        return InputStream.class;
    }
    
    @Override
    public WriteCellData<?> convertToExcelData(InputStream value, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) throws IOException {
        return new WriteCellData<>(IoUtils.toByteArray(value));
    }
    
}
