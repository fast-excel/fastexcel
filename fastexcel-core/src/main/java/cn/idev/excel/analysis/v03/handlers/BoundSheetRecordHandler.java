package cn.idev.excel.analysis.v03.handlers;

import cn.idev.excel.analysis.v03.IgnorableXlsRecordHandler;
import cn.idev.excel.context.xls.XlsReadContext;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.Record;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class BoundSheetRecordHandler extends AbstractXlsRecordHandler implements IgnorableXlsRecordHandler {

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        BoundSheetRecord bsr = (BoundSheetRecord)record;
        xlsReadContext.xlsReadWorkbookHolder().getBoundSheetRecordList().add(bsr);
    }
}
