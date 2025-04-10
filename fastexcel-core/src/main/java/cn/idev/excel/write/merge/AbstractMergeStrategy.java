package cn.idev.excel.write.merge;

import cn.idev.excel.write.handler.CellWriteHandler;
import cn.idev.excel.write.handler.context.CellWriteHandlerContext;
import cn.idev.excel.metadata.Head;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * Merge strategy
 *
 * @author Jiaju Zhuang
 */
public abstract class AbstractMergeStrategy implements CellWriteHandler {

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        if (context.getHead()) {
            return;
        }
        merge(context.getWriteSheetHolder().getSheet(), context.getCell(), context.getHeadData(),
            context.getRelativeRowIndex());
    }

    /**
     * merge
     *
     * @param sheet
     * @param cell
     * @param head
     * @param relativeRowIndex
     */
    protected abstract void merge(Sheet sheet, Cell cell, Head head, Integer relativeRowIndex);
}
