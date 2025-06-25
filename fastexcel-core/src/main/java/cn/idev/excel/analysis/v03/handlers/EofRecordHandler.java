package cn.idev.excel.analysis.v03.handlers;

import java.util.LinkedHashMap;

import cn.idev.excel.enums.RowTypeEnum;
import cn.idev.excel.metadata.Cell;
import cn.idev.excel.read.metadata.holder.ReadRowHolder;
import cn.idev.excel.read.metadata.holder.xls.XlsReadSheetHolder;
import cn.idev.excel.util.BooleanUtils;
import cn.idev.excel.context.xls.XlsReadContext;
import org.apache.poi.hssf.record.Record;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class EofRecordHandler extends AbstractXlsRecordHandler {

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        if (xlsReadContext.readSheetHolder() == null) {
            return;
        }

        //Represents the current sheet does not need to be read or the user manually stopped reading the sheet.
        if (BooleanUtils.isTrue(xlsReadContext.xlsReadWorkbookHolder().getIgnoreRecord())) {
            // When the user manually stops reading the sheet, the method to end the sheet needs to be called.
            if (BooleanUtils.isTrue(xlsReadContext.xlsReadWorkbookHolder().getCurrentSheetStopped())) {
                xlsReadContext.analysisEventProcessor().endSheet(xlsReadContext);
            }
            return;
        }

        // Sometimes tables lack the end record of the last column
        if (!xlsReadContext.xlsReadSheetHolder().getCellMap().isEmpty()) {
            XlsReadSheetHolder xlsReadSheetHolder = xlsReadContext.xlsReadSheetHolder();
            // Forge a termination data
            xlsReadContext.readRowHolder(new ReadRowHolder(xlsReadContext.xlsReadSheetHolder().getRowIndex() + 1,
                xlsReadSheetHolder.getTempRowType(),
                xlsReadContext.readSheetHolder().getGlobalConfiguration(), xlsReadSheetHolder.getCellMap()));
            xlsReadContext.analysisEventProcessor().endRow(xlsReadContext);
            xlsReadSheetHolder.setCellMap(new LinkedHashMap<Integer, Cell>());
            xlsReadSheetHolder.setTempRowType(RowTypeEnum.EMPTY);
        }

        xlsReadContext.analysisEventProcessor().endSheet(xlsReadContext);
    }
}
