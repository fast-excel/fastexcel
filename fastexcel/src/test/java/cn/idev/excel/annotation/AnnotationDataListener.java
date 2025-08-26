package cn.idev.excel.annotation;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.exception.ExcelCommonException;
import cn.idev.excel.util.DateUtils;
import com.alibaba.fastjson2.JSON;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

/**
 *
 */
@Slf4j
public class AnnotationDataListener extends AnalysisEventListener<AnnotationData> {
    List<AnnotationData> list = new ArrayList<AnnotationData>();

    @Override
    public void invoke(AnnotationData data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        Assertions.assertEquals(list.size(), 1);
        AnnotationData data = list.get(0);
        try {
            Assertions.assertEquals(data.getDate(), DateUtils.parseDate("2020-01-01 01:01:01"));
        } catch (ParseException e) {
            throw new ExcelCommonException("Test Exception", e);
        }
        Assertions.assertEquals(data.getNumber(), 99.99, 0.00);
        log.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
