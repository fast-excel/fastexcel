package cn.idev.excel.test.demo.read;

import cn.idev.excel.annotation.ExcelProperty;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Basic data class
 *
 *
 **/
@Getter
@Setter
@EqualsAndHashCode
public class IndexOrNameData {
    /**
     * Force reading the third column. It is not recommended to use both index and name at the same time.
     * Either use index only or use name only for matching within a single object.
     */
    @ExcelProperty(index = 2)
    private Double doubleData;
    /**
     * Match by name. Note that if the name is duplicated, only one field will be populated with data.
     */
    @ExcelProperty("String")
    private String string;

    @ExcelProperty("Date")
    private Date date;
}
