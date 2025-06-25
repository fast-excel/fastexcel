package cn.idev.excel.test.demo.read;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Basic data class. The order here is consistent with the order in the Excel file.
 *
 * @author Jiaju Zhuang
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
