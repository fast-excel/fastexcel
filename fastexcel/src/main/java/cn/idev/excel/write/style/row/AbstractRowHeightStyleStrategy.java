package cn.idev.excel.write.style.row;

import cn.idev.excel.write.handler.RowWriteHandler;
import cn.idev.excel.write.handler.context.RowWriteHandlerContext;

import org.apache.poi.ss.usermodel.Row;

/**
 * Set the row height strategy
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractRowHeightStyleStrategy implements RowWriteHandler {
    @Override
    public void afterRowDispose(RowWriteHandlerContext context) {
        if (context.getHead() == null) {
            return;
        }
        if (context.getHead()) {
            setHeadColumnHeight(context.getRow(), context.getRelativeRowIndex());
        } else {
            setContentColumnHeight(context.getRow(), context.getRelativeRowIndex());
        }
    }

    /**
     * Sets the height of header
     *
     * @param row
     * @param relativeRowIndex
     */
    protected abstract void setHeadColumnHeight(Row row, int relativeRowIndex);

    /**
     * Sets the height of content
     *
     * @param row
     * @param relativeRowIndex
     */
    protected abstract void setContentColumnHeight(Row row, int relativeRowIndex);

}
