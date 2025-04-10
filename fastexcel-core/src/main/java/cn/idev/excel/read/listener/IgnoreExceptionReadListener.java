package cn.idev.excel.read.listener;

import cn.idev.excel.context.AnalysisContext;

/**
 * Interface to listen for read results
 *
 * @author Jiaju Zhuang
 */
public interface IgnoreExceptionReadListener<T> extends ReadListener<T> {

    /**
     * All listeners receive this method when any one Listener does an error report. If an exception is thrown here, the
     * entire read will terminate.
     *
     * @param exception
     * @param context
     * @throws Exception
     */
    @Override
    default void onException(Exception exception, AnalysisContext context) throws Exception {}

}
