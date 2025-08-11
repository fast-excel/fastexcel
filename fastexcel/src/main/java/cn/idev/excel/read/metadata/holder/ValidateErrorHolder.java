package cn.idev.excel.read.metadata.holder;

import cn.idev.excel.read.metadata.ValidateError;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * validate read listener
 *
 * @author wangmeng
 */
public interface ValidateErrorHolder {

    /**
     * get a map of error messages
     *
     * @return {@link List }<{@link ValidateError }>
     */
    Map<Integer, List<ValidateError>> getError();

    /**
     * add an error message
     *
     * @param rowNum   rowNum,
     * @param message  error message
     */
    void addError(Integer rowNum, String message);


    /**
     * get the source file for reading
     *
     * @return file for reading
     */
    File getSourceFile();

    /**
     * get sheetNo
     * @return sheetNo
     */
    Integer getSheetNo();
}
