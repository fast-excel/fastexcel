package cn.idev.excel.test.core.head;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class MaxHeadSizeData {
    @ExcelProperty({"名称"})
    private String string0;
    @ExcelProperty({"区域"})
    private String string1;
    @ExcelProperty({"所属项目"})
    private String string2;
    @ExcelProperty({"所属维保单位"})
    private String string3;
    @ExcelProperty({"序列号"})
    private String string4;
    @ExcelProperty({"到期时间"})
    private String string5;
}
