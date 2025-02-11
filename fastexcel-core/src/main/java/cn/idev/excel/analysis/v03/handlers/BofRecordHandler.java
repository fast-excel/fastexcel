package cn.idev.excel.analysis.v03.handlers;

import java.util.ArrayList;
import java.util.List;

import cn.idev.excel.exception.ExcelAnalysisStopException;
import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.read.metadata.holder.xls.XlsReadWorkbookHolder;
import cn.idev.excel.util.SheetUtils;
import cn.idev.excel.context.xls.XlsReadContext;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.Record;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class BofRecordHandler extends AbstractXlsRecordHandler {

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        BOFRecord br = (BOFRecord) record;
        XlsReadWorkbookHolder xlsReadWorkbookHolder = xlsReadContext.xlsReadWorkbookHolder();
        if (br.getType() == BOFRecord.TYPE_WORKBOOK) {
            xlsReadWorkbookHolder.setReadSheetIndex(null);
            xlsReadWorkbookHolder.setIgnoreRecord(Boolean.FALSE);
            return;
        }
        if (br.getType() != BOFRecord.TYPE_WORKSHEET) {
            return;
        }
        // Init read sheet Data
        initReadSheetDataList(xlsReadWorkbookHolder);
        Integer readSheetIndex = xlsReadWorkbookHolder.getReadSheetIndex();
        if (readSheetIndex == null) {
            readSheetIndex = 0;
            xlsReadWorkbookHolder.setReadSheetIndex(readSheetIndex);
        }
        ReadSheet actualReadSheet = xlsReadWorkbookHolder.getActualSheetDataList().get(readSheetIndex);
        assert actualReadSheet != null : "Can't find the sheet.";
        // Copy the parameter to the current sheet
        ReadSheet readSheet = SheetUtils.match(actualReadSheet, xlsReadContext);
        if (readSheet != null) {
            xlsReadContext.currentSheet(readSheet);
            xlsReadContext.xlsReadWorkbookHolder().setIgnoreRecord(Boolean.FALSE);
        } else {
            xlsReadContext.xlsReadWorkbookHolder().setIgnoreRecord(Boolean.TRUE);
        }
        xlsReadContext.xlsReadWorkbookHolder().setCurrentSheetStopped(Boolean.FALSE);
        // Go read the next one
        xlsReadWorkbookHolder.setReadSheetIndex(xlsReadWorkbookHolder.getReadSheetIndex() + 1);
    }

    private void initReadSheetDataList(XlsReadWorkbookHolder xlsReadWorkbookHolder) {
        if (xlsReadWorkbookHolder.getActualSheetDataList() != null) {
            return;
        }
        BoundSheetRecord[] boundSheetRecords =
            BoundSheetRecord.orderByBofPosition(xlsReadWorkbookHolder.getBoundSheetRecordList());
        List<ReadSheet> readSheetDataList = new ArrayList<ReadSheet>();
        for (int i = 0; i < boundSheetRecords.length; i++) {
            BoundSheetRecord boundSheetRecord = boundSheetRecords[i];
            ReadSheet readSheet = new ReadSheet(i, boundSheetRecord.getSheetname());
            readSheetDataList.add(readSheet);
        }
        xlsReadWorkbookHolder.setActualSheetDataList(readSheetDataList);
        // Just need to get the list of sheets
        if (!xlsReadWorkbookHolder.getNeedReadSheet()) {
            throw new ExcelAnalysisStopException("Just need to get the list of sheets.");
        }
    }
}
