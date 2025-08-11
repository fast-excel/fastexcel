package cn.idev.excel.test.core.parameter;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.event.AnalysisEventListener;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
public class DateWindowingListener extends AnalysisEventListener<ParameterData> {

    private Boolean expectFlag = Boolean.FALSE;

    private DateWindowingListener() {}

    public DateWindowingListener(Boolean expectFlag) {
        this.expectFlag = expectFlag == null ? Boolean.FALSE : expectFlag;
    }

    @Override
    public void invoke(ParameterData data, AnalysisContext context) {
        log.info("row data:{}", JSON.toJSONString(data));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        boolean isUse1904windowing =
                context.readWorkbookHolder().globalConfiguration().getUse1904windowing();
        log.info(
                "excel type:{},isUse1904windowing: {}",
                context.readWorkbookHolder().getExcelType(),
                isUse1904windowing);
        Assertions.assertEquals(isUse1904windowing, this.expectFlag);
    }
}
