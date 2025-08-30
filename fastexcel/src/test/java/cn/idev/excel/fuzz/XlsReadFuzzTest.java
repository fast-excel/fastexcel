package cn.idev.excel.fuzz;

import cn.idev.excel.FastExcelFactory;
import cn.idev.excel.read.builder.ExcelReaderBuilder;
import cn.idev.excel.support.ExcelTypeEnum;
import com.code_intelligence.jazzer.junit.FuzzTest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.zip.ZipException;
import lombok.SneakyThrows;
import org.apache.poi.EmptyFileException;
import org.apache.poi.hssf.record.RecordInputStream.LeftoverDataException;
import org.apache.poi.poifs.filesystem.NotOLE2FileException;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;

/**
 * Fuzzes the XLS (BIFF) parsing path with arbitrary bytes.
 */
public class XlsReadFuzzTest {
    private static final int MAX_SIZE = 1_000_000; // 1MB guard

    @SneakyThrows
    @FuzzTest
    void fuzzXls(byte[] data) {
        if (data == null || data.length == 0 || data.length > MAX_SIZE) {
            return;
        }
        try (InputStream in = new ByteArrayInputStream(data)) {
            ExcelReaderBuilder builder = FastExcelFactory.read(in).excelType(ExcelTypeEnum.XLS);
            builder.sheet().doReadSync();
        } catch (Throwable t) {
            if (isBenignHssfParseException(t)) {
                return; // expected for random inputs
            }
            throw t;
        }
    }

    private static boolean isBenignHssfParseException(Throwable t) {
        for (int i = 0; i < 6 && t != null; i++, t = t.getCause()) {
            if (t instanceof NotOLE2FileException
                    || t instanceof OfficeXmlFileException
                    || t instanceof LeftoverDataException
                    || t instanceof EmptyFileException
                    || t instanceof ZipException) {
                return true;
            }
            String msg = t.getMessage();
            if (msg != null) {
                String m = msg.toLowerCase();
                if (m.contains("not ole2")
                        || m.contains("invalid header signature")
                        || m.contains("corrupt stream")
                        || m.contains("invalid record")
                        || m.contains("buffer underrun")
                        || m.contains("buffer overrun")
                        || m.contains("leftover")
                        || m.contains("zip")) {
                    return true;
                }
            }
        }
        return false;
    }
}
