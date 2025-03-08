package cn.idev.excel.write.merge;

import cn.idev.excel.write.handler.RowWriteHandler;
import cn.idev.excel.write.handler.context.RowWriteHandlerContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @Description 指定列数据相同时进行单元格合并
 * @Author Zhuojianlong
 * @Date 2025/3/8
 */
public class DynamicMergeStrategy implements RowWriteHandler {
    private final int columnIndex;
    private final int columnExtend;
    private final int dataSize;
    Deque<MergeRow> rowStack = new ArrayDeque<>();

    public DynamicMergeStrategy(int columnIndex,int dataSize) {
        this(columnIndex,1,dataSize);
    }
    public DynamicMergeStrategy(int columnIndex, int columnExtend,int dataSize) {
        if (columnExtend < 1) {
            throw new IllegalArgumentException("ColumnExtend must be greater than 1");
        }
        if (columnIndex < 0) {
            throw new IllegalArgumentException("ColumnIndex must be greater than 0");
        }
        this.columnIndex = columnIndex;
        this.columnExtend = columnExtend;
        this.dataSize = dataSize;

    }
    @Override
    public void afterRowDispose(RowWriteHandlerContext context) {
        if (context.getHead() || context.getRelativeRowIndex() == null) {
            return;
        }
        Row row = context.getRow();
        rowStack.push(new MergeRow(row, context.getRelativeRowIndex()));
        if(context.getRelativeRowIndex()==(dataSize-1)){
            while (!rowStack.isEmpty()){
                MergeRow lastRow  = rowStack.pop();
                while (!rowStack.isEmpty()){
                    MergeRow prevRow = rowStack.pop();
                    if(prevRow.getRelativeRowIndex().equals(0)){
                        CellRangeAddress cellRangeAddress = new CellRangeAddress(prevRow.getRow().getRowNum(),
                            lastRow.getRow().getRowNum(), columnIndex, columnIndex + columnExtend - 1);
                        context.getWriteSheetHolder().getSheet().addMergedRegionUnsafe(cellRangeAddress);
                    }else {
                        if (!prevRow.getRow().getCell(columnIndex).getStringCellValue().equals(lastRow.getRow().getCell(columnIndex).getStringCellValue())) {
                            CellRangeAddress cellRangeAddress = new CellRangeAddress(prevRow.getRow().getRowNum()+1,
                                lastRow.getRow().getRowNum(), columnIndex, columnIndex + columnExtend - 1);
                            context.getWriteSheetHolder().getSheet().addMergedRegionUnsafe(cellRangeAddress);
                            rowStack.push(prevRow);
                            break;
                        }
                    }

                }

            }
        }

    }

    @Data
    @AllArgsConstructor
    public static  class MergeRow{
        private Row row;
        private Integer relativeRowIndex;
    }
}
