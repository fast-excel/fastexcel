package cn.idev.excel.metadata;

import java.util.Map;

import cn.idev.excel.converters.Converter;
import cn.idev.excel.converters.ConverterKeyBuild;

/**
 * Get the corresponding holder
 *
 * @author Jiaju Zhuang
 **/
public interface ConfigurationHolder extends Holder {

    /**
     * Record whether it's new or from cache
     *
     * @return Record whether it's new or from cache
     */
    boolean isNew();

    /**
     * Some global variables
     *
     * @return Global configuration
     */
    GlobalConfiguration globalConfiguration();

    /**
     * What converter does the currently operated cell need to execute
     *
     * @return Converter
     */
    Map<ConverterKeyBuild.ConverterKey, Converter<?>> converterMap();
}
