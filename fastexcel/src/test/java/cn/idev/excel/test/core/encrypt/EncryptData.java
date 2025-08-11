package cn.idev.excel.test.core.encrypt;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 */
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class EncryptData {
    @ExcelProperty("姓名")
    private String name;
}
