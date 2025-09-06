package cn.idev.excel.util;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.context.WriteContext;
import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.write.metadata.WriteSheet;

/**
 * Parameter Util
 */
public class ParameterUtil {

    /**
     * Get autoTrim flag for read
     *
     * @param readSheet actual read sheet
     * @param context   Analysis Context
     * @return autoTrim flag
     */
    public static boolean getAutoTrimFlag(ReadSheet readSheet, AnalysisContext context) {
        return (readSheet.getAutoTrim() != null && readSheet.getAutoTrim())
                || (readSheet.getAutoTrim() == null
                        && context.readWorkbookHolder().getGlobalConfiguration().getAutoTrim());
    }

    /**
     * Get autoStrip flag for read
     *
     * @param readSheet actual read sheet
     * @param context   Analysis Context
     * @return autoStrip flag
     */
    public static boolean getAutoStripFlag(ReadSheet readSheet, AnalysisContext context) {
        return (readSheet.getAutoStrip() != null && readSheet.getAutoStrip())
                || context.readWorkbookHolder().getGlobalConfiguration().getAutoStrip();
    }

    /**
     * Get autoTrim flag for write
     *
     * @param writeSheet actual write sheet
     * @param context    Write Context
     * @return autoTrim flag
     */
    public static boolean getAutoTrimFlag(WriteSheet writeSheet, WriteContext context) {
        return (writeSheet.getAutoTrim() != null && writeSheet.getAutoTrim())
                || (writeSheet.getAutoTrim() == null
                        && context.writeWorkbookHolder()
                                .getGlobalConfiguration()
                                .getAutoTrim());
    }

    /**
     * Get autoStrip flag for write
     *
     * @param writeSheet actual write sheet
     * @param context    Write Context
     * @return autoStrip flag
     */
    public static boolean getAutoStripFlag(WriteSheet writeSheet, WriteContext context) {
        return (writeSheet.getAutoStrip() != null && writeSheet.getAutoStrip())
                || context.writeWorkbookHolder().getGlobalConfiguration().getAutoStrip();
    }
}
