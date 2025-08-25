package cn.idev.excel.test.core.exception;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import cn.idev.excel.analysis.v07.XlsxSaxAnalyser;
import cn.idev.excel.context.xlsx.DefaultXlsxReadContext;
import cn.idev.excel.context.xlsx.XlsxReadContext;
import cn.idev.excel.exception.ExcelCommonException;
import cn.idev.excel.read.metadata.ReadWorkbook;
import cn.idev.excel.support.ExcelTypeEnum;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;

/**
 * Tests for XlsxSaxAnalyser.readOpcPackage error handling: it should wrap
 * POI's NotOfficeXmlFileException/InvalidFormatException into ExcelCommonException
 * with a message containing "Invalid OOXML/zip format".
 */
public class XlsxSaxAnalyserReadOpcPackageTest {

    @Test
    void invalidInputStream_mandatoryUseInputStream_throwsExcelCommonException() {
        ReadWorkbook rw = new ReadWorkbook();
        rw.setInputStream(new ByteArrayInputStream("not-xlsx".getBytes(StandardCharsets.UTF_8)));
        rw.setMandatoryUseInputStream(true);
        XlsxReadContext ctx = new DefaultXlsxReadContext(rw, ExcelTypeEnum.XLSX);

        ExcelCommonException ex = assertThrows(ExcelCommonException.class, () -> new XlsxSaxAnalyser(ctx, null));
        assertTrue(ex.getMessage() != null && ex.getMessage().toLowerCase().contains("invalid ooxml/zip format"));
    }

    @Test
    void invalidFile_throwsExcelCommonException() throws Exception {
        File tmp = File.createTempFile("invalid-ooxml", ".xlsx");
        try {
            Files.write(tmp.toPath(), new byte[] {1, 2, 3, 4});
            ReadWorkbook rw = new ReadWorkbook();
            rw.setFile(tmp);
            XlsxReadContext ctx = new DefaultXlsxReadContext(rw, ExcelTypeEnum.XLSX);

            ExcelCommonException ex = assertThrows(ExcelCommonException.class, () -> new XlsxSaxAnalyser(ctx, null));
            assertTrue(ex.getMessage() != null && ex.getMessage().toLowerCase().contains("invalid ooxml/zip format"));
        } finally {
            try {
                Files.deleteIfExists(tmp.toPath());
            } catch (Exception ignore) {
            }
        }
    }

    @Test
    void decryptedStreamProvided_throwsExcelCommonException() {
        ReadWorkbook rw = new ReadWorkbook();
        // do not set file/inputStream; pass decryptedStream directly
        XlsxReadContext ctx = new DefaultXlsxReadContext(rw, ExcelTypeEnum.XLSX);
        ByteArrayInputStream decrypted = new ByteArrayInputStream("still-not-xlsx".getBytes(StandardCharsets.UTF_8));

        ExcelCommonException ex = assertThrows(ExcelCommonException.class, () -> new XlsxSaxAnalyser(ctx, decrypted));
        assertTrue(ex.getMessage() != null && ex.getMessage().toLowerCase().contains("invalid ooxml/zip format"));
    }
}
