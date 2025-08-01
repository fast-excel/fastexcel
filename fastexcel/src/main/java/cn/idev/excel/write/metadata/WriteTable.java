package cn.idev.excel.write.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * table
 *
 */
@Getter
@Setter
@EqualsAndHashCode
public class WriteTable extends WriteBasicParameter {
    /**
     * Starting from 0
     */
    private Integer tableNo;
}
