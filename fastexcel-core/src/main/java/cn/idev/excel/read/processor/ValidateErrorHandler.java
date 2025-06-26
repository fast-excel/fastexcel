package cn.idev.excel.read.processor;

import cn.idev.excel.read.metadata.ValidateError;
import cn.idev.excel.read.metadata.holder.ValidateErrorHolder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handle error messages
 * the source of the error is
 * {@link cn.idev.excel.read.metadata.holder.ValidateErrorHolder}
 *
 * @author wangmeng
 */
public interface ValidateErrorHandler<T> {


    T handleError(ValidateErrorHolder errorHolder);


    default String mergeText(List<ValidateError> list) {
        return list.stream().map(each->each.getMessage() + ";").collect(Collectors.joining(""));
    }


}
