package cn.idev.excel.util;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.read.metadata.holder.ReadWorkbookHolder;
import lombok.extern.slf4j.Slf4j;

/**
 * Sheet utils
 *
 *
 */
@Slf4j
public class SheetUtils {

    private SheetUtils() {}

    /**
     * Match the parameters to the actual sheet
     *
     * @param readSheet       actual sheet
     * @param analysisContext
     * @return
     */
    public static ReadSheet match(ReadSheet readSheet, AnalysisContext analysisContext) {
        ReadWorkbookHolder readWorkbookHolder = analysisContext.readWorkbookHolder();
        if (analysisContext.readWorkbookHolder().getIgnoreHiddenSheet()
                && (readSheet.isHidden() || readSheet.isVeryHidden())) {
            return null;
        }
        if (readWorkbookHolder.getReadAll()) {
            return readSheet;
        }
        for (ReadSheet parameterReadSheet : readWorkbookHolder.getParameterSheetDataList()) {
            if (parameterReadSheet == null) {
                continue;
            }
            if (parameterReadSheet.getSheetNo() == null && parameterReadSheet.getSheetName() == null) {
                if (log.isDebugEnabled()) {
                    log.debug("The first is read by default.");
                }
                parameterReadSheet.setSheetNo(0);
            }
            boolean match = (parameterReadSheet.getSheetNo() != null
                    && parameterReadSheet.getSheetNo().equals(readSheet.getSheetNo()));
            if (!match) {
                String parameterSheetName = parameterReadSheet.getSheetName();
                if (!StringUtils.isEmpty(parameterSheetName)) {
                    String sheetName = readSheet.getSheetName();
                    if (sheetName != null) {
                        boolean autoTrim = getAutoTrimFlag(parameterReadSheet, analysisContext);
                        if (autoTrim) {
                            parameterSheetName = parameterSheetName.trim();
                            sheetName = sheetName.trim();
                        }

                        boolean autoStrip = getAutoStripFlag(parameterReadSheet, analysisContext);
                        if (autoStrip) {
                            parameterSheetName = StringUtils.strip(parameterSheetName);
                            sheetName = StringUtils.strip(sheetName);
                        }
                        match = parameterSheetName.equals(sheetName);
                    }
                }
            }
            if (match) {
                readSheet.copyBasicParameter(parameterReadSheet);
                return readSheet;
            }
        }
        return null;
    }

    /**
     * Get autoTrim flag
     *
     * @param parameterReadSheet actual sheet
     * @param analysisContext    Analysis Context
     * @return autoTrim flag
     */
    private static boolean getAutoTrimFlag(ReadSheet parameterReadSheet, AnalysisContext analysisContext) {
        return (parameterReadSheet.getAutoTrim() != null && parameterReadSheet.getAutoTrim())
                || (parameterReadSheet.getAutoTrim() == null
                        && analysisContext
                                .readWorkbookHolder()
                                .getGlobalConfiguration()
                                .getAutoTrim());
    }

    /**
     * Get autoStrip flag
     *
     * @param parameterReadSheet actual sheet
     * @param analysisContext    Analysis Context
     * @return autoStrip flag
     */
    private static boolean getAutoStripFlag(ReadSheet parameterReadSheet, AnalysisContext analysisContext) {
        return (parameterReadSheet.getAutoStrip() != null && parameterReadSheet.getAutoStrip())
                || (parameterReadSheet.getAutoStrip() == null
                        && analysisContext
                                .readWorkbookHolder()
                                .getGlobalConfiguration()
                                .getAutoStrip());
    }
}
