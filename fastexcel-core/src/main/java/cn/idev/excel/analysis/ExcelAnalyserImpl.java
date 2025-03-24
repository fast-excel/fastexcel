package cn.idev.excel.analysis;

import cn.idev.excel.analysis.csv.CsvExcelReadExecutor;
import cn.idev.excel.analysis.v03.XlsSaxAnalyser;
import cn.idev.excel.analysis.v07.XlsxSaxAnalyser;
import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.context.csv.CsvReadContext;
import cn.idev.excel.context.csv.DefaultCsvReadContext;
import cn.idev.excel.context.xls.DefaultXlsReadContext;
import cn.idev.excel.context.xls.XlsReadContext;
import cn.idev.excel.context.xlsx.DefaultXlsxReadContext;
import cn.idev.excel.context.xlsx.XlsxReadContext;
import cn.idev.excel.exception.ExcelAnalysisException;
import cn.idev.excel.exception.ExcelAnalysisStopException;
import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.read.metadata.ReadWorkbook;
import cn.idev.excel.read.metadata.holder.ReadWorkbookHolder;
import cn.idev.excel.read.metadata.holder.csv.CsvReadWorkbookHolder;
import cn.idev.excel.read.metadata.holder.xls.XlsReadWorkbookHolder;
import cn.idev.excel.read.metadata.holder.xlsx.XlsxReadWorkbookHolder;
import cn.idev.excel.support.ExcelTypeEnum;
import cn.idev.excel.util.ClassUtils;
import cn.idev.excel.util.DateUtils;
import cn.idev.excel.util.FileUtils;
import cn.idev.excel.util.NumberDataFormatterUtils;
import cn.idev.excel.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.filesystem.DocumentFactoryHelper;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;

/**
 * A class that implements the ExcelAnalyser interface for analyzing Excel files. This implementation supports various
 * Excel file formats (XLS, XLSX, CSV) and handles encrypted files as well. It provides methods to perform analysis,
 * manage resources, and clean up after the analysis is complete.
 *
 * <p>Key functionalities include:</p>
 * <ul>
 *     <li>Selecting the appropriate executor based on the Excel file type.</li>
 *     <li>Executing the analysis process with specified sheets or all sheets.</li>
 *     <li>Closing and cleaning up resources such as streams, caches, and temporary files.</li>
 * </ul>
 *
 * @author jipengfei
 */
public class ExcelAnalyserImpl implements ExcelAnalyser {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelAnalyserImpl.class);
    
    /**
     * The context object holding metadata and configuration for the Excel analysis process.
     */
    private AnalysisContext analysisContext;
    
    /**
     * The executor responsible for performing the actual analysis of the Excel file.
     */
    private ExcelReadExecutor excelReadExecutor;
    
    /**
     * Prevent multiple shutdowns
     */
    private boolean finished = false;
    
    public ExcelAnalyserImpl(ReadWorkbook readWorkbook) {
        try {
            chooseExcelExecutor(readWorkbook);
        } catch (RuntimeException e) {
            finish();
            throw e;
        } catch (Throwable e) {
            finish();
            throw new ExcelAnalysisException(e);
        }
    }
    
    /**
     * Chooses the appropriate Excel execution strategy based on the type of Excel file This method deals with different
     * types of Excel files by creating corresponding processing contexts and executors
     *
     * @param readWorkbook The workbook to be read, containing the necessary information for Excel reading
     * @throws Exception if an error occurs while reading the Excel file
     */
    private void chooseExcelExecutor(ReadWorkbook readWorkbook) throws Exception {
        // Determine the type of Excel file based on the provided readWorkbook
        ExcelTypeEnum excelType = ExcelTypeEnum.valueOf(readWorkbook);
        switch (excelType) {
            case XLS:
                POIFSFileSystem poifsFileSystem;
                // Initialize POIFSFileSystem based on whether a file or input stream is provided
                if (readWorkbook.getFile() != null) {
                    poifsFileSystem = new POIFSFileSystem(readWorkbook.getFile());
                } else {
                    poifsFileSystem = new POIFSFileSystem(readWorkbook.getInputStream());
                }
                // So in encrypted excel, it looks like XLS but it's actually XLSX
                if (poifsFileSystem.getRoot().hasEntry(Decryptor.DEFAULT_POIFS_ENTRY)) {
                    InputStream decryptedStream = null;
                    try {
                        // Decrypt the Excel file and treat it as XLSX for processing
                        decryptedStream = DocumentFactoryHelper.getDecryptedStream(
                                poifsFileSystem.getRoot().getFileSystem(), readWorkbook.getPassword());
                        XlsxReadContext xlsxReadContext = new DefaultXlsxReadContext(readWorkbook, ExcelTypeEnum.XLSX);
                        analysisContext = xlsxReadContext;
                        excelReadExecutor = new XlsxSaxAnalyser(xlsxReadContext, decryptedStream);
                        return;
                    } finally {
                        // Close the decrypted stream and POIFSFileSystem to prevent resource leaks
                        IOUtils.closeQuietly(decryptedStream);
                        poifsFileSystem.close();
                    }
                }
                // Set the user password for processing encrypted Excel files
                if (readWorkbook.getPassword() != null) {
                    Biff8EncryptionKey.setCurrentUserPassword(readWorkbook.getPassword());
                }
                XlsReadContext xlsReadContext = new DefaultXlsReadContext(readWorkbook, ExcelTypeEnum.XLS);
                xlsReadContext.xlsReadWorkbookHolder().setPoifsFileSystem(poifsFileSystem);
                analysisContext = xlsReadContext;
                excelReadExecutor = new XlsSaxAnalyser(xlsReadContext);
                break;
            case XLSX:
                // Directly create a context and executor for processing XLSX files
                XlsxReadContext xlsxReadContext = new DefaultXlsxReadContext(readWorkbook, ExcelTypeEnum.XLSX);
                analysisContext = xlsxReadContext;
                excelReadExecutor = new XlsxSaxAnalyser(xlsxReadContext, null);
                break;
            case CSV:
                // Create a context and executor for processing CSV files
                CsvReadContext csvReadContext = new DefaultCsvReadContext(readWorkbook, ExcelTypeEnum.CSV);
                analysisContext = csvReadContext;
                excelReadExecutor = new CsvExcelReadExecutor(csvReadContext);
                break;
            default:
                // Reserved branch for handling potential future Excel types
                break;
        }
    }
    
    
    /**
     * Performs the analysis of the Excel file based on the specified sheets or all sheets. Ensures proper handling of
     * exceptions and resource cleanup in case of errors.
     *
     * @param readSheetList A list of ReadSheet objects specifying which sheets to analyze. Can be null if all sheets
     *                      are to be analyzed.
     * @param readAll       A boolean indicating whether to analyze all sheets in the workbook. If true, the
     *                      readSheetList parameter is ignored.
     * @throws IllegalArgumentException If neither readAll is true nor readSheetList contains any sheets.
     * @throws ExcelAnalysisException   If a non-runtime exception occurs during the analysis.
     */
    @Override
    public void analysis(List<ReadSheet> readSheetList, Boolean readAll) {
        try {
            if (!readAll && CollectionUtils.isEmpty(readSheetList)) {
                throw new IllegalArgumentException("Specify at least one read sheet.");
            }
            analysisContext.readWorkbookHolder().setParameterSheetDataList(readSheetList);
            analysisContext.readWorkbookHolder().setReadAll(readAll);
            try {
                excelReadExecutor.execute();
            } catch (ExcelAnalysisStopException e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Custom stop!");
                }
            }
        } catch (RuntimeException e) {
            finish();
            throw e;
        } catch (Throwable e) {
            finish();
            throw new ExcelAnalysisException(e);
        }
    }
    
    /**
     * Cleans up resources used during the analysis process, including closing streams, destroying caches, and deleting
     * temporary files. Ensures no resource leaks occur.
     */
    @Override
    public void finish() {
        if (finished) {
            return;
        }
        finished = true;
        if (analysisContext == null || analysisContext.readWorkbookHolder() == null) {
            return;
        }
        ReadWorkbookHolder readWorkbookHolder = analysisContext.readWorkbookHolder();
        
        Throwable throwable = null;
        
        try {
            if (readWorkbookHolder.getReadCache() != null) {
                readWorkbookHolder.getReadCache().destroy();
            }
        } catch (Throwable t) {
            throwable = t;
        }
        try {
            if ((readWorkbookHolder instanceof XlsxReadWorkbookHolder)
                    && ((XlsxReadWorkbookHolder) readWorkbookHolder).getOpcPackage() != null) {
                ((XlsxReadWorkbookHolder) readWorkbookHolder).getOpcPackage().revert();
            }
        } catch (Throwable t) {
            throwable = t;
        }
        try {
            if ((readWorkbookHolder instanceof XlsReadWorkbookHolder)
                    && ((XlsReadWorkbookHolder) readWorkbookHolder).getPoifsFileSystem() != null) {
                ((XlsReadWorkbookHolder) readWorkbookHolder).getPoifsFileSystem().close();
            }
        } catch (Throwable t) {
            throwable = t;
        }
        
        // close csv.
        // https://github.com/fast-excel/fastexcel/issues/2309
        try {
            if ((readWorkbookHolder instanceof CsvReadWorkbookHolder)
                    && ((CsvReadWorkbookHolder) readWorkbookHolder).getCsvParser() != null
                    && analysisContext.readWorkbookHolder().getAutoCloseStream()) {
                ((CsvReadWorkbookHolder) readWorkbookHolder).getCsvParser().close();
            }
        } catch (Throwable t) {
            throwable = t;
        }
        
        try {
            if (analysisContext.readWorkbookHolder().getAutoCloseStream()
                    && readWorkbookHolder.getInputStream() != null) {
                readWorkbookHolder.getInputStream().close();
            }
        } catch (Throwable t) {
            throwable = t;
        }
        try {
            if (readWorkbookHolder.getTempFile() != null) {
                FileUtils.delete(readWorkbookHolder.getTempFile());
            }
        } catch (Throwable t) {
            throwable = t;
        }
        
        clearEncrypt03();
        
        removeThreadLocalCache();
        
        if (throwable != null) {
            throw new ExcelAnalysisException("Can not close IO.", throwable);
        }
    }
    
    /**
     * Removes thread-local caches used during the analysis process to free up memory.
     */
    private void removeThreadLocalCache() {
        NumberDataFormatterUtils.removeThreadLocalCache();
        DateUtils.removeThreadLocalCache();
        ClassUtils.removeThreadLocalCache();
    }
    
    /**
     * Clears the encryption key for XLS files to ensure secure handling of sensitive data.
     */
    private void clearEncrypt03() {
        if (StringUtils.isEmpty(analysisContext.readWorkbookHolder().getPassword()) || !ExcelTypeEnum.XLS.equals(
                analysisContext.readWorkbookHolder().getExcelType())) {
            return;
        }
        Biff8EncryptionKey.setCurrentUserPassword(null);
    }
    
    /**
     * Retrieves the executor used for performing the Excel analysis.
     *
     * @return The ExcelReadExecutor instance responsible for the analysis.
     */
    @Override
    public ExcelReadExecutor excelExecutor() {
        return excelReadExecutor;
    }
    
    /**
     * Retrieves the context object used during the Excel analysis process.
     *
     * @return The AnalysisContext instance containing metadata and configuration.
     */
    @Override
    public AnalysisContext analysisContext() {
        return analysisContext;
    }
}
