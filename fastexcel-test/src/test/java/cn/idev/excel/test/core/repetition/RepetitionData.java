package cn.idev.excel.test.core.repetition;

import cn.idev.excel.annotation.ExcelProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class RepetitionData {
    @ExcelProperty("字符串")
    private String string;
}
