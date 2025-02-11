package cn.idev.excel.analysis.v07.handlers;

import java.util.Optional;

import cn.idev.excel.enums.CellExtraTypeEnum;
import cn.idev.excel.metadata.CellExtra;
import cn.idev.excel.util.StringUtils;
import cn.idev.excel.context.xlsx.XlsxReadContext;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.PackageRelationshipCollection;
import org.xml.sax.Attributes;

import cn.idev.excel.constant.ExcelXmlConstants;

/**
 * Cell Handler
 *
 * @author Jiaju Zhuang
 */
public class HyperlinkTagHandler extends AbstractXlsxTagHandler {

    @Override
    public boolean support(XlsxReadContext xlsxReadContext) {
        return xlsxReadContext.readWorkbookHolder().getExtraReadSet().contains(CellExtraTypeEnum.HYPERLINK);
    }

    @Override
    public void startElement(XlsxReadContext xlsxReadContext, String name, Attributes attributes) {
        String ref = attributes.getValue(ExcelXmlConstants.ATTRIBUTE_REF);
        if (StringUtils.isEmpty(ref)) {
            return;
        }
        // Hyperlink has 2 case:
        // case 1，In the 'location' tag
        String location = attributes.getValue(ExcelXmlConstants.ATTRIBUTE_LOCATION);
        if (location != null) {
            CellExtra cellExtra = new CellExtra(CellExtraTypeEnum.HYPERLINK, location, ref);
            xlsxReadContext.readSheetHolder().setCellExtra(cellExtra);
            xlsxReadContext.analysisEventProcessor().extra(xlsxReadContext);
            return;
        }
        // case 2, In the 'r:id' tag, Then go to 'PackageRelationshipCollection' to get inside
        String rId = attributes.getValue(ExcelXmlConstants.ATTRIBUTE_RID);
        PackageRelationshipCollection packageRelationshipCollection = xlsxReadContext.xlsxReadSheetHolder()
            .getPackageRelationshipCollection();
        if (rId == null || packageRelationshipCollection == null) {
            return;
        }
        Optional.ofNullable(packageRelationshipCollection.getRelationshipByID(rId))
            .map(PackageRelationship::getTargetURI)
            .ifPresent(uri -> {
                CellExtra cellExtra = new CellExtra(CellExtraTypeEnum.HYPERLINK, uri.toString(), ref);
                xlsxReadContext.readSheetHolder().setCellExtra(cellExtra);
                xlsxReadContext.analysisEventProcessor().extra(xlsxReadContext);
            });
    }

}
