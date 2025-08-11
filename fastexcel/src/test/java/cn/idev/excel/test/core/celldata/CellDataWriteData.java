package cn.idev.excel.test.core.celldata;

import cn.idev.excel.annotation.format.DateTimeFormat;
import cn.idev.excel.metadata.data.WriteCellData;
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
public class CellDataWriteData {
    @DateTimeFormat("yyyy年MM月dd日")
    private WriteCellData<Date> date;

    private WriteCellData<Integer> integer1;
    private Integer integer2;
    private WriteCellData<?> formulaValue;
}
