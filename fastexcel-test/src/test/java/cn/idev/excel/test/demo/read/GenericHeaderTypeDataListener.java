package cn.idev.excel.test.demo.read;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;

/**
 * A data listener example that specifies the header type through generics.
 *
 * @param <T>
 */
@Slf4j
public class GenericHeaderTypeDataListener<T> implements ReadListener<T> {

    private final Class<T> headerClass;

    private GenericHeaderTypeDataListener(Class<T> headerClass) {
        this.headerClass = headerClass;
    }


    @Override
    public void invoke(T data, AnalysisContext context) {
        log.info("data:{}", data);
        // Execute business logic
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // Perform cleanup tasks
    }

    public static <T> GenericHeaderTypeDataListener<T> build(Class<T> excelHeaderClass) {
        return new GenericHeaderTypeDataListener<>(excelHeaderClass);
    }

    public Class<T> getHeaderClass() {
        return headerClass;
    }
}
