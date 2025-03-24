package cn.idev.excel.analysis;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.read.metadata.ReadSheet;

import java.util.List;

/**
 * Excel file analyser
 *
 * @author jipengfei
 */
public interface ExcelAnalyser {
    
    /**
     * parse the sheet
     *
     * @param readSheetList Which sheets you need to read.
     * @param readAll       The <code>readSheetList</code> parameter is ignored, and all sheets are read.
     */
    void analysis(List<ReadSheet> readSheetList, Boolean readAll);
    
    /**
     * Complete the entire read file.Release the cache and close stream
     */
    void finish();
    
    /**
     * Acquisition excel executor
     *
     * @return Excel file Executor
     */
    ExcelReadExecutor excelExecutor();
    
    /**
     * get the analysis context.
     *
     * @return analysis context
     */
    AnalysisContext analysisContext();
    
}
