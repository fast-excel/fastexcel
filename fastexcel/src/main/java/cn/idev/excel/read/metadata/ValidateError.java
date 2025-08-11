package cn.idev.excel.read.metadata;

import cn.idev.excel.exception.ExcelDataConvertException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * validation error message
 *
 * @author wangmeng
 */
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class ValidateError {

    /**
     * rowNum
     */
    private Integer rowNum;

    /**
     * headName
     */
    private String headName;

    /**
     * message
     */
    private String message;

    /**
     * Record whether it was caused by  {@link ExcelDataConvertException}
     */
    private boolean convertError = false;


    public ValidateError(Integer rowNum, String headName, String message) {
        this.rowNum = rowNum;
        this.headName = headName;
        this.message = message;
    }


    public ValidateError(Integer rowNum, String message) {
        this.rowNum = rowNum;
        this.message = message;
    }
}
