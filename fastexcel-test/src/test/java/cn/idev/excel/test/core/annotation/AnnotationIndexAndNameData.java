package cn.idev.excel.test.core.annotation;

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
public class AnnotationIndexAndNameData {
    @ExcelProperty(value = "第四个", index = 4)
    private String index4;

    @ExcelProperty(value = "第二个")
    private String index2;

    @ExcelProperty(index = 0)
    private String index0;

    @ExcelProperty(value = "第一个", index = 1)
    private String index1;
}
