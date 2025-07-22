package cn.idev.excel.event;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.metadata.CellExtra;
import cn.idev.excel.read.listener.ReadListener;

/**
 * Receives the return of each piece of data parsed
 *
 * @author jipengfei
 * @deprecated Use directly {@link ReadListener}
 */
@Deprecated
public abstract class AbstractIgnoreExceptionReadListener<T> implements ReadListener<T> {

    /**
     * All listeners receive this method when any one Listener does an error report. If an exception is thrown here, the
     * entire read will terminate.
     *
     * @param exception
     * @param context
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {}

    /**
     * The current method is called when extra information is returned
     *
     * @param extra   extra information
     * @param context analysis context
     */
    @Override
    public void extra(CellExtra extra, AnalysisContext context) {}

    @Override
    public boolean hasNext(AnalysisContext context) {
        return true;
    }
}
