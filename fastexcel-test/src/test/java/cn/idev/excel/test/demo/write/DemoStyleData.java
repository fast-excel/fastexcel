package cn.idev.excel.test.demo.write;

import java.util.Date;

import cn.idev.excel.enums.poi.FillPatternTypeEnum;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ContentFontStyle;
import cn.idev.excel.annotation.write.style.ContentStyle;
import cn.idev.excel.annotation.write.style.HeadFontStyle;
import cn.idev.excel.annotation.write.style.HeadStyle;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 样式的数据类
 *
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
// 头背景设置成红色 IndexedColors.RED.getIndex()
@HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 10)
// 头字体设置成20
@HeadFontStyle(fontHeightInPoints = 20)
// 内容的背景设置成绿色 IndexedColors.GREEN.getIndex()
@ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 17)
// 内容字体设置成20
@ContentFontStyle(fontHeightInPoints = 20)
public class DemoStyleData {
    // 字符串的头背景设置成粉红 IndexedColors.PINK.getIndex()
    @HeadStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 14)
    // 字符串的头字体设置成20
    @HeadFontStyle(fontHeightInPoints = 30)
    // 字符串的内容的背景设置成天蓝 IndexedColors.SKY_BLUE.getIndex()
    @ContentStyle(fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND, fillForegroundColor = 40)
    // 字符串的内容字体设置成20
    @ContentFontStyle(fontHeightInPoints = 30)
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;
}
