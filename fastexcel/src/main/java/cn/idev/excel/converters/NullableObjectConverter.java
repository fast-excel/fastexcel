package cn.idev.excel.converters;

/**
 * When implementing <code>convertToExcelData</code> method, pay attention to the reference <code>value</code> may be
 * null
 *
 *
 **/
public interface NullableObjectConverter<T> extends Converter<T> {}
