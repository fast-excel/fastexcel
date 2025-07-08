package cn.idev.excel.read.processor;

import cn.idev.excel.read.metadata.ValidateError;
import cn.idev.excel.read.metadata.holder.ValidateErrorHolder;

import java.util.List;
import java.util.Map;

/**
 * handle the error message as text
 *
 * @author wangmeng
 */
public class TextErrorHandler implements ValidateErrorHandler<String> {

    private final static String ERROR_STRING = "error message:";
    private final static String LINE_ERROR_STRING = " Line %s: %s";

    public final static TextErrorHandler INSTANCE = new TextErrorHandler();

    @Override
    public String handleError(ValidateErrorHolder errorHolder) {
        Map<Integer, List<ValidateError>> errorMap = errorHolder.getError();
        if (errorMap.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder(ERROR_STRING);
        errorMap.forEach((index, errorList) -> {
            sb.append(String.format(LINE_ERROR_STRING, index, mergeText(errorList)));
        });
        return sb.toString();
    }
}
