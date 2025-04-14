package cn.idev.excel.test.temp.simple;

import cn.idev.excel.write.handler.SheetWriteHandler;
import cn.idev.excel.write.metadata.holder.WriteSheetHolder;
import cn.idev.excel.write.metadata.holder.WriteWorkbookHolder;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Jiaju Zhuang
 */
@Slf4j
public class WriteHandler implements SheetWriteHandler {

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder,
        WriteSheetHolder writeSheetHolder) {
        log.info("锁住");
        writeSheetHolder.getSheet().protectSheet("edit");
    }
}
