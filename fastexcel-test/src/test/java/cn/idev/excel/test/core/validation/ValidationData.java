package cn.idev.excel.test.core.validation;

import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.validation.*;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Validation test data
 *
 * @author TianYufeng
 * @author FastExcel Team
 * @since 1.2.1
 * @date 2025-07-30
 */
@Getter
@Setter
@EqualsAndHashCode
public class ValidationData {
    
    @ColumnWidth(10)
    @ExcelProperty("序号")
    @AutoIncrementId(start = 1)
    private Integer id;
    
    @ColumnWidth(20)
    @ExcelProperty("项目名称")
    @ExcelRequired(message = "项目名称不能为空")
    private String projectName;
    
    @ColumnWidth(15)
    @ExcelProperty("项目类型")
    @ExcelSelect({"基础设施", "房屋建筑", "市政工程", "水利工程", "其他"})
    @ExcelRequired(message = "请选择项目类型")
    private String projectType;
    
    @ColumnWidth(15)
    @ExcelProperty("项目金额(万元)")
    @ExcelNumberValidation(
        min = 0.0,
        max = 999999.99,
        decimalPlaces = 2,
        errorTitle = "金额输入错误",
        errorContent = "请输入0到999999.99之间的金额，最多2位小数",
        promptTitle = "金额输入",
        promptContent = "请输入项目金额，单位：万元",
        unit = "万元"
    )
    @ExcelRequired(message = "项目金额不能为空")
    private BigDecimal projectAmount;
    
    @ColumnWidth(12)
    @ExcelProperty("项目进度(%)")
    @ExcelNumberValidation(
        min = 0.0,
        max = 100.0,
        decimalPlaces = 1,
        errorTitle = "进度输入错误",
        errorContent = "请输入0到100之间的百分比数值",
        promptTitle = "进度输入",
        promptContent = "请输入项目完成进度，范围：0-100%",
        unit = "%"
    )
    private BigDecimal progress;
    
    @ColumnWidth(15)
    @ExcelProperty("开始日期")
    @ExcelDateValidation(
        format = "yyyy-MM-dd",
        minDate = "2020-01-01",
        maxDate = "2030-12-31",
        errorTitle = "日期输入错误",
        errorContent = "请输入2020-01-01到2030-12-31之间的日期",
        promptTitle = "日期输入",
        promptContent = "请输入项目开始日期，格式：yyyy-MM-dd"
    )
    @ExcelRequired(message = "开始日期不能为空")
    private Date startDate;
    
    @ColumnWidth(15)
    @ExcelProperty("结束日期")
    @ExcelDateValidation(
        format = "yyyy-MM-dd",
        minDate = "2020-01-01",
        maxDate = "2030-12-31"
    )
    private Date endDate;
    
    @ColumnWidth(20)
    @ExcelProperty("负责部门")
    @ExcelDefaultValue(value = "工程部")
    private String department;
    
    @ColumnWidth(30)
    @ExcelProperty("备注")
    private String remark;
}
