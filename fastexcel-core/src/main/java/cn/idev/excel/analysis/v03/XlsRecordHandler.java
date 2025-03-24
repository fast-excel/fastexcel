package cn.idev.excel.analysis.v03;

import cn.idev.excel.context.xls.XlsReadContext;
import org.apache.poi.hssf.record.Record;

/**
 * Intercepts handle xls reads.
 *
 * @author Dan Zheng
 */
public interface XlsRecordHandler {
    
    /**
     * Whether to support
     *
     * @param xlsReadContext
     * @param record
     * @return
     */
    boolean support(XlsReadContext xlsReadContext, Record record);
    
    /**
     * Processing record
     *
     * @param xlsReadContext
     * @param record
     */
    void processRecord(XlsReadContext xlsReadContext, Record record);
}
