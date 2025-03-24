package cn.idev.excel.converters.date;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.metadata.GlobalConfiguration;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.metadata.property.ExcelContentProperty;
import cn.idev.excel.util.DateUtils;
import cn.idev.excel.util.WorkBookUtil;

import java.util.Date;

/**
 * Date and date converter
 *
 * @author Jiaju Zhuang
 */
public class DateDateConverter implements Converter<Date> {
    
    @Override
    public Class<Date> supportJavaTypeKey() {
        return Date.class;
    }
    
    @Override
    public WriteCellData<?> convertToExcelData(Date value, ExcelContentProperty contentProperty,
            GlobalConfiguration globalConfiguration) throws Exception {
        WriteCellData<?> cellData = new WriteCellData<>(value);
        String format = null;
        if (contentProperty != null && contentProperty.getDateTimeFormatProperty() != null) {
            format = contentProperty.getDateTimeFormatProperty().getFormat();
        }
        WorkBookUtil.fillDataFormat(cellData, format, DateUtils.defaultDateFormat);
        return cellData;
    }
}
