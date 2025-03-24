package cn.idev.excel.converters.string;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;
import cn.idev.excel.util.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * String and image converter
 *
 * @author Jiaju Zhuang
 */
public class StringImageConverter implements Converter<String> {
    
    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }
    
    @Override
    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) throws IOException {
        return new WriteCellData<>(FileUtils.readFileToByteArray(new File(value)));
    }
    
}
