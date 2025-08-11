package cn.idev.excel.read.builder;

import cn.idev.excel.ExcelReader;
import cn.idev.excel.event.SyncReadListener;
import cn.idev.excel.exception.ExcelGenerateException;
import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.read.metadata.ReadWorkbook;
import cn.idev.excel.support.ExcelTypeEnum;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.QuoteMode;

/**
 * Builder for CSV file reading
 */
public class CsvReaderBuilder extends AbstractExcelReaderParameterBuilder<CsvReaderBuilder, ReadSheet> {
    private ReadWorkbook readWorkbook;
    private ReadSheet readSheet;
    private CSVFormat.Builder csvFormatBuilder;

    private CsvReaderBuilder() {}

    public CsvReaderBuilder(ReadWorkbook readWorkbook) {
        readWorkbook.setExcelType(ExcelTypeEnum.CSV);
        this.readWorkbook = readWorkbook;
        this.readSheet = new ReadSheet();
        this.csvFormatBuilder = CSVFormat.DEFAULT.builder();
    }

    /**
     * Sets the delimiter character
     *
     * @param delimiter the delimiter character
     * @return Returns a CsvReaderBuilder object, enabling method chaining
     */
    public CsvReaderBuilder delimiter(String delimiter) {
        if (delimiter != null) {
            this.csvFormatBuilder.setDelimiter(delimiter);
        }
        return this;
    }

    /**
     * Sets the quote character
     *
     * @param quote the quote character
     * @return Returns a CsvReaderBuilder object, enabling method chaining
     */
    public CsvReaderBuilder quote(Character quote) {
        return quote(quote, QuoteMode.MINIMAL);
    }

    /**
     * Sets the quote character and the quoting behavior
     *
     * @param quote     the quote character
     * @param quoteMode defines the quoting behavior
     * @return Returns a CsvReaderBuilder object, enabling method chaining
     */
    public CsvReaderBuilder quote(Character quote, QuoteMode quoteMode) {
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
     * @return Returns a CsvReaderBuilder object, enabling method chaining
     */
    public CsvReaderBuilder recordSeparator(String recordSeparator) {
        if (recordSeparator != null) {
            this.csvFormatBuilder.setRecordSeparator(recordSeparator);
        }
        return this;
    }

    /**
     * Sets the null string
     *
     * @param nullString the String to convert to and from {@code null}
     * @return Returns a CsvReaderBuilder object, enabling method chaining
     */
    public CsvReaderBuilder nullString(String nullString) {
        if (nullString != null) {
            this.csvFormatBuilder.setNullString(nullString);
        }
        return this;
    }

    /**
     * Sets the escape character.
     *
     * @param escape the Character used to escape special characters in values
     * @return Returns a CsvReaderBuilder object, enabling method chaining
     */
    public CsvReaderBuilder escape(Character escape) {
        if (escape != null) {
            this.csvFormatBuilder.setEscape(escape);
        }
        return this;
    }

    private ExcelReader buildExcelReader() {
        if (this.readWorkbook.getAutoTrim() != null) {
            this.csvFormatBuilder.setTrim(this.readWorkbook.getAutoTrim());
        }
        if (this.readWorkbook.getIgnoreEmptyRow() != null) {
            this.csvFormatBuilder.setIgnoreEmptyLines(this.readWorkbook.getIgnoreEmptyRow());
        }
        this.readWorkbook.setCsvFormat(this.csvFormatBuilder.build());
        return new ExcelReader(this.readWorkbook);
    }

    public void doRead() {
        if (this.readWorkbook == null) {
            throw new ExcelGenerateException("Must use 'FastExcelFactory.read().csv()' to call this method");
        }
        ExcelReader excelReader = buildExcelReader();
        excelReader.read(this.readSheet);
        excelReader.finish();
    }

    /**
     * synchronous read and returns the results
     *
     * @return Returns a list containing the read data
     */
    public <T> List<T> doReadSync() {
        if (this.readWorkbook == null) {
            throw new ExcelGenerateException("Must use 'FastExcelFactory.read().csv()' to call this method");
        }
        ExcelReader excelReader = buildExcelReader();
        // Register a synchronous read listener
        SyncReadListener syncReadListener = new SyncReadListener();
        registerReadListener(syncReadListener);
        excelReader.read(this.readSheet);
        excelReader.finish();
        return (List<T>) syncReadListener.getList();
    }

    @Override
    protected ReadSheet parameter() {
        return this.readSheet;
    }
}
