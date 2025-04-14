package cn.idev.excel.test.demo.write;

import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.HeadFontStyle;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Font;

/**
 * Basic data class for test color
 *
 **/
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ColorDemoData {


    @ExcelProperty("姓名")
    private String name;
    @ExcelProperty("年龄")
    @HeadFontStyle(color = Font.COLOR_RED)
    private Integer age;
    @ExcelProperty("性别")
    private String sex;
}
