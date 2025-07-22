package cn.idev.excel.context.csv;

import cn.idev.excel.context.AnalysisContextImpl;
import cn.idev.excel.read.metadata.ReadWorkbook;
import cn.idev.excel.read.metadata.holder.csv.CsvReadSheetHolder;
import cn.idev.excel.read.metadata.holder.csv.CsvReadWorkbookHolder;
import cn.idev.excel.support.ExcelTypeEnum;

/**
 * A context is the main anchorage point of a ls xls reader.
 *
 * @author Jiaju Zhuang
 */
public class DefaultCsvReadContext extends AnalysisContextImpl implements CsvReadContext {

    public DefaultCsvReadContext(ReadWorkbook readWorkbook, ExcelTypeEnum actualExcelType) {
        super(readWorkbook, actualExcelType);
    }

    @Override
    public CsvReadWorkbookHolder csvReadWorkbookHolder() {
        return (CsvReadWorkbookHolder)readWorkbookHolder();
    }

    @Override
    public CsvReadSheetHolder csvReadSheetHolder() {
        return (CsvReadSheetHolder)readSheetHolder();
    }
}
