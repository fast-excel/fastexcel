package cn.idev.excel.metadata;

import cn.idev.excel.enums.HolderEnum;

/**
 *
 * Get the corresponding holder
 *
 * @author Jiaju Zhuang
 **/
public interface Holder {

    /**
     * What holder is the return
     *
     * @return Holder enum.
     */
    HolderEnum holderType();

}
