package cn.idev.excel.test.core.exception;

import java.util.List;
import java.util.Map;

import cn.idev.excel.event.AnalysisEventListener;
import cn.idev.excel.util.ListUtils;
import cn.idev.excel.util.MapUtils;
import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.exception.ExcelAnalysisStopSheetException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Slf4j
public class ExcelAnalysisStopSheetExceptionDataListener extends AnalysisEventListener<ExceptionData> {

    private Map<Integer, List<String>> dataMap = MapUtils.newHashMap();


    @Override
    public void invoke(ExceptionData data, AnalysisContext context) {
        List<String> sheetDataList = dataMap.computeIfAbsent(context.readSheetHolder().getSheetNo(),
            key -> ListUtils.newArrayList());
        sheetDataList.add(data.getName());
        if (sheetDataList.size() >= 5) {
            throw new ExcelAnalysisStopSheetException();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        List<String> sheetDataList = dataMap.get(context.readSheetHolder().getSheetNo());
        Assertions.assertNotNull(sheetDataList);
        Assertions.assertEquals(5, sheetDataList.size());
    }
}
