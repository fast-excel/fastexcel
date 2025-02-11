package cn.idev.excel.test.core.handler;

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
public class WriteHandlerData {
    @ExcelProperty("姓名")
    private String name;
}
