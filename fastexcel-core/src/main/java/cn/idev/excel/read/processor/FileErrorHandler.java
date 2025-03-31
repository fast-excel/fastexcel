package cn.idev.excel.read.processor;

import cn.idev.excel.read.metadata.ValidateError;
import cn.idev.excel.read.metadata.holder.ValidateErrorHolder;
import cn.idev.excel.util.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * handle the error message as file
 *
 * @author wangmeng
 */
public class FileErrorHandler implements ValidateErrorHandler<File> {

    private final static Logger LOGGER = LoggerFactory.getLogger(FileErrorHandler.class);

    private final static String COLUMN_NAME = "Error message";

    public final static FileErrorHandler INSTANCE = new FileErrorHandler();

    @Override
    public File handleError(ValidateErrorHolder errorHolder) {
        File sourceFile = errorHolder.getSourceFile();
        try {
            File tempFile = FileUtils.createErrorTempleFile(sourceFile);
            fillInErrorData(tempFile, errorHolder);
            return tempFile;
        } catch (IOException e) {
            LOGGER.warn("create error temp file fail", e);
        }
        return null;
    }


    /**
     * fill error data into the Excel file
     *
     * @param tempFile
     * @param errorHolder
     * @throws IOException
     */
    public void fillInErrorData(File tempFile, ValidateErrorHolder errorHolder) throws IOException {
        Map<Integer, List<ValidateError>> errorMap = errorHolder.getError();

        Workbook workbook = null;
        // open the original Excel file and add error messages
        try (FileInputStream inputStream = new FileInputStream(tempFile)) {
            workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(errorHolder.getSheetNo());

            // add error column headers
            Row headerRow = sheet.getRow(0);
            short lastCellNum = headerRow.getLastCellNum();
            // check if the error column already exists
            Cell lastValidCell = headerRow.getCell(lastCellNum - 1);
            if (lastValidCell != null) {
                if (!COLUMN_NAME.equals(lastValidCell.getStringCellValue())) {
                    Cell errorHeaderCell = headerRow.createCell(lastCellNum);
                    errorHeaderCell.setCellValue(COLUMN_NAME);
                    errorMap.forEach((rowNum, list) -> {
                        Row row = sheet.getRow(rowNum);
                        if (row != null) {
                            Cell errorCell = row.createCell(lastCellNum);
                            errorCell.setCellValue(mergeText(list));
                        }
                    });
                } else {
                    int lastRowNum = sheet.getLastRowNum();
                    for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {
                        Row row = sheet.getRow(rowNum);
                        String setErrorMsg = mergeText(errorMap.get(rowNum));
                        // if there is no error information to set, the old error information should be cleared
                        Cell errorCell = row.getCell(lastCellNum - 1);
                        if (setErrorMsg == null) {
                            if (errorCell != null) {
                                errorCell.setCellValue((String) null);
                            }
                        } else {
                            if (errorCell == null) {
                                errorCell = row.createCell(lastCellNum - 1);
                            }
                            errorCell.setCellValue(setErrorMsg);
                        }
                    }
                }
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
            // write it back
            workbook.write(outputStream);
            workbook.close();
        }
    }

}
