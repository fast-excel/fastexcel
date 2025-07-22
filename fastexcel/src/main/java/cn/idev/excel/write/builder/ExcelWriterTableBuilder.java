package cn.idev.excel.write.builder;

import java.util.Collection;
import java.util.function.Supplier;

import cn.idev.excel.ExcelWriter;
import cn.idev.excel.exception.ExcelGenerateException;
import cn.idev.excel.write.metadata.WriteSheet;
import cn.idev.excel.write.metadata.WriteTable;

/**
 * Build sheet
 *
 * @author Jiaju Zhuang
 */
public class ExcelWriterTableBuilder extends AbstractExcelWriterParameterBuilder<ExcelWriterTableBuilder, WriteTable> {

    private ExcelWriter excelWriter;

    private WriteSheet writeSheet;
    /**
     * table
     */
    private final WriteTable writeTable;

    public ExcelWriterTableBuilder() {
        this.writeTable = new WriteTable();
    }

    public ExcelWriterTableBuilder(ExcelWriter excelWriter, WriteSheet writeSheet) {
        this.excelWriter = excelWriter;
        this.writeSheet = writeSheet;
        this.writeTable = new WriteTable();
    }

    /**
     * Starting from 0
     *
     * @param tableNo
     * @return
     */
    public ExcelWriterTableBuilder tableNo(Integer tableNo) {
        writeTable.setTableNo(tableNo);
        return this;
    }

    public WriteTable build() {
        return writeTable;
    }

    public void doWrite(Collection<?> data) {
        if (excelWriter == null) {
            throw new ExcelGenerateException("Must use 'FastExcelFactory.write().sheet().table()' to call this method");
        }
        excelWriter.write(data, writeSheet, build());
        excelWriter.finish();
    }

    public void doWrite(Supplier<Collection<?>> supplier) {
        doWrite(supplier.get());
    }

    @Override
    protected WriteTable parameter() {
        return writeTable;
    }
}
