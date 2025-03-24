package cn.idev.excel.converters.file;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;
import cn.idev.excel.util.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * File and image converter
 *
 * @author Jiaju Zhuang
 */
public class FileImageConverter implements Converter<File> {
    
    @Override
    public Class<?> supportJavaTypeKey() {
        return File.class;
    }
    
    @Override
    public WriteCellData<?> convertToExcelData(File value, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) throws IOException {
        return new WriteCellData<>(FileUtils.readFileToByteArray(value));
    }
}
