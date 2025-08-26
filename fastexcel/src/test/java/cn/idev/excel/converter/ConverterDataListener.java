package cn.idev.excel.converter;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.util.TestUtil;
import com.alibaba.fastjson2.JSON;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

/**
 *
 */
@Slf4j
public class ConverterDataListener extends AnalysisEventListener<ConverterReadData> {
    private final List<ConverterReadData> list = new ArrayList<>();

    @Override
    public void invoke(ConverterReadData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 1);
        ConverterReadData data = list.get(0);
        Assertions.assertEquals(TestUtil.TEST_DATE, data.getDate());
        Assertions.assertEquals(TestUtil.TEST_LOCAL_DATE, data.getLocalDate());
        Assertions.assertEquals(TestUtil.TEST_LOCAL_DATE_TIME, data.getLocalDateTime());
        Assertions.assertEquals(data.getBooleanData(), Boolean.TRUE);
        Assertions.assertEquals(data.getBigDecimal().doubleValue(), BigDecimal.ONE.doubleValue(), 0.0);
        Assertions.assertEquals(data.getBigInteger().intValue(), BigInteger.ONE.intValue(), 0.0);
        Assertions.assertEquals((long) data.getLongData(), 1L);
        Assertions.assertEquals((long) data.getIntegerData(), 1L);
        Assertions.assertEquals((long) data.getShortData(), 1L);
        Assertions.assertEquals((long) data.getByteData(), 1L);
        Assertions.assertEquals(data.getDoubleData(), 1.0, 0.0);
        Assertions.assertEquals(data.getFloatData(), (float) 1.0, 0.0);
        Assertions.assertEquals(data.getString(), "测试");
        Assertions.assertEquals(data.getCellData().getStringValue(), "自定义");
        log.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
