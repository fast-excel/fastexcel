package cn.idev.excel.analysis.v07.handlers;

import cn.idev.excel.metadata.data.FormulaData;
import cn.idev.excel.read.metadata.holder.xlsx.XlsxReadSheetHolder;
import cn.idev.excel.context.xlsx.XlsxReadContext;

import org.xml.sax.Attributes;

/**
 * Cell Handler
 *
 * @author jipengfei
 */
public class CellFormulaTagHandler extends AbstractXlsxTagHandler {

    @Override
    public void startElement(XlsxReadContext xlsxReadContext, String name, Attributes attributes) {
        XlsxReadSheetHolder xlsxReadSheetHolder = xlsxReadContext.xlsxReadSheetHolder();
        xlsxReadSheetHolder.setTempFormula(new StringBuilder());
    }

    @Override
    public void endElement(XlsxReadContext xlsxReadContext, String name) {
        XlsxReadSheetHolder xlsxReadSheetHolder = xlsxReadContext.xlsxReadSheetHolder();
        FormulaData formulaData = new FormulaData();
        formulaData.setFormulaValue(xlsxReadSheetHolder.getTempFormula().toString());
        xlsxReadSheetHolder.getTempCellData().setFormulaData(formulaData);
    }

    @Override
    public void characters(XlsxReadContext xlsxReadContext, char[] ch, int start, int length) {
        xlsxReadContext.xlsxReadSheetHolder().getTempFormula().append(ch, start, length);
    }
}
