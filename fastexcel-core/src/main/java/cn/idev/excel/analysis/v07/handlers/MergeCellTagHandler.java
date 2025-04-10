package cn.idev.excel.analysis.v07.handlers;

import cn.idev.excel.enums.CellExtraTypeEnum;
import cn.idev.excel.metadata.CellExtra;
import cn.idev.excel.util.StringUtils;
import cn.idev.excel.context.xlsx.XlsxReadContext;
import org.xml.sax.Attributes;

import cn.idev.excel.constant.ExcelXmlConstants;

/**
 * Cell Handler
 *
 * @author Jiaju Zhuang
 */
public class MergeCellTagHandler extends AbstractXlsxTagHandler {

    @Override
    public boolean support(XlsxReadContext xlsxReadContext) {
        return xlsxReadContext.readWorkbookHolder().getExtraReadSet().contains(CellExtraTypeEnum.MERGE);
    }

    @Override
    public void startElement(XlsxReadContext xlsxReadContext, String name, Attributes attributes) {
        String ref = attributes.getValue(ExcelXmlConstants.ATTRIBUTE_REF);
        if (StringUtils.isEmpty(ref)) {
            return;
        }
        CellExtra cellExtra = new CellExtra(CellExtraTypeEnum.MERGE, null, ref);
        xlsxReadContext.readSheetHolder().setCellExtra(cellExtra);
        xlsxReadContext.analysisEventProcessor().extra(xlsxReadContext);
    }

}
