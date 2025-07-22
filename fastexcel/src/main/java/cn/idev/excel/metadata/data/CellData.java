package cn.idev.excel.metadata.data;

import java.math.BigDecimal;

import cn.idev.excel.enums.CellDataTypeEnum;
import cn.idev.excel.metadata.AbstractCell;
import cn.idev.excel.util.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Excel internal cell data.
 *
 * <p>
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class CellData<T> extends AbstractCell {
    /**
     * cell type
     */
    private CellDataTypeEnum type;
    /**
     * {@link CellDataTypeEnum#NUMBER}
     */
    private BigDecimal numberValue;
    /**
     * {@link CellDataTypeEnum#STRING} and{@link CellDataTypeEnum#ERROR}
     */
    private String stringValue;
    /**
     * {@link CellDataTypeEnum#BOOLEAN}
     */
    private Boolean booleanValue;

    /**
     * The resulting converted data.
     */
    private T data;

    /**
     * formula
     */
    private FormulaData formulaData;

    /**
     * Ensure that the object does not appear null
     */
    public void checkEmpty() {
        if (type == null) {
            type = CellDataTypeEnum.EMPTY;
        }
        switch (type) {
            case STRING:
            case DIRECT_STRING:
            case ERROR:
                if (StringUtils.isEmpty(stringValue)) {
                    type = CellDataTypeEnum.EMPTY;
                }
                return;
            case NUMBER:
                if (numberValue == null) {
                    type = CellDataTypeEnum.EMPTY;
                }
                return;
            case BOOLEAN:
                if (booleanValue == null) {
                    type = CellDataTypeEnum.EMPTY;
                }
                return;
            default:
        }
    }

}
