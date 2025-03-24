package cn.idev.excel.test.demo.read;

import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 针对通过泛型指定头部类型的数据监听器样例
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
        // 执行业务逻辑
    }
    
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 执行收尾工作
    }
    
    public static <T> GenericHeaderTypeDataListener<T> build(Class<T> excelHeaderClass) {
        return new GenericHeaderTypeDataListener<>(excelHeaderClass);
    }
    
    public Class<T> getHeaderClass() {
        return headerClass;
    }
}
