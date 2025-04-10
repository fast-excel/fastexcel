package cn.idev.excel.test.temp;

import cn.idev.excel.annotation.write.style.ContentRowHeight;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
@ContentRowHeight(30)
public class TempFillData {
    private String name;
    private double number;
}
