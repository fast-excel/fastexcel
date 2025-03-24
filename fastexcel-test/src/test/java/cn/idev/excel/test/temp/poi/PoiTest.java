package cn.idev.excel.test.temp.poi;

import cn.idev.excel.util.FileUtils;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * 测试poi
 *
 * @author Jiaju Zhuang
 **/

public class PoiTest {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PoiTest.class);
    
    @TempDir
    Path tempDir;
    
    @SneakyThrows
    @Test
    public void lastRowNum() throws IOException {
        String sourceFile = "src/test/resources/poi/last_row_number_xssf_date_test.xlsx";
        String file = tempDir.resolve(System.currentTimeMillis() + ".xlsx").toString();
        Files.copy(Paths.get(sourceFile), Paths.get(file));
        try (SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(new XSSFWorkbook(new File(file)));) {
            SXSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            Assertions.assertEquals(-1, xssfSheet.getLastRowNum());
            LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
            xssfSheet.createRow(10);
            SXSSFRow row = xssfSheet.getRow(10);
            SXSSFCell cell1 = row.createCell(0);
            SXSSFCell cell2 = row.createCell(1);
            cell1.setCellValue(new Date());
            cell2.setCellValue(new Date());
            LOGGER.info("dd{}", row.getCell(0).getColumnIndex());
            Date date = row.getCell(1).getDateCellValue();
        }
    }
    
    @Test
    public void lastRowNumXSSF() throws IOException {
        
        String sourceFile = "src/test/resources/poi/last_row_number_xssf_date_test.xlsx";
        String file = tempDir.resolve(System.currentTimeMillis() + ".xlsx").toString();
        Files.copy(Paths.get(sourceFile), Paths.get(file));
        try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
                FileOutputStream fileOutputStream = new FileOutputStream(
                        tempDir.resolve(System.currentTimeMillis() + ".xlsx").toFile());) {
            
            LOGGER.info("一共:{}个sheet", xssfWorkbook.getNumberOfSheets());
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
            XSSFRow row = xssfSheet.getRow(1);
            LOGGER.info("dd{}", row.getCell(0).getRow().getRowNum());
            LOGGER.info("dd{}", xssfSheet.getLastRowNum());
            
            XSSFCellStyle cellStyle = row.getCell(0).getCellStyle();
            LOGGER.info("size1:{}", cellStyle.getFontIndexAsInt());
            
            XSSFCellStyle cellStyle1 = xssfWorkbook.createCellStyle();
            LOGGER.info("size2:{}", cellStyle1.getFontIndexAsInt());
            
            cellStyle1.cloneStyleFrom(cellStyle);
            LOGGER.info("size3:{}", cellStyle1.getFontIndexAsInt());
            
            LOGGER.info("bbb:{}", cellStyle1.getFont().getXSSFColor().getIndex());
            LOGGER.info("bbb:{}", cellStyle1.getFont().getXSSFColor().getIndexed());
            XSSFColor myColor = new XSSFColor(cellStyle1.getFont().getXSSFColor().getRGB(), null);
            LOGGER.info("bbb:{}", cellStyle1.getFont().getXSSFColor().getRGB());
            LOGGER.info("bbb:{}", cellStyle1.getFont().getXSSFColor().getARGBHex());
            
            LOGGER.info("bbb:{}", cellStyle1.getFont().getBold());
            LOGGER.info("bbb:{}", cellStyle1.getFont().getFontName());
            
            XSSFFont xssfFont = xssfWorkbook.createFont();
            
            xssfFont.setColor(myColor);
            
            xssfFont.setFontHeightInPoints((short) 50);
            xssfFont.setBold(Boolean.TRUE);
            cellStyle1.setFont(xssfFont);
            cellStyle1.setFillForegroundColor(IndexedColors.PINK.getIndex());
            
            LOGGER.info("aaa:{}", cellStyle1.getFont().getColor());
            
            row.getCell(1).setCellStyle(cellStyle1);
            row.getCell(1).setCellValue(3334l);
            
            XSSFCellStyle cellStyle2 = xssfWorkbook.createCellStyle();
            cellStyle2.cloneStyleFrom(cellStyle);
            cellStyle2.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            //cellStyle2.setFont(cellStyle1.getFont());
            row.getCell(2).setCellStyle(cellStyle2);
            row.getCell(2).setCellValue(3334l);
            //LOGGER.info("date1:{}",  row.getCell(0).getStringCellValue());
            //LOGGER.info("date2:{}", ((XSSFColor) cellStyle.getFillForegroundColorColor()).getIndex());
            //LOGGER.info("date2:{}", ((XSSFColor) cellStyle.getFillForegroundColorColor()).isRGB());
            //LOGGER.info("date4:{}", ((XSSFColor) cellStyle.getFillForegroundColorColor()).isIndexed());
            //LOGGER.info("date3:{}", cellStyle.getFont().getXSSFColor().getRGB());
            //LOGGER.info("date4:{}", cellStyle.getFont().getCTFont().getColorArray(0).getRgb());
            xssfWorkbook.write(fileOutputStream);
        }
    }
    
    @Test
    public void lastRowNumXSSFv22() throws IOException {
        
        String sourceFile = "src/test/resources/poi/last_row_number_xssf_date_test.xls";
        String file = tempDir.resolve(System.currentTimeMillis() + ".xls").toString();
        Files.copy(Paths.get(sourceFile), Paths.get(file));
        try (HSSFWorkbook xssfWorkbook = new HSSFWorkbook(Files.newInputStream(Paths.get(file.toString())));
                FileOutputStream fileOutputStream = new FileOutputStream(new File(file))) {
            LOGGER.info("一共:{}个sheet", xssfWorkbook.getNumberOfSheets());
            HSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
            HSSFRow row = xssfSheet.getRow(1);
            LOGGER.info("dd{}", row.getCell(0).getRow().getRowNum());
            LOGGER.info("dd{}", xssfSheet.getLastRowNum());
            
            HSSFCellStyle cellStyle = row.getCell(0).getCellStyle();
            LOGGER.info("单元格1的字体:{}", cellStyle.getFontIndexAsInt());
            
            HSSFCellStyle cellStyle1 = xssfWorkbook.createCellStyle();
            LOGGER.info("size2:{}", cellStyle1.getFontIndexAsInt());
            
            cellStyle1.cloneStyleFrom(cellStyle);
            LOGGER.info("单元格2的字体:{}", cellStyle1.getFontIndexAsInt());
            
            LOGGER.info("bbb:{}", cellStyle1.getFont(xssfWorkbook).getColor());
            
            HSSFFont xssfFont = xssfWorkbook.createFont();
            
            xssfFont.setColor(cellStyle1.getFont(xssfWorkbook).getColor());
            xssfFont.setFontHeightInPoints((short) 50);
            xssfFont.setBold(Boolean.TRUE);
            cellStyle1.setFont(xssfFont);
            cellStyle1.setFillForegroundColor(IndexedColors.PINK.getIndex());
            
            LOGGER.info("aaa:{}", cellStyle1.getFont(xssfWorkbook).getColor());
            
            row.getCell(1).setCellStyle(cellStyle1);
            row.getCell(1).setCellValue(3334l);
            
            HSSFCellStyle cellStyle2 = xssfWorkbook.createCellStyle();
            cellStyle2.cloneStyleFrom(cellStyle);
            cellStyle2.setFillForegroundColor(IndexedColors.BLUE.getIndex());
            //cellStyle2.setFont(cellStyle1.getFont());
            row.getCell(2).setCellStyle(cellStyle2);
            row.getCell(2).setCellValue(3334l);
            //LOGGER.info("date1:{}",  row.getCell(0).getStringCellValue());
            //LOGGER.info("date2:{}", ((XSSFColor) cellStyle.getFillForegroundColorColor()).getIndex());
            //LOGGER.info("date2:{}", ((XSSFColor) cellStyle.getFillForegroundColorColor()).isRGB());
            //LOGGER.info("date4:{}", ((XSSFColor) cellStyle.getFillForegroundColorColor()).isIndexed());
            //LOGGER.info("date3:{}", cellStyle.getFont().getXSSFColor().getRGB());
            //LOGGER.info("date4:{}", cellStyle.getFont().getCTFont().getColorArray(0).getRgb());
            xssfWorkbook.write(fileOutputStream);
        }
    }
    
    @Test
    public void lastRowNum233() throws IOException {
        String sourceFile = "src/test/resources/poi/last_row_number_xssf_date_test.xlsx";
        String file = tempDir.resolve(System.currentTimeMillis() + ".xlsx").toString();
        Files.copy(Paths.get(sourceFile), Paths.get(file));
        try (XSSFWorkbook xx = new XSSFWorkbook(file);
                SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(xx);
                FileOutputStream fileout = new FileOutputStream(
                        tempDir.resolve(System.currentTimeMillis() + ".xlsx").toFile());) {
            System.out.println(new File(file).exists());
            Sheet xssfSheet = xssfWorkbook.getXSSFWorkbook().getSheetAt(0);
            Cell cell = xssfSheet.getRow(0).createCell(9);
            cell.setCellValue("testssdf是士大夫否t");
            xssfWorkbook.write(fileout);
        }
    }
    
    @Test
    public void lastRowNum255() throws IOException, InvalidFormatException {
        String sourceFile = "src/test/resources/poi/last_row_number_xssf_date_test.xlsx";
        String file = tempDir.resolve(System.currentTimeMillis() + ".xlsx").toString();
        Files.copy(Paths.get(sourceFile), Paths.get(file));
        try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new File(file));
                SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(xssfWorkbook);
                FileOutputStream fileout = new FileOutputStream(
                        tempDir.resolve(System.currentTimeMillis() + ".xlsx").toFile());) {
            Sheet xssfSheet = xssfWorkbook.getSheetAt(0);
            xssfSheet.shiftRows(1, 4, 10, true, true);
            sxssfWorkbook.write(fileout);
        }
    }
    
    @Test
    public void cp() throws IOException, InvalidFormatException {
        String sourceFile = "src/test/resources/poi/last_row_number_xssf_date_test.xlsx";
        String file = tempDir.resolve(System.currentTimeMillis() + ".xlsx").toString();
        Files.copy(Paths.get(sourceFile), Paths.get(file));
        SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(new XSSFWorkbook(file));
        SXSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
        SXSSFRow row = xssfSheet.getRow(0);
        LOGGER.info("第一行数据:{}", row);
        xssfSheet.createRow(20);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
    }
    
    @Test
    public void lastRowNum233443() throws IOException, InvalidFormatException {
        String sourceFile = "src/test/resources/poi/last_row_number_xssf_date_test.xlsx";
        String file = tempDir.resolve(System.currentTimeMillis() + ".xlsx").toString();
        Files.copy(Paths.get(sourceFile), Paths.get(file));
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new File(file));
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        System.out.println(xssfSheet.getLastRowNum());
        System.out.println(xssfSheet.getRow(0));
        
    }
    
    @Test
    public void lastRowNum2333() throws IOException, InvalidFormatException {
        String sourceFile = "src/test/resources/poi/last_row_number_xssf_date_test.xlsx";
        String file = tempDir.resolve(System.currentTimeMillis() + ".xlsx").toString();
        Files.copy(Paths.get(sourceFile), Paths.get(file));
        try (XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new File(file));
                SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(xssfWorkbook);
                FileOutputStream fileout = new FileOutputStream(
                        tempDir.resolve(System.currentTimeMillis() + ".xlsx").toFile());) {
            Sheet xssfSheet = xssfWorkbook.getSheetAt(0);
            Cell cell = xssfSheet.getRow(0).createCell(9);
            cell.setCellValue("testssdf是士大夫否t");
            sxssfWorkbook.write(fileout);
        }
    }
    
    @Test
    public void testread() throws IOException {
        String sourceFile = "src/test/resources/simple/simple07.xlsx";
        String file = tempDir.resolve(System.currentTimeMillis() + ".xlsx").toString();
        Files.copy(Paths.get(sourceFile), Paths.get(file));
        try (SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(new XSSFWorkbook(file));) {
            Sheet xssfSheet = xssfWorkbook.getXSSFWorkbook().getSheetAt(0);
            //
            // Cell cell = xssfSheet.getRow(0).createCell(9);
        }
        
        String file1 = tempDir.resolve(System.currentTimeMillis() + ".xlsx").toString();
        Files.copy(Paths.get(sourceFile), Paths.get(file1));
        
        try (SXSSFWorkbook xssfWorkbook1 = new SXSSFWorkbook(new XSSFWorkbook(file1));) {
            Sheet xssfSheet1 = xssfWorkbook1.getXSSFWorkbook().getSheetAt(0);
            // Cell cell1 = xssfSheet1.getRow(0).createCell(9);
        }
    }
    
    @Test
    public void testreadRead() throws IOException {
        String sourceFile = "src/test/resources/poi/last_row_number_xssf_date_test.xlsx";
        String file = tempDir.resolve(System.currentTimeMillis() + ".xlsx").toString();
        Files.copy(Paths.get(sourceFile), Paths.get(file));
        FileUtils.readFileToByteArray(new File(file));
    }
    
    @Test
    public void lastRowNum2332222() throws IOException {
        String sourceFile = "src/test/resources/poi/last_row_number_xssf_date_test.xlsx";
        String file = tempDir.resolve(System.currentTimeMillis() + ".xlsx").toString();
        Files.copy(Paths.get(sourceFile), Paths.get(file));
        try (SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(new XSSFWorkbook(file));
                FileOutputStream fileout = new FileOutputStream(
                        tempDir.resolve(System.currentTimeMillis() + ".xlsx").toFile());) {
            Sheet xssfSheet = xssfWorkbook.getXSSFWorkbook().getSheetAt(0);
            Cell cell = xssfSheet.getRow(0).createCell(9);
            cell.setCellValue("testssdf是士大夫否t");
            xssfWorkbook.write(fileout);
        }
    }
    
    @Test
    public void lastRowNum23443() throws IOException {
        String sourceFile = "src/test/resources/poi/last_row_number_xssf_date_test.xlsx";
        String file = tempDir.resolve(System.currentTimeMillis() + ".xlsx").toString();
        Files.copy(Paths.get(sourceFile), Paths.get(file));
        try (SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(new XSSFWorkbook(file));
                FileOutputStream fileout = new FileOutputStream(
                        tempDir.resolve(System.currentTimeMillis() + ".xlsx").toFile());) {
            Sheet xssfSheet = xssfWorkbook.getSheetAt(0);
            xssfWorkbook.write(fileout);
        }
    }
    
    @Test
    public void lastRowNum2() throws IOException {
        String sourceFile = "src/test/resources/poi/last_row_number_xssf_date_test.xlsx";
        String file = tempDir.resolve(System.currentTimeMillis() + ".xlsx").toString();
        Files.copy(Paths.get(sourceFile), Paths.get(file));
        SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(new XSSFWorkbook(file));
        Sheet xssfSheet = xssfWorkbook.getXSSFWorkbook().getSheetAt(0);
        LOGGER.info("一共行数:{}", xssfSheet.getPhysicalNumberOfRows());
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
        LOGGER.info("一共行数:{}", xssfSheet.getFirstRowNum());
        
    }
    
    @Test
    public void lastRowNumXSSF2() throws IOException {
        String sourceFile = "src/test/resources/poi/last_row_number_xssf_date_test.xlsx";
        String file = tempDir.resolve(System.currentTimeMillis() + ".xlsx").toString();
        Files.copy(Paths.get(sourceFile), Paths.get(file));
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        LOGGER.info("一共:{}个sheet", xssfWorkbook.getNumberOfSheets());
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
        XSSFRow row = xssfSheet.getRow(0);
        LOGGER.info("第一行数据:{}", row);
    }
    
}
