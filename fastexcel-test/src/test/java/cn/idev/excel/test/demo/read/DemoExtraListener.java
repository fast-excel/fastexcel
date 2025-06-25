package cn.idev.excel.test.demo.read;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.metadata.CellExtra;
import cn.idev.excel.read.listener.ReadListener;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

/**
 * Listener to read cell comments, hyperlinks, and merged cells.
 *
 * @author Jiaju Zhuang
 **/
@Slf4j
public class DemoExtraListener implements ReadListener<DemoExtraData> {

    @Override
    public void invoke(DemoExtraData data, AnalysisContext context) {
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
    }

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        log.info("Read an extra piece of information: {}", JSON.toJSONString(extra));
        switch (extra.getType()) {
            case COMMENT:
                log.info("The extra information is a comment, at rowIndex:{}, columnIndex:{}, content:{}", extra.getRowIndex(),
                        extra.getColumnIndex(), extra.getText());
                break;
            case HYPERLINK:
                if ("Sheet1!A1".equals(extra.getText())) {
                    log.info("The extra information is a hyperlink, at rowIndex:{}, columnIndex:{}, content:{}", extra.getRowIndex(),
                            extra.getColumnIndex(), extra.getText());
                } else if ("Sheet2!A1".equals(extra.getText())) {
                    log.info("The extra information is a hyperlink, covering a range, firstRowIndex:{}, firstColumnIndex:{}, " +
                            "lastRowIndex:{}, lastColumnIndex:{}, content:{}",  extra.getFirstRowIndex(), extra.getFirstColumnIndex(),
                            extra.getLastRowIndex(), extra.getLastColumnIndex(), extra.getText());
                } else {
                    Assertions.fail("Unknown hyperlink!");
                }
                break;
            case MERGE:
                log.info(
                    "The extra information is a merged cell, covering a range, firstRowIndex:{}, firstColumnIndex:{}, " +
                    "lastRowIndex:{}, lastColumnIndex:{}",
                    extra.getFirstRowIndex(), extra.getFirstColumnIndex(),
                    extra.getLastRowIndex(), extra.getLastColumnIndex());
                break;
            default:
        }
    }
}
