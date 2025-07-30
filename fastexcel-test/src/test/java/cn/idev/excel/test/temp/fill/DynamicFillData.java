package cn.idev.excel.test.temp.fill;

import cn.idev.excel.annotation.fill.DynamicColumn;
import lombok.Data;

import java.util.Map;

@Data
public class DynamicFillData {
    private String name;
    private double number;
    @DynamicColumn()
    private Map<String,String> qtyMap;

    @DynamicColumn()
    private Map<String,DynamicFillDataObj> priceMap;
}
