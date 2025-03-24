package cn.idev.excel.metadata;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Field;

/**
 * filed wrapper
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class FieldWrapper {
    
    /**
     * field
     */
    private Field field;
    
    /**
     * The field name matching cglib
     */
    private String fieldName;
    
    /**
     * The name of the sheet header.
     *
     * @see ExcelProperty
     */
    private String[] heads;
}
