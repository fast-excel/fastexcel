package cn.idev.excel.test.temp.bug;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 */
@Getter
@Setter
@EqualsAndHashCode
public class HeadType {

    /**
     * 任务id
     */
    @ExcelProperty("任务ID")
    private Integer id;

    @ExcelProperty(value = "备注1")
    private String firstRemark;

    @ExcelProperty(value = "备注2")
    private String secRemark;
}
