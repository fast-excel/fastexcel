package cn.idev.excel.test.core.simple;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.event.SyncReadListener;
import com.alibaba.fastjson2.JSON;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

/**
 * Define an AnalysisEventListener to handler the Analysis event
 *
 *
 */
@Slf4j
public class SimpleDataListener extends AnalysisEventListener<SimpleData> {

    List<SimpleData> list = new ArrayList<SimpleData>();

    /**
     * handle header of the file data
     *
     * @param headMap head map
     * @param context context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        log.debug("Head is:{}", JSON.toJSONString(headMap));
        Assertions.assertEquals(headMap.get(0), "姓名");
    }

    /**
     * handle data row in the file
     * <p>
     * this is the same way of implement as {@link SyncReadListener#invoke}
     * all the data are stored synchronously
     * </p>
     *
     * @param data    data
     * @param context context
     */
    @Override
    public void invoke(SimpleData data, AnalysisContext context) {
        list.add(data);
    }

    /**
     * do after all analyse process
     *
     * @param context context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // check the results
        Assertions.assertEquals(list.size(), 10);
        Assertions.assertEquals(list.get(0).getName(), "姓名0");
        Assertions.assertEquals((int) (context.readSheetHolder().getSheetNo()), 0);
        Assertions.assertEquals(
                context.readSheetHolder()
                        .getExcelReadHeadProperty()
                        .getHeadMap()
                        .get(0)
                        .getHeadNameList()
                        .get(0),
                "姓名");
        log.debug("First row:{}", JSON.toJSONString(list.get(0)));
    }
}
