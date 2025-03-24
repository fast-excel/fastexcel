package cn.idev.excel.test.temp.poi;

import cn.idev.excel.metadata.data.CellData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * TODO
 *
 * @author 罗成
 **/
@Getter
@Setter
@EqualsAndHashCode
public class TestCell {
    
    private CellData<?> c1;
    
    private CellData<List<String>> c2;
}
