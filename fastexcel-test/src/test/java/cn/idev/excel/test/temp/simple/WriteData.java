package cn.idev.excel.test.temp.simple;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * write data
 *
 * @author Jiaju Zhuang
 **/
@Getter
@Setter
@EqualsAndHashCode
public class WriteData {
    
    //    @ContentStyle(locked = true)
    private Date dd;
    
    //    @ContentStyle(locked = false)
    private float f1;
}
