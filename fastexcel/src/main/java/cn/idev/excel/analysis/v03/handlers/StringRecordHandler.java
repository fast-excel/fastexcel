package cn.idev.excel.analysis.v03.handlers;

import cn.idev.excel.analysis.v03.IgnorableXlsRecordHandler;
import cn.idev.excel.context.xls.XlsReadContext;
import cn.idev.excel.metadata.data.CellData;
import cn.idev.excel.read.metadata.holder.xls.XlsReadSheetHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.StringRecord;

/**
 * Record handler
 */
@Slf4j
public class StringRecordHandler extends AbstractXlsRecordHandler implements IgnorableXlsRecordHandler {

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        // String for formula
        StringRecord srec = (StringRecord) record;
        XlsReadSheetHolder xlsReadSheetHolder = xlsReadContext.xlsReadSheetHolder();
        CellData<?> tempCellData = xlsReadSheetHolder.getTempCellData();
        if (tempCellData == null) {
            log.warn("String type formula but no value found.");
            return;
        }
        tempCellData.setStringValue(srec.getString());
        xlsReadSheetHolder.getCellMap().put(tempCellData.getColumnIndex(), tempCellData);
        xlsReadSheetHolder.setTempCellData(null);
    }
}
