package cn.idev.excel.test.temp.write;

import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ContentStyle;
import cn.idev.excel.annotation.write.style.HeadStyle;
import cn.idev.excel.enums.BooleanEnum;

import lombok.Data;

@Data
//@Accessors(chain = true)
public class TempWriteData {
    private String name1;

    @ExcelProperty("     换行\r\n \\ \r\n的名字")
    @HeadStyle(wrapped = BooleanEnum.TRUE)
    @ContentStyle(wrapped = BooleanEnum.TRUE)
    private String name;
}
