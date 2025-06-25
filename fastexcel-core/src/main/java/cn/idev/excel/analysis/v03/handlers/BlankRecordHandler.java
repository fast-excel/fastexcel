package cn.idev.excel.analysis.v03.handlers;

import cn.idev.excel.analysis.v03.IgnorableXlsRecordHandler;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.context.xls.XlsReadContext;

import org.apache.poi.hssf.record.BlankRecord;
import org.apache.poi.hssf.record.Record;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class BlankRecordHandler extends AbstractXlsRecordHandler implements IgnorableXlsRecordHandler {

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        BlankRecord br = (BlankRecord)record;
        xlsReadContext.xlsReadSheetHolder().getCellMap().put((int)br.getColumn(),
            ReadCellData.newEmptyInstance(br.getRow(), (int)br.getColumn()));
    }
}
