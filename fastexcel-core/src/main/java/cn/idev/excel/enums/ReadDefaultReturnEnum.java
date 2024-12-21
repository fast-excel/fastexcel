package cn.idev.excel.enums;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import cn.idev.excel.metadata.data.ReadCellData;

/**
 * Read not to {@code cn.idev.excel.metadata.BasicParameter#clazz} value, the default will return type.
 *
 * @author Jiaju Zhuang
 */
public enum ReadDefaultReturnEnum {
    /**
     * default.The content of cells into string, is the same as you see in the excel.
     */
    STRING,

    /**
     * Returns the actual type.
     * Will be automatically selected according to the cell contents what return type, will return the following class:
     * <ol>
     *     <li>{@link BigDecimal}</li>
     *     <li>{@link Boolean}</li>
     *     <li>{@link String}</li>
     *     <li>{@link LocalDateTime}</li>
     * </ol>
     */
    ACTUAL_DATA,

    /**
     * Return to {@link ReadCellData}, can decide which field you need.
     */
    READ_CELL_DATA

}
