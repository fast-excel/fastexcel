package cn.idev.excel.test.core.fill.style;

import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
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
