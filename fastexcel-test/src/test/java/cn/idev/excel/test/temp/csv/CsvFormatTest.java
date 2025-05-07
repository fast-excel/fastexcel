package cn.idev.excel.test.temp.csv;

import cn.idev.excel.ExcelReader;
import cn.idev.excel.FastExcel;
import cn.idev.excel.read.metadata.holder.ReadWorkbookHolder;
import cn.idev.excel.read.metadata.holder.csv.CsvReadWorkbookHolder;
import cn.idev.excel.test.util.TestFileUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.QuoteMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

public class CsvFormatTest {

    private static File csvSimpleFile;
    private static File csvSimpleDelimiterFile;
    private static File csvSimpleQuoteFile;

    @BeforeAll
    public static void init() {
        csvSimpleFile = TestFileUtil.readFile("csv" + File.separator + "simple.csv");
        csvSimpleDelimiterFile = TestFileUtil.readFile("csv" + File.separator + "simple-delimiter.csv");
        csvSimpleQuoteFile = TestFileUtil.readFile("csv" + File.separator + "simple-quote.csv");
    }

    @Test
    public void testReadSimple() {
        List<CsvData> dataList = FastExcel.read(csvSimpleFile, CsvData.class, new CsvDataListener())
            .csvFormat(null)
            .doReadAllSync();
        Assertions.assertEquals(10, dataList.size());
    }

    @Test
    public void testReadDelimiter() {
        char delimiter = '#';
        // setting the CsvFormat of ExcelReader
        try (ExcelReader excelReader = FastExcel.read(csvSimpleDelimiterFile, CsvData.class, new CsvDataListener()).build()) {
            ReadWorkbookHolder readWorkbookHolder = excelReader.analysisContext().readWorkbookHolder();
            if (readWorkbookHolder instanceof CsvReadWorkbookHolder) {
                CsvReadWorkbookHolder csvReadWorkbookHolder = (CsvReadWorkbookHolder) readWorkbookHolder;
                csvReadWorkbookHolder.setCsvFormat(CSVFormat.DEFAULT.builder().setDelimiter(delimiter).build());
            }
            excelReader.readAll();
        }

        // use parameter
        List<CsvData> dataList = FastExcel.read(csvSimpleDelimiterFile, CsvData.class, new CsvDataListener())
            .csvFormat(CSVFormat.DEFAULT.builder().setDelimiter(delimiter).build())
            .doReadAllSync();
        Assertions.assertEquals(10, dataList.size());
    }

    @Test
    public void testReadQuote() {
        List<CsvData> dataList = FastExcel.read(csvSimpleQuoteFile, CsvData.class, new CsvDataListener())
            .csvFormat(CSVFormat.DEFAULT.builder().setQuoteMode(QuoteMode.MINIMAL).build())
            .doReadAllSync();
        Assertions.assertEquals(10, dataList.size());
    }
}
