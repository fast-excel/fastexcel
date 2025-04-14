package cn.idev.excel.test.core.head;

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
public class ComplexHeadData {
    @ExcelProperty({"顶格", "顶格", "两格"})
    private String string0;
    @ExcelProperty({"顶格", "顶格", "两格"})
    private String string1;
    @ExcelProperty({"顶格", "四联", "四联"})
    private String string2;
    @ExcelProperty({"顶格", "四联", "四联"})
    private String string3;
    @ExcelProperty({"顶格"})
    private String string4;
}
