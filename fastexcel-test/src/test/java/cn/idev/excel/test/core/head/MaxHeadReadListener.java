package cn.idev.excel.test.core.head;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaxHeadReadListener extends AnalysisEventListener<Map<Integer, String>> {
    private static final Logger log = LoggerFactory.getLogger(MaxHeadReadListener.class);
    List<Map<Integer, String>> list = new ArrayList<Map<Integer, String>>();
    private List<Map<Integer, String>> headList = new ArrayList<>();
    private Map<Integer, String> headTitleMap = new HashMap<>();
    private int headSize;

    public MaxHeadReadListener(int headSize) {
        this.headSize = headSize;
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("origin head : {}",JSON.toJSONString(headTitleMap,JSONWriter.Feature.WriteMapNullValue));
        log.info("max not empty head size : {}",context.readSheetHolder().getMaxNotEmptyDataHeadSize());
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        headTitleMap = headMap;
        headList.add(headMap);
    }
}
