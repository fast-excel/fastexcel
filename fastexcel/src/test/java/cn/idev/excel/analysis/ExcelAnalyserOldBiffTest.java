package cn.idev.excel.analysis;

import cn.idev.excel.read.metadata.ReadWorkbook;
import cn.idev.excel.support.ExcelTypeEnum;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Unit tests for handling very old XLS (e.g., BIFF2) gracefully.
 */
class ExcelAnalyserOldBiffTest {

    /**
     * Given a BIFF2-like minimal input (from fuzz crash seed Base64: CQAE),
     * ExcelAnalyserImpl should not throw; it should fall back to a no-op executor.
     */
    @Test
    void chooseExecutor_shouldNoop_onOldBiffBytes_stream() {
        byte[] seed = Base64.getDecoder().decode("CQAE");
        InputStream in = new ByteArrayInputStream(seed);

        ReadWorkbook rw = new ReadWorkbook();
        rw.setInputStream(in);
        // Force XLS branch so chooseExcelExecutor will attempt POIFS construction
        rw.setExcelType(ExcelTypeEnum.XLS);

        ExcelAnalyserImpl analyser = new ExcelAnalyserImpl(rw);
        // analysis should not throw even if sheets list is empty when readAll=true
        Assertions.assertDoesNotThrow(() -> analyser.analysis(Collections.emptyList(), true));
        // Noop executor should present empty sheet list
        Assertions.assertTrue(analyser.excelExecutor().sheetList().isEmpty());
        // Analysis context should be XLS (fallback context)
        Assertions.assertEquals(
                ExcelTypeEnum.XLS,
                analyser.analysisContext().readWorkbookHolder().getExcelType());
        Assertions.assertTrue(
                analyser.excelExecutor() instanceof ExcelAnalyserImpl.NoopExcelReadExecutor,
                "Executor should be NoopExcelReadExecutor for old BIFF");
    }

    /**
     * Same as above but via File path to cover the other constructor branch.
     */
    @Test
    void chooseExecutor_shouldNoop_onOldBiffBytes_file(@TempDir Path tmp) throws Exception {
        byte[] seed = Base64.getDecoder().decode("CQAE");
        Path f = tmp.resolve("old_biff_seed.xls");
        Files.write(f, seed);

        ReadWorkbook rw = new ReadWorkbook();
        rw.setFile(f.toFile());
        // Force XLS branch
        rw.setExcelType(ExcelTypeEnum.XLS);

        ExcelAnalyserImpl analyser = new ExcelAnalyserImpl(rw);
        Assertions.assertDoesNotThrow(() -> analyser.analysis(Collections.emptyList(), true));
        Assertions.assertTrue(analyser.excelExecutor().sheetList().isEmpty());
        Assertions.assertEquals(
                ExcelTypeEnum.XLS,
                analyser.analysisContext().readWorkbookHolder().getExcelType());
        Assertions.assertTrue(
                analyser.excelExecutor() instanceof ExcelAnalyserImpl.NoopExcelReadExecutor,
                "Executor should be NoopExcelReadExecutor for old BIFF");
    }
}
