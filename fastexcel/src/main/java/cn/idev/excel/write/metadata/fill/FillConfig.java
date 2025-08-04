package cn.idev.excel.write.metadata.fill;

import cn.idev.excel.annotation.fill.DynamicColumn;
import cn.idev.excel.enums.WriteDirectionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fill config
 *
 *
 **/
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FillConfig {
    public static final String DEFAULT_DYNAMIC_INFO_KEY = "default";
    private WriteDirectionEnum direction;
    /**
     * Create a new row each time you use the list parameter.The default create if necessary.
     * <p>
     * Warnning:If you use <code>forceNewRow</code> set true, will not be able to use asynchronous write file, simply
     * say the whole file will be stored in memory.
     */
    private Boolean forceNewRow;

    /**
     * Automatically inherit style
     *
     * default true.
     */
    private Boolean autoStyle;

    private boolean hasInit;

    /**
     * dynamic column info
     * */
    private Map<String,DynamicColumnInfo> dynamicColumnInfoMap;

    /**
     * get dynamic column info
     *
     * if field name is null or not exist, return default dynamic column info
     * else return dynamic column info by field name
     *
     * @param fieldName field name nullable
     * @return dynamic column info
     * */
    public DynamicColumnInfo getDynamicColumnInfo(String fieldName) {
        if (null == fieldName || !dynamicColumnInfoMap.containsKey(fieldName)) {
            return dynamicColumnInfoMap.get(DEFAULT_DYNAMIC_INFO_KEY);
        }else{
            return dynamicColumnInfoMap.get(fieldName);
        }
    }

    public void init() {
        if (hasInit) {
            return;
        }
        if (direction == null) {
            direction = WriteDirectionEnum.VERTICAL;
        }
        if (forceNewRow == null) {
            forceNewRow = Boolean.FALSE;
        }
        if (autoStyle == null) {
            autoStyle = Boolean.TRUE;
        }
        hasInit = true;
    }

    public static class FillConfigBuilder {
        public FillConfigBuilder addDynamicInfo(List<String> keys, Integer groupSize, String fieldName) {
            if (null == dynamicColumnInfoMap) {
                dynamicColumnInfoMap = new HashMap<>();
            }
            dynamicColumnInfoMap.put(fieldName, new DynamicColumnInfo(keys, groupSize));
            return this;
        }

        public FillConfigBuilder addDefaultDynamicInfo(List<String> keys) {
            return addDynamicInfo(keys, 1, DEFAULT_DYNAMIC_INFO_KEY);
        }

        public FillConfigBuilder addDefaultDynamicInfo(List<String> keys, Integer groupSize) {
            return addDynamicInfo(keys, groupSize, DEFAULT_DYNAMIC_INFO_KEY);
        }

    }
}
