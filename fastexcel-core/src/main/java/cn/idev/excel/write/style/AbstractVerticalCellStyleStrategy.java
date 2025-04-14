package cn.idev.excel.write.style;

import cn.idev.excel.write.handler.context.CellWriteHandlerContext;
import cn.idev.excel.metadata.Head;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.write.metadata.style.WriteCellStyle;

/**
 * Use the same style for the column
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractVerticalCellStyleStrategy extends AbstractCellStyleStrategy {

    @Override
    protected void setHeadCellStyle(CellWriteHandlerContext context) {
        if (stopProcessing(context)) {
            return;
        }
        WriteCellData<?> cellData = context.getFirstCellData();
        WriteCellStyle.merge(headCellStyle(context), cellData.getOrCreateStyle());
    }

    @Override
    protected void setContentCellStyle(CellWriteHandlerContext context) {
        if (context.getFirstCellData() == null) {
            return;
        }
        WriteCellData<?> cellData = context.getFirstCellData();
        WriteCellStyle.merge(contentCellStyle(context), cellData.getOrCreateStyle());
    }

    /**
     * Returns the column width corresponding to each column head
     *
     * @param context
     * @return
     */
    protected WriteCellStyle headCellStyle(CellWriteHandlerContext context) {
        return headCellStyle(context.getHeadData());
    }

    /**
     * Returns the column width corresponding to each column head
     *
     * @param head Nullable
     * @return
     */
    protected WriteCellStyle headCellStyle(Head head) {
        return null;
    }

    /**
     * Returns the column width corresponding to each column head.
     *
     * @param context
     * @return
     */
    protected WriteCellStyle contentCellStyle(CellWriteHandlerContext context) {
        return contentCellStyle(context.getHeadData());
    }

    /**
     * Returns the column width corresponding to each column head
     *
     * @param head Nullable
     * @return
     */
    protected WriteCellStyle contentCellStyle(Head head) {
        return null;
    }

    protected boolean stopProcessing(CellWriteHandlerContext context) {
        if (context.getFirstCellData() == null) {
            return true;
        }
        return context.getHeadData() == null;
    }
}
