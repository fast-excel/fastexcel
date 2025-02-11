package cn.idev.excel.write.handler.impl;

import cn.idev.excel.write.handler.CellWriteHandler;
import cn.idev.excel.constant.OrderConstant;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.util.BooleanUtils;
import cn.idev.excel.write.handler.context.CellWriteHandlerContext;
import cn.idev.excel.write.metadata.holder.WriteWorkbookHolder;
import cn.idev.excel.write.metadata.style.WriteCellStyle;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * fill cell style.
 *
 * @author Jiaju Zhuang
 */
@Slf4j
public class FillStyleCellWriteHandler implements CellWriteHandler {

    @Override
    public int order() {
        return OrderConstant.FILL_STYLE;
    }

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        if (BooleanUtils.isTrue(context.getIgnoreFillStyle())) {
            return;
        }

        WriteCellData<?> cellData = context.getFirstCellData();
        if (cellData == null) {
            return;
        }
        WriteCellStyle writeCellStyle = cellData.getWriteCellStyle();
        CellStyle originCellStyle = cellData.getOriginCellStyle();
        if (writeCellStyle == null && originCellStyle == null) {
            return;
        }
        WriteWorkbookHolder writeWorkbookHolder = context.getWriteWorkbookHolder();
        context.getCell().setCellStyle(writeWorkbookHolder.createCellStyle(writeCellStyle, originCellStyle));
    }

}
