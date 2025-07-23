package cn.idev.excel.test.temp;

import cn.idev.excel.annotation.ExcelProperty;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 基础数据类
 *
 *
 **/
@Getter
@Setter
@EqualsAndHashCode
public class DemoData3 {
    @ExcelProperty("日期时间标题")
    private LocalDateTime localDateTime;
}
