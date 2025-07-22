package cn.idev.excel.test.demo.read;

import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Basic data class. The order here is consistent with the order in the Excel file.
 *
 *
 **/
@Getter
@Setter
@EqualsAndHashCode
public class ExceptionDemoData {

    /**
     * Using a Date to receive a string will definitely cause an error.
     */
    private Date date;
}
