package cn.idev.excel.writesheet;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class WriteSheetData {
    @ExcelProperty("字符串标题")
    private String string;
}
