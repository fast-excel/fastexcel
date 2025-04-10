package cn.idev.excel.converters.url;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.util.IoUtils;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;

/**
 * Url and image converter
 *
 * @author Jiaju Zhuang
 * @since 2.1.1
 */
public class UrlImageConverter implements Converter<URL> {
    public static int urlConnectTimeout = 1000;
    public static int urlReadTimeout = 5000;

    @Override
    public Class<?> supportJavaTypeKey() {
        return URL.class;
    }

    @Override
    public WriteCellData<?> convertToExcelData(URL value, ExcelContentProperty contentProperty,
                                               GlobalConfiguration globalConfiguration) throws IOException {
        InputStream inputStream = null;
        try {
            URLConnection urlConnection = value.openConnection();
            urlConnection.setConnectTimeout(urlConnectTimeout);
            urlConnection.setReadTimeout(urlReadTimeout);
            inputStream = urlConnection.getInputStream();
            byte[] bytes = IoUtils.toByteArray(inputStream);
            return new WriteCellData<>(bytes);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

}
