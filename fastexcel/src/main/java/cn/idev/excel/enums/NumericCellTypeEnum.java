package cn.idev.excel.enums;

import org.apache.poi.ss.usermodel.CellType;

/**
 * Used to supplement {@link CellType}.
 *
 * Cannot distinguish between date and number in write case.
 *
 *
 */
public enum NumericCellTypeEnum {
    /**
     * number
     */
    NUMBER,
    /**
     * date. Support only when writing.
     */
    DATE,
    ;
}
