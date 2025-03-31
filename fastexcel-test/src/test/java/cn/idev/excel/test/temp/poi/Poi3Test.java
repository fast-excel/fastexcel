package cn.idev.excel.test.temp.poi;

import cn.idev.excel.test.util.TestFileUtil;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * 测试poi
 *
 * @author Jiaju Zhuang
 **/

public class Poi3Test {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Poi3Test.class);
    
    @Test
    public void Encryption(@TempDir Path tempDir) throws Exception {
        // Write out the encrypted version
        try (POIFSFileSystem fs = new POIFSFileSystem();
                FileOutputStream fos = new FileOutputStream(
                        tempDir.resolve(System.currentTimeMillis() + ".xlsx").toFile());) {
            String file = TestFileUtil.getPath() + "large" + File.separator + "large07.xlsx";
            EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
            Encryptor enc = info.getEncryptor();
            enc.confirmPassword("foobaa");
            OPCPackage opc = OPCPackage.open(new File(file), PackageAccess.READ_WRITE);
            OutputStream os = enc.getDataStream(fs);
            opc.save(os);
            opc.close();
            fs.writeFilesystem(fos);
        }
    }
    
    @Test
    public void Encryption2() throws Exception {
        Biff8EncryptionKey.setCurrentUserPassword("incorrect pwd");
        POIFSFileSystem fs = new POIFSFileSystem(new File("src/test/resources/demo/pwd_123.xls"), true);
        Assertions.assertThrows(EncryptedDocumentException.class, () -> new HSSFWorkbook(fs.getRoot(), true));
        Biff8EncryptionKey.setCurrentUserPassword("123");
        HSSFWorkbook hwb = new HSSFWorkbook(
                new POIFSFileSystem(new File("src/test/resources/demo/pwd_123.xls"), true).getRoot(), true);
        Assertions.assertEquals("Sheet1", hwb.getSheetAt(0).getSheetName());
        Biff8EncryptionKey.setCurrentUserPassword(null);
    }
}
