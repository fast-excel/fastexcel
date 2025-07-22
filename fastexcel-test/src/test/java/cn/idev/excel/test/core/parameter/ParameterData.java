package cn.idev.excel.test.core.parameter;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
@EqualsAndHashCode
public class ParameterData {
    @ExcelProperty("姓名")
    private String name;
}
