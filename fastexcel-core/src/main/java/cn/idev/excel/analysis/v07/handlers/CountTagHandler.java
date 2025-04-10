package cn.idev.excel.analysis.v07.handlers;

import cn.idev.excel.constant.ExcelXmlConstants;
import cn.idev.excel.util.PositionUtils;
import cn.idev.excel.context.xlsx.XlsxReadContext;

import org.xml.sax.Attributes;

/**
 * Cell Handler
 *
 * @author jipengfei
 */
public class CountTagHandler extends AbstractXlsxTagHandler {

    @Override
    public void startElement(XlsxReadContext xlsxReadContext, String name, Attributes attributes) {
        String d = attributes.getValue(ExcelXmlConstants.ATTRIBUTE_REF);
        String totalStr = d.substring(d.indexOf(":") + 1);
        xlsxReadContext.readSheetHolder().setApproximateTotalRowNumber(PositionUtils.getRow(totalStr) + 1);
    }

}
