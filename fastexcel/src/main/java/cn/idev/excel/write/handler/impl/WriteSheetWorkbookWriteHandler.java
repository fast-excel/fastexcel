package cn.idev.excel.write.handler.impl;

import cn.idev.excel.constant.OrderConstant;
import cn.idev.excel.util.StringUtils;
import cn.idev.excel.write.handler.WorkbookWriteHandler;
import cn.idev.excel.write.metadata.holder.WriteSheetHolder;
import cn.idev.excel.write.metadata.holder.WriteWorkbookHolder;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections4.MapUtils;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Set the order of each worksheet after completing workbook processing.
 */
public class WriteSheetWorkbookWriteHandler implements WorkbookWriteHandler {

    @Override
    public int order() {
        return OrderConstant.SHEET_ORDER;
    }

    @Override
    public void afterWorkbookDispose(WriteWorkbookHolder writeWorkbookHolder) {
        if (writeWorkbookHolder == null || writeWorkbookHolder.getWorkbook() == null) {
            return;
        }
        Map<Integer, WriteSheetHolder> writeSheetHolderMap = writeWorkbookHolder.getHasBeenInitializedSheetIndexMap();
        if (MapUtils.isEmpty(writeSheetHolderMap)) {
            return;
        }
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        // sort by sheetNo.
        List<Integer> sheetNoSortList =
                writeSheetHolderMap.keySet().stream().sorted().collect(Collectors.toList());

        int pos = 0;
        for (Integer key : sheetNoSortList) {
            WriteSheetHolder writeSheetHolder = writeSheetHolderMap.get(key);
            if (writeSheetHolder == null
                    || writeSheetHolder.getWriteSheet() == null
                    || writeSheetHolder.getSheetNo() == null
                    || StringUtils.isBlank(writeSheetHolder.getSheetName())) {
                continue;
            }
            // set the order of sheet.
            workbook.setSheetOrder(writeSheetHolder.getSheetName(), pos++);
        }
    }
}
