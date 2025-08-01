package cn.idev.excel.write.handler.impl;

import cn.idev.excel.write.handler.RowWriteHandler;
import cn.idev.excel.write.metadata.holder.WriteSheetHolder;
import cn.idev.excel.write.metadata.holder.WriteTableHolder;
import java.util.*;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author zz_zhi
 */
@Getter
public class HiddenRowWriteHandler implements RowWriteHandler {
    /***
     * sheetNo
     */
    private final Integer sheetNo;

    /***
     * sheetName
     */
    private final String sheetName;

    private Collection<Integer> hiddenColumns = new ArrayList<>();

    public HiddenRowWriteHandler() {
        this.sheetNo = null;
        this.sheetName = null;
    }

    public HiddenRowWriteHandler(Integer sheetNo, String sheetName) {
        this.sheetNo = sheetNo;
        this.sheetName = sheetName;
    }

    public HiddenRowWriteHandler(Integer sheetNo, String sheetName, Collection<Integer> hiddenColumns) {
        if (CollectionUtils.isNotEmpty(hiddenColumns)) {
            this.hiddenColumns = hiddenColumns;
        }
        this.sheetNo = sheetNo;
        this.sheetName = sheetName;
    }

    public HiddenRowWriteHandler addHiddenColumns(Collection<Integer> hiddenColumns) {
        this.hiddenColumns.addAll(hiddenColumns);
        return this;
    }

    public HiddenRowWriteHandler addHiddenColumns(Integer hiddenColumn) {
        this.hiddenColumns.add(hiddenColumn);
        return this;
    }

    @Override
    public void afterRowDispose(
            WriteSheetHolder writeSheetHolder,
            WriteTableHolder writeTableHolder,
            Row row,
            Integer relativeRowIndex,
            Boolean isHead) {
        boolean isSheetName =
                (null == sheetName || StringUtils.equals(this.sheetName, writeSheetHolder.getSheetName()));
        boolean isSheetNo = (null == sheetNo || Objects.equals(this.sheetNo, writeSheetHolder.getSheetNo()));
        if (isSheetName && isSheetNo) {
            if (hiddenColumns.contains(relativeRowIndex)) {
                row.setZeroHeight(true);
            }
        }
    }
}
