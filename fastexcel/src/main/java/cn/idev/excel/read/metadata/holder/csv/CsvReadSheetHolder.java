package cn.idev.excel.read.metadata.holder.csv;

import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.read.metadata.holder.ReadSheetHolder;
import cn.idev.excel.read.metadata.holder.ReadWorkbookHolder;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * sheet holder
 *
 * @author Jiaju Zhuang
 */
@Getter
@Setter
@EqualsAndHashCode
public class CsvReadSheetHolder extends ReadSheetHolder {

    public CsvReadSheetHolder(ReadSheet readSheet, ReadWorkbookHolder readWorkbookHolder) {
        super(readSheet, readWorkbookHolder);
    }
}
