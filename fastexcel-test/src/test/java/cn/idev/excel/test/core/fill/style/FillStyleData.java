package cn.idev.excel.test.core.fill.style;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class FillStyleData {
    
    private String name;
    
    private Double number;
    
    private Date date;
    
    private String empty;
}
