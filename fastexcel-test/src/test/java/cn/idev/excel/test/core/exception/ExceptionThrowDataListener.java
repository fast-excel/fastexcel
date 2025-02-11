package cn.idev.excel.test.core.exception;

import java.util.ArrayList;
import java.util.List;

import cn.idev.excel.read.listener.ReadListener;
import cn.idev.excel.context.AnalysisContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiaju Zhuang
 */
public class ExceptionThrowDataListener implements ReadListener<ExceptionData> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionData.class);
    List<ExceptionData> list = new ArrayList<ExceptionData>();

    @Override
    public void invoke(ExceptionData data, AnalysisContext context) {
        list.add(data);
        if (list.size() == 5) {
            int i = 5 / 0;
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }
}
