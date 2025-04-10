package cn.idev.excel.test.temp.poi;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.test.core.encrypt.EncryptData;
import cn.idev.excel.test.core.simple.SimpleData;
import cn.idev.excel.test.util.TestFileUtil;

import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;

/**
 * TODO
 *
 * @author Jiaju Zhuang
 */

public class PoiEncryptTest {
    @Test
    public void encrypt() throws Exception {

        XSSFWorkbook workbook = new XSSFWorkbook();
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook);

        Sheet sheet = sxssfWorkbook.createSheet("sheet1");
        sheet.createRow(0).createCell(0).setCellValue("T2");

        POIFSFileSystem fs = new POIFSFileSystem();
        EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);

        Encryptor enc = info.getEncryptor();
        enc.confirmPassword("123456");

        // write the workbook into the encrypted OutputStream
        OutputStream encos = enc.getDataStream(fs);
        sxssfWorkbook.write(encos);
        sxssfWorkbook.dispose();
        sxssfWorkbook.close();
        encos.close(); // this is necessary before writing out the FileSystem

        OutputStream os = new FileOutputStream(
            TestFileUtil.createNewFile("encrypt" + System.currentTimeMillis() + ".xlsx"));
        fs.writeFilesystem(os);
        os.close();
        fs.close();
    }

    @Test
    public void encryptExcel() throws Exception {
        EasyExcel.write(TestFileUtil.createNewFile("encryptv2" + System.currentTimeMillis() + ".xlsx"),
                EncryptData.class).password("123456")
            .sheet().doWrite(data());
    }

    private List<SimpleData> data() {
        List<SimpleData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SimpleData simpleData = new SimpleData();
            simpleData.setName("姓名" + i);
            list.add(simpleData);
        }
        return list;
    }

}
