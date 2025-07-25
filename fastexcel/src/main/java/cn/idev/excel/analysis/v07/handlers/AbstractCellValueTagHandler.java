package cn.idev.excel.analysis.v07.handlers;

import cn.idev.excel.context.xlsx.XlsxReadContext;

/**
 * Cell Value Handler
 *
 */
public abstract class AbstractCellValueTagHandler extends AbstractXlsxTagHandler {

    @Override
    public void characters(XlsxReadContext xlsxReadContext, char[] ch, int start, int length) {
        xlsxReadContext.xlsxReadSheetHolder().getTempData().append(ch, start, length);
    }
}
