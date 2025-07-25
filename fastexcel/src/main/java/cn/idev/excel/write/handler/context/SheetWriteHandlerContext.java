package cn.idev.excel.write.handler.context;

import cn.idev.excel.context.WriteContext;
import cn.idev.excel.write.metadata.holder.WriteSheetHolder;
import cn.idev.excel.write.metadata.holder.WriteWorkbookHolder;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * sheet context
 *
 *
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class SheetWriteHandlerContext {
    /**
     * write context
     */
    private WriteContext writeContext;
    /**
     * workbook
     */
    private WriteWorkbookHolder writeWorkbookHolder;
    /**
     * sheet
     */
    private WriteSheetHolder writeSheetHolder;
}
