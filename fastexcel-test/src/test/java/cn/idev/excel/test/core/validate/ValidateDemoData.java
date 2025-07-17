package cn.idev.excel.test.core.validate;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author wangmeng
 * @since 2025/3/22
 */
@Data
public class ValidateDemoData {

    @ExcelProperty(value = "订单号", notNull = true)
    private String orderNo;
    @ExcelProperty(value = "用户名", notNull = true)
    private String username;
    @ExcelProperty(value = "金额")
    private BigDecimal amount;
}
