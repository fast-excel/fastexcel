package cn.idev.excel.analysis;

import cn.idev.excel.analysis.v07.XlsxSaxAnalyser;
import cn.idev.excel.exception.ExcelAnalysisException;
import cn.idev.excel.exception.ExcelAnalysisStopException;
import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.read.metadata.ReadWorkbook;
import cn.idev.excel.read.metadata.holder.ReadWorkbookHolder;
import cn.idev.excel.read.metadata.holder.csv.CsvReadWorkbookHolder;
import cn.idev.excel.read.metadata.holder.xls.XlsReadWorkbookHolder;
import cn.idev.excel.read.metadata.holder.xlsx.XlsxReadWorkbookHolder;
import cn.idev.excel.analysis.csv.CsvExcelReadExecutor;
import cn.idev.excel.analysis.v03.XlsSaxAnalyser;
import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.context.csv.CsvReadContext;
import cn.idev.excel.context.csv.DefaultCsvReadContext;
import cn.idev.excel.context.xls.DefaultXlsReadContext;
import cn.idev.excel.context.xls.XlsReadContext;
import cn.idev.excel.context.xlsx.DefaultXlsxReadContext;
import cn.idev.excel.context.xlsx.XlsxReadContext;
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
 * @author jipengfei
 */
public class ExcelAnalyserImpl implements ExcelAnalyser {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelAnalyserImpl.class);

    private AnalysisContext analysisContext;

    private ExcelReadExecutor excelReadExecutor;
    /**
     * Prevent multiple shutdowns
     */
    private boolean finished = false;

    public ExcelAnalyserImpl(ReadWorkbook readWorkbook) {
        try {
            choiceExcelExecutor(readWorkbook);
        } catch (RuntimeException e) {
            finish();
            throw e;
        } catch (Throwable e) {
            finish();
            throw new ExcelAnalysisException(e);
        }
    }

    private void choiceExcelExecutor(ReadWorkbook readWorkbook) throws Exception {
        ExcelTypeEnum excelType = ExcelTypeEnum.valueOf(readWorkbook);
        switch (excelType) {
            case XLS:
                POIFSFileSystem poifsFileSystem;
                if (readWorkbook.getFile() != null) {
                    poifsFileSystem = new POIFSFileSystem(readWorkbook.getFile());
                } else {
                    poifsFileSystem = new POIFSFileSystem(readWorkbook.getInputStream());
                }
                // So in encrypted excel, it looks like XLS but it's actually XLSX
                if (poifsFileSystem.getRoot().hasEntry(Decryptor.DEFAULT_POIFS_ENTRY)) {
                    InputStream decryptedStream = null;
                    try {
                        decryptedStream = DocumentFactoryHelper
                            .getDecryptedStream(poifsFileSystem.getRoot().getFileSystem(), readWorkbook.getPassword());
                        XlsxReadContext xlsxReadContext = new DefaultXlsxReadContext(readWorkbook, ExcelTypeEnum.XLSX);
                        analysisContext = xlsxReadContext;
                        excelReadExecutor = new XlsxSaxAnalyser(xlsxReadContext, decryptedStream);
                        return;
                    } finally {
                        IOUtils.closeQuietly(decryptedStream);
                        // as we processed the full stream already, we can close the filesystem here
                        // otherwise file handles are leaked
                        poifsFileSystem.close();
                    }
                }
                if (readWorkbook.getPassword() != null) {
                    Biff8EncryptionKey.setCurrentUserPassword(readWorkbook.getPassword());
                }
                XlsReadContext xlsReadContext = new DefaultXlsReadContext(readWorkbook, ExcelTypeEnum.XLS);
                xlsReadContext.xlsReadWorkbookHolder().setPoifsFileSystem(poifsFileSystem);
                analysisContext = xlsReadContext;
                excelReadExecutor = new XlsSaxAnalyser(xlsReadContext);
                break;
            case XLSX:
                XlsxReadContext xlsxReadContext = new DefaultXlsxReadContext(readWorkbook, ExcelTypeEnum.XLSX);
                analysisContext = xlsxReadContext;
                excelReadExecutor = new XlsxSaxAnalyser(xlsxReadContext, null);
                break;
            case CSV:
                CsvReadContext csvReadContext = new DefaultCsvReadContext(readWorkbook, ExcelTypeEnum.CSV);
                analysisContext = csvReadContext;
                excelReadExecutor = new CsvExcelReadExecutor(csvReadContext);
                break;
            default:
                break;
        }
    }

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
        // https://github.com/CodePhiliaX/fastexcel/issues/2309
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

    private void removeThreadLocalCache() {
        NumberDataFormatterUtils.removeThreadLocalCache();
        DateUtils.removeThreadLocalCache();
        ClassUtils.removeThreadLocalCache();
    }

    private void clearEncrypt03() {
        if (StringUtils.isEmpty(analysisContext.readWorkbookHolder().getPassword())
            || !ExcelTypeEnum.XLS.equals(analysisContext.readWorkbookHolder().getExcelType())) {
            return;
        }
        Biff8EncryptionKey.setCurrentUserPassword(null);
    }

    @Override
    public ExcelReadExecutor excelExecutor() {
        return excelReadExecutor;
    }

    @Override
    public AnalysisContext analysisContext() {
        return analysisContext;
    }
}
