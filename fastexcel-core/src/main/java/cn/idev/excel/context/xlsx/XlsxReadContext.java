package cn.idev.excel.context.xlsx;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.read.metadata.holder.xlsx.XlsxReadSheetHolder;
import cn.idev.excel.read.metadata.holder.xlsx.XlsxReadWorkbookHolder;

/**
 * A context is the main anchorage point of a ls xlsx reader.
 *
 * @author Jiaju Zhuang
 **/
public interface XlsxReadContext extends AnalysisContext {
    /**
     * All information about the workbook you are currently working on.
     *
     * @return Current workbook holder
     */
    XlsxReadWorkbookHolder xlsxReadWorkbookHolder();

    /**
     * All information about the sheet you are currently working on.
     *
     * @return Current sheet holder
     */
    XlsxReadSheetHolder xlsxReadSheetHolder();
}
