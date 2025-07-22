package cn.idev.excel.write.handler;

import java.util.ArrayList;
import java.util.List;

import cn.idev.excel.support.ExcelTypeEnum;
import cn.idev.excel.write.handler.impl.DefaultRowWriteHandler;
import cn.idev.excel.write.handler.impl.DimensionWorkbookWriteHandler;
import cn.idev.excel.write.handler.impl.FillStyleCellWriteHandler;
import cn.idev.excel.write.style.DefaultStyle;

/**
 * Load default handler
 *
 * @author Jiaju Zhuang
 */
public class DefaultWriteHandlerLoader {

    public static final List<WriteHandler> DEFAULT_WRITE_HANDLER_LIST = new ArrayList<>();

    static {
        DEFAULT_WRITE_HANDLER_LIST.add(new DimensionWorkbookWriteHandler());
        DEFAULT_WRITE_HANDLER_LIST.add(new DefaultRowWriteHandler());
        DEFAULT_WRITE_HANDLER_LIST.add(new FillStyleCellWriteHandler());
    }

    /**
     * Load default handler
     *
     * @return
     */
    public static List<WriteHandler> loadDefaultHandler(Boolean useDefaultStyle, ExcelTypeEnum excelType) {
        List<WriteHandler> handlerList = new ArrayList<>();
        switch (excelType) {
            case XLSX:
                handlerList.add(new DimensionWorkbookWriteHandler());
                handlerList.add(new DefaultRowWriteHandler());
                handlerList.add(new FillStyleCellWriteHandler());
                if (useDefaultStyle) {
                    handlerList.add(new DefaultStyle());
                }
                break;
            case XLS:
                handlerList.add(new DefaultRowWriteHandler());
                handlerList.add(new FillStyleCellWriteHandler());
                if (useDefaultStyle) {
                    handlerList.add(new DefaultStyle());
                }
                break;
            case CSV:
                handlerList.add(new DefaultRowWriteHandler());
                handlerList.add(new FillStyleCellWriteHandler());
                break;
            default:
                break;
        }
        return handlerList;
    }

}
