package cn.idev.excel.test.core.converter;

import java.math.BigDecimal;

import cn.idev.excel.converters.WriteConverterContext;
import cn.idev.excel.converters.floatconverter.FloatNumberConverter;
import cn.idev.excel.metadata.data.WriteCellData;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * @author Jiaju Zhuang
 */
@TestMethodOrder(MethodOrderer.MethodName.class)
public class ConverterTest {

    @Test
    public void t01FloatNumberConverter() {
        FloatNumberConverter floatNumberConverter = new FloatNumberConverter();
        WriteConverterContext<Float> context = new WriteConverterContext<>();
        context.setValue(95.62F);
        WriteCellData<?> writeCellData = floatNumberConverter.convertToExcelData(context);
        Assertions.assertEquals(0, writeCellData.getNumberValue().compareTo(new BigDecimal("95.62")));
    }

}
