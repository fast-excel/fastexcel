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
                        boolean autoStrip = ParameterUtil.getAutoStripFlag(parameterReadSheet, analysisContext);
                        boolean autoTrim = ParameterUtil.getAutoTrimFlag(parameterReadSheet, analysisContext);

                        if (autoStrip) {
                            parameterSheetName = StringUtils.strip(parameterSheetName);
                            sheetName = StringUtils.strip(sheetName);
                        } else if (autoTrim) {
                            parameterSheetName = parameterSheetName.trim();
                            sheetName = sheetName.trim();
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
}
