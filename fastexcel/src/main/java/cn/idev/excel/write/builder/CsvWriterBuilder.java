package cn.idev.excel.write.builder;

import cn.idev.excel.ExcelWriter;
import cn.idev.excel.exception.ExcelGenerateException;
import cn.idev.excel.support.ExcelTypeEnum;
import cn.idev.excel.write.metadata.WriteSheet;
import cn.idev.excel.write.metadata.WriteWorkbook;
import java.util.Collection;
import java.util.function.Supplier;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.QuoteMode;

/**
 * Builder for CSV file writing
 */
public class CsvWriterBuilder extends AbstractExcelWriterParameterBuilder<CsvWriterBuilder, WriteSheet> {
    private WriteWorkbook writeWorkbook;
    private CSVFormat.Builder csvFormatBuilder;
    private WriteSheet writeSheet;

    private CsvWriterBuilder() {}

    public CsvWriterBuilder(WriteWorkbook writeWorkbook) {
        writeWorkbook.setExcelType(ExcelTypeEnum.CSV);
        this.writeWorkbook = writeWorkbook;
        this.writeSheet = new WriteSheet();
        this.csvFormatBuilder = CSVFormat.DEFAULT.builder();
    }

    /**
     * Sets the delimiter character
     *
     * @param delimiter the delimiter character
     * @return Returns a CsvWriterBuilder object, enabling method chaining
     */
    public CsvWriterBuilder delimiter(String delimiter) {
        if (delimiter != null) {
            this.csvFormatBuilder.setDelimiter(delimiter);
        }
        return this;
    }

    /**
     * Sets the quote character
     *
     * @param quote the quote character
     * @return Returns a CsvWriterBuilder object, enabling method chaining
     */
    public CsvWriterBuilder quote(Character quote) {
        return quote(quote, QuoteMode.MINIMAL);
    }

    /**
     * Sets the quote character and the quoting behavior
     *
     * @param quote     the quote character
     * @param quoteMode defines the quoting behavior
     * @return Returns a CsvWriterBuilder object, enabling method chaining
     */
    public CsvWriterBuilder quote(Character quote, QuoteMode quoteMode) {
        if (quote != null) {
            this.csvFormatBuilder.setQuote(quote);
        }
        if (quoteMode != null) {
            this.csvFormatBuilder.setQuoteMode(quoteMode);
        }
        return this;
    }

    /**
     * Sets the line separator
     *
     * @param recordSeparator the line separator
     * @return Returns a CsvWriterBuilder object, enabling method chaining
     */
    public CsvWriterBuilder recordSeparator(String recordSeparator) {
        if (recordSeparator != null) {
            this.csvFormatBuilder.setRecordSeparator(recordSeparator);
        }
        return this;
    }

    /**
     * Sets the null string
     *
     * @param nullString the String to convert to and from {@code null}
     * @return Returns a CsvWriterBuilder object, enabling method chaining
     */
    public CsvWriterBuilder nullString(String nullString) {
        if (nullString != null) {
            this.csvFormatBuilder.setNullString(nullString);
        }
        return this;
    }

    /**
     * Sets the escape character.
     *
     * @param escape the Character used to escape special characters in values
     * @return Returns a CsvWriterBuilder object, enabling method chaining
     */
    public CsvWriterBuilder escape(Character escape) {
        if (escape != null) {
            this.csvFormatBuilder.setEscape(escape);
        }
        return this;
    }

    private ExcelWriter buildExcelWriter() {
        if (this.writeWorkbook.getAutoTrim() != null) {
            this.csvFormatBuilder.setTrim(this.writeWorkbook.getAutoTrim());
        }
        if (this.writeWorkbook.getNeedHead() != null) {
            this.csvFormatBuilder.setSkipHeaderRecord(!this.writeWorkbook.getNeedHead());
        }
        this.writeWorkbook.setCsvFormat(this.csvFormatBuilder.build());
        return new ExcelWriter(this.writeWorkbook);
    }

    public void doWrite(Collection<?> data) {
        if (writeWorkbook == null) {
            throw new ExcelGenerateException("Must use 'FastExcelFactory.write().csv()' to call this method");
        }
        ExcelWriter excelWriter = buildExcelWriter();
        excelWriter.write(data, this.writeSheet);
        excelWriter.finish();
    }

    public void doWrite(Supplier<Collection<?>> supplier) {
        doWrite(supplier.get());
    }

    @Override
    protected WriteSheet parameter() {
        return writeSheet;
    }
}
