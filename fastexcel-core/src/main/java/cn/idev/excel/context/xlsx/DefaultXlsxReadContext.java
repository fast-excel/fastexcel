package cn.idev.excel.context.xlsx;

import cn.idev.excel.context.AnalysisContextImpl;
import cn.idev.excel.read.metadata.ReadWorkbook;
import cn.idev.excel.read.metadata.holder.xlsx.XlsxReadSheetHolder;
import cn.idev.excel.read.metadata.holder.xlsx.XlsxReadWorkbookHolder;
import cn.idev.excel.support.ExcelTypeEnum;

/**
 *
 * A context is the main anchorage point of a ls xls reader.
 *
 * @author Jiaju Zhuang
 */
public class DefaultXlsxReadContext extends AnalysisContextImpl implements XlsxReadContext {

    public DefaultXlsxReadContext(ReadWorkbook readWorkbook, ExcelTypeEnum actualExcelType) {
        super(readWorkbook, actualExcelType);
    }

    @Override
    public XlsxReadWorkbookHolder xlsxReadWorkbookHolder() {
        return (XlsxReadWorkbookHolder)readWorkbookHolder();
    }

    @Override
    public XlsxReadSheetHolder xlsxReadSheetHolder() {
        return (XlsxReadSheetHolder)readSheetHolder();
    }
}
