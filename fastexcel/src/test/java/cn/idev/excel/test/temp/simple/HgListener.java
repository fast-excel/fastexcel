package cn.idev.excel.test.temp.simple;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSON;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * 模板的读取类
 *
 *
 */
@Slf4j
public class HgListener extends AnalysisEventListener<Map<Integer, String>> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 5;

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("index:{}", context.readRowHolder().getRowIndex());
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }
}
