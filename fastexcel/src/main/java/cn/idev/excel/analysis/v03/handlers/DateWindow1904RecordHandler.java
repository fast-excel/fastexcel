package cn.idev.excel.analysis.v03.handlers;

import cn.idev.excel.analysis.v03.IgnorableXlsRecordHandler;
import cn.idev.excel.context.xls.XlsReadContext;
import org.apache.poi.hssf.record.DateWindow1904Record;
import org.apache.poi.hssf.record.Record;

/**
 * DateWindow1904Record Handler
 */
public class DateWindow1904RecordHandler extends AbstractXlsRecordHandler implements IgnorableXlsRecordHandler {

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        DateWindow1904Record dwr = (DateWindow1904Record) record;
        xlsReadContext.xlsReadWorkbookHolder().getGlobalConfiguration().setUse1904windowing(dwr.getWindowing() == 1);
    }
}
