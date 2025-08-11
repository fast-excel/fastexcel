package cn.idev.excel.test.temp.poi;

import cn.idev.excel.metadata.data.CellData;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
@EqualsAndHashCode
public class TestCell {
    private CellData<?> c1;
    private CellData<List<String>> c2;
}
