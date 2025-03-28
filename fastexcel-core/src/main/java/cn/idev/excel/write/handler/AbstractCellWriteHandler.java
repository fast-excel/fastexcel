package cn.idev.excel.write.handler;

import cn.idev.excel.metadata.Head;
import cn.idev.excel.metadata.data.WriteCellData;
import cn.idev.excel.write.metadata.holder.WriteSheetHolder;
import cn.idev.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.util.List;

/**
 * Abstract cell write handler
 *
 * @author Jiaju Zhuang
 * @deprecated Please use it directly {@link CellWriteHandler}
 **/
@Deprecated
public abstract class AbstractCellWriteHandler implements CellWriteHandler {
    
    @Override
    public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
            Head head, Integer columnIndex, Integer relativeRowIndex, Boolean isHead) {
        
    }
    
    @Override
    public void afterCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Cell cell,
            Head head, Integer relativeRowIndex, Boolean isHead) {
        
    }
    
    @Override
    public void afterCellDataConverted(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
            WriteCellData<?> cellData, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
    }
    
    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder,
            List<WriteCellData<?>> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        
    }
}
