package cn.idev.excel.test.demo.read;

import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 基础数据类.这里的排序和excel里面的排序一致
 *
 *
 **/
@Data
@Accessors(chain = true)
public class DemoChainAccessorsData {
    private String string;
    private Date date;
    private Double doubleData;
}
