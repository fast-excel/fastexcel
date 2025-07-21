package cn.idev.excel.analysis.v03.handlers;

import java.math.BigDecimal;

import cn.idev.excel.analysis.v03.IgnorableXlsRecordHandler;
import cn.idev.excel.constant.BuiltinFormats;
import cn.idev.excel.enums.RowTypeEnum;
import cn.idev.excel.metadata.data.DataFormatData;
import cn.idev.excel.metadata.data.ReadCellData;
import cn.idev.excel.context.xls.XlsReadContext;

import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.Record;

/**
 * Record handler
 *
 * @author Dan Zheng
 */
public class NumberRecordHandler extends AbstractXlsRecordHandler implements IgnorableXlsRecordHandler {

    @Override
    public void processRecord(XlsReadContext xlsReadContext, Record record) {
        NumberRecord nr = (NumberRecord)record;
        ReadCellData<?> cellData = ReadCellData.newInstanceOriginal(BigDecimal.valueOf(nr.getValue()), nr.getRow(),
            (int)nr.getColumn());
        short dataFormat = (short)xlsReadContext.xlsReadWorkbookHolder().getFormatTrackingHSSFListener().getFormatIndex(
            nr);
        DataFormatData dataFormatData = new DataFormatData();
        dataFormatData.setIndex(dataFormat);
        dataFormatData.setFormat(BuiltinFormats.getBuiltinFormat(dataFormat,
            xlsReadContext.xlsReadWorkbookHolder().getFormatTrackingHSSFListener().getFormatString(nr),
            xlsReadContext.readSheetHolder().getGlobalConfiguration().getLocale()));
        cellData.setDataFormatData(dataFormatData);
        xlsReadContext.xlsReadSheetHolder().getCellMap().put((int)nr.getColumn(), cellData);
        xlsReadContext.xlsReadSheetHolder().setTempRowType(RowTypeEnum.DATA);
    }
}
