package cn.idev.excel.demo.read;

import cn.idev.excel.metadata.data.CellData;
import java.util.Date;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Basic data class. The order here is consistent with the order in Excel
 *
 *
 **/
@Getter
@Setter
@EqualsAndHashCode
public class CellDataReadDemoData {
    private CellData<String> string;
    // Note that although this is a date, the type stored is number because excel stores it as number
    private CellData<Date> date;
    private CellData<Double> doubleData;
    // This may not be perfectly retrieved. Some formulas are dependent and may not be read. This issue will be fixed
    // later
    private CellData<String> formulaValue;
}
