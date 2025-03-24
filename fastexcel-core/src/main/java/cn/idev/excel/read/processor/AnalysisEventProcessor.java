package cn.idev.excel.read.processor;

import cn.idev.excel.context.AnalysisContext;

/**
 * Event processor
 *
 * @author jipengfei
 */
public interface AnalysisEventProcessor {
    
    /**
     * Read extra information
     *
     * @param analysisContext
     */
    void extra(AnalysisContext analysisContext);
    
    /**
     * End row
     *
     * @param analysisContext
     */
    void endRow(AnalysisContext analysisContext);
    
    /**
     * Notify after all analysed
     *
     * @param analysisContext Analysis context
     */
    void endSheet(AnalysisContext analysisContext);
}
