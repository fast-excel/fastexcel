package cn.idev.excel.test.demo.read;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.ExcelReader;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.format.DateTimeFormat;
import cn.idev.excel.annotation.format.NumberFormat;
import cn.idev.excel.context.AnalysisContext;
import cn.idev.excel.converters.DefaultConverterLoader;
import cn.idev.excel.enums.CellExtraTypeEnum;
import cn.idev.excel.read.listener.PageReadListener;
import cn.idev.excel.read.listener.ReadListener;
import cn.idev.excel.read.metadata.ReadSheet;
import cn.idev.excel.read.metadata.holder.csv.CsvReadWorkbookHolder;
import cn.idev.excel.test.util.TestFileUtil;
import cn.idev.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Common approaches for reading Excel files
 *
 * @author Jiaju Zhuang
 */

@Slf4j
public class ReadTest {

    /**
     * Simplest way to read
     * <p>
     * 1. Create an entity class corresponding to the Excel data structure. Refer to {@link DemoData}.
     * <p>
     * 2. Since EasyExcel reads Excel files row by row, you need to create a callback listener for each row. Refer to {@link DemoDataListener}.
     * <p>
     * 3. Directly read the file.
     */
    @Test
    public void simpleRead() {
        // Approach 1: JDK8+, no need to create a separate DemoDataListener
        // since: 3.0.0-beta1
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // Specify the class to read the data, then read the first sheet. The file stream will be automatically closed.
        // By default, it reads 100 rows at a time. You can process the data directly.
        // The number of rows to read can be set in the constructor of `PageReadListener`.
        EasyExcel.read(fileName, DemoData.class, new PageReadListener<DemoData>(dataList -> {
            for (DemoData demoData : dataList) {
                log.info("Reading a row of data: {}", JSON.toJSONString(demoData));
            }
        })).numRows(2).sheet().doRead();

        // Approach 2:
        // Anonymous inner class, no need to create a separate DemoDataListener
        fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // Specify the class to read the data, then read the first sheet. The file stream will be automatically closed.
        EasyExcel.read(fileName, DemoData.class, new ReadListener<DemoData>() {
            /**
             * Batch size for caching data
             */
            public static final int BATCH_COUNT = 100;
            /**
             * Temporary storage
             */
            private List<DemoData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

            @Override
            public void invoke(DemoData data, AnalysisContext context) {
                cachedDataList.add(data);
                if (cachedDataList.size() >= BATCH_COUNT) {
                    saveData();
                    // Clear the list after saving
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData();
            }

            /**
             * Simulate saving data to the database
             */
            private void saveData() {
                log.info("Saving {} rows of data to the database!", cachedDataList.size());
                log.info("Data saved successfully!");
            }
        }).sheet().doRead();

        // Important note: DemoDataListener should not be managed by Spring. It needs to be instantiated every time you read an Excel file.
        // Approach 3:
        fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // Specify the class to read the data, then read the first sheet. The file stream will be automatically closed.
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();

        // Approach 4
        fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // One reader per file
        try (ExcelReader excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build()) {
            // Build a sheet. You can specify the name or index.
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            readSheet.setNumRows(2);
            // Read a single sheet
            excelReader.read(readSheet);
        }
    }

    @Test
    public void genericHeaderTypeRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "generic-demo.xlsx";
        // Simulate obtaining the Excel header's Class<?> object through any possible means
        Class<?> excelHeaderClass = DemoDataAnother.class;
        EasyExcel.read(fileName, excelHeaderClass, GenericHeaderTypeDataListener.build(excelHeaderClass)).sheet().doRead();
    }

    /**
     * Specify column indexes or names
     * <p>
     * 1. Create an entity class corresponding to the Excel data structure and use the {@link ExcelProperty} annotation. Refer to {@link IndexOrNameData}.
     * <p>
     * 2. Since EasyExcel reads Excel files row by row, you need to create a callback listener for each row. Refer to {@link IndexOrNameDataListener}.
     * <p>
     * 3. Directly read the file.
     */
    @Test
    public void indexOrNameRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // By default, read the first sheet
        EasyExcel.read(fileName, IndexOrNameData.class, new IndexOrNameDataListener()).numRows(1).sheet().doRead();
    }

    /**
     * 读多个或者全部sheet,这里注意一个sheet不能读取多次，多次读取需要重新读取文件
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void repeatedRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 读取全部sheet
        // 这里需要注意 DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个DemoDataListener里面写
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).doReadAll();

        // 读取部分sheet
        fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";

        // 写法1
        try (ExcelReader excelReader = EasyExcel.read(fileName).build()) {
            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            ReadSheet readSheet1 = EasyExcel.readSheet(0).head(DemoData.class)
                    .registerReadListener(new DemoDataListener()).build();
            ReadSheet readSheet2 = EasyExcel.readSheet(1).head(DemoData.class)
                    .registerReadListener(new DemoDataListener()).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(readSheet1, readSheet2);
        }
    }

    /**
     * 日期、数字或者自定义格式转换
     * <p>
     * 默认读的转换器{@link DefaultConverterLoader#loadDefaultReadConverter()}
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ConverterData}.里面可以使用注解{@link DateTimeFormat}、{@link NumberFormat}或者自定义注解
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link ConverterDataListener}
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void converterRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, ConverterData.class, new ConverterDataListener())
                // 这里注意 我们也可以registerConverter来指定自定义转换器， 但是这个转换变成全局了， 所有java为string,excel为string的都会用这个转换器。
                // 如果就想单个字段使用请使用@ExcelProperty 指定converter
                // .registerConverter(new CustomStringStringConverter())
                // 读取sheet
                .sheet().doRead();
    }

    /**
     * 多行头
     *
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>
     * 3. 设置headRowNumber参数，然后读。 这里要注意headRowNumber如果不指定， 会根据你传入的class的{@link ExcelProperty#value()}里面的表头的数量来决定行数，
     * 如果不传入class则默认为1.当然你指定了headRowNumber不管是否传入class都是以你传入的为准。
     */
    @Test
    public void complexHeaderRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet()
                // 这里可以设置1，因为头就是一行。如果多行头，可以设置其他值。不传入也可以，因为默认会根据DemoData 来解析，他没有指定头，也就是默认1行
                .headRowNumber(1).doRead();
    }

    /**
     * Method to read Excel files with headers that support compatibility, such as case sensitivity or simultaneous support for Chinese and English headers.
     *
     * <p>
     * 1. Create an entity object corresponding to the Excel data structure. Refer to {@link DemoCompatibleHeaderData} for implementation details.
     * </p>
     *
     * <p>
     * 2. Since EasyExcel reads the Excel file row by row by default, you need to create a listener that handles each row's data accordingly. Refer to {@link DemoCompatibleHeaderDataListener} for implementation details.
     * In this listener, you should override the `invokeHead` method to transform the uploaded headers as needed.
     * </p>
     *
     * <p>
     * 3. Simply proceed to read the file.
     * </p>
     */
    @Test
    public void compatibleHeaderRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // Specify the class used for reading and choose to read the first sheet.
        EasyExcel.read(fileName, DemoCompatibleHeaderData.class, new DemoCompatibleHeaderDataListener()).sheet().doRead();
    }

    /**
     * 读取表头数据
     *
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoHeadDataListener}
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void headerRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, DemoData.class, new DemoHeadDataListener()).sheet().doRead();
    }

    /**
     * 额外信息（批注、超链接、合并单元格信息读取）
     * <p>
     * 由于是流式读取，没法在读取到单元格数据的时候直接读取到额外信息，所以只能最后通知哪些单元格有哪些额外信息
     *
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoExtraData}
     * <p>
     * 2. 由于默认异步读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoExtraListener}
     * <p>
     * 3. 直接读即可
     *
     * @since 2.2.0-beat1
     */
    @Test
    public void extraRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "extra.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, DemoExtraData.class, new DemoExtraListener())
            // 需要读取批注 默认不读取
            .extraRead(CellExtraTypeEnum.COMMENT)
            // 需要读取超链接 默认不读取
            .extraRead(CellExtraTypeEnum.HYPERLINK)
            // 需要读取合并单元格信息 默认不读取
            .extraRead(CellExtraTypeEnum.MERGE).sheet().doRead();
    }

    /**
     * 读取公式和单元格类型
     *
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link CellDataReadDemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoHeadDataListener}
     * <p>
     * 3. 直接读即可
     *
     * @since 2.2.0-beat1
     */
    @Test
    public void cellDataRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "cellDataDemo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, CellDataReadDemoData.class, new CellDataDemoHeadDataListener()).sheet().doRead();
    }

    /**
     * 数据转换等异常处理
     *
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link ExceptionDemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoExceptionListener}
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void exceptionRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(fileName, ExceptionDemoData.class, new DemoExceptionListener()).sheet().doRead();
    }

    /**
     * 同步的返回，不推荐使用，如果数据量大会把数据放到内存里面
     */
    @Test
    public void synchronousRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        List<DemoData> list = EasyExcel.read(fileName).head(DemoData.class).sheet().doReadSync();
        for (DemoData data : list) {
            log.info("读取到数据:{}", JSON.toJSONString(data));
        }

        // 这里 也可以不指定class，返回一个list，然后读取第一个sheet 同步读取会自动finish
        List<Map<Integer, String>> listMap = EasyExcel.read(fileName).sheet().doReadSync();
        for (Map<Integer, String> data : listMap) {
            // 返回每条数据的键值对 表示所在的列 和所在列的值
            log.info("读取到数据:{}", JSON.toJSONString(data));
        }
    }

    /**
     * 不创建对象的读
     */
    @Test
    public void noModelRead() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        // 这里 只要，然后读取第一个sheet 同步读取会自动finish
        EasyExcel.read(fileName, new NoModelDataListener()).sheet().doRead();
    }

    /**
     * 自定义修改csv配置
     */
    @Test
    public void csvFormat() {
        String fileName = TestFileUtil.getPath() + "demo" + File.separator + "demo.csv";
        try (ExcelReader excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build()) {
            // 判断是 csv 文件
            if (excelReader.analysisContext().readWorkbookHolder() instanceof CsvReadWorkbookHolder) {
                CsvReadWorkbookHolder csvReadWorkbookHolder = (CsvReadWorkbookHolder)excelReader.analysisContext()
                    .readWorkbookHolder();
                // 设置成逗号分隔 当然默认也是逗号分隔
                // 这里要注意 withDelimiter 会重新生成一个 所以要放回去
                csvReadWorkbookHolder.setCsvFormat(csvReadWorkbookHolder.getCsvFormat().withDelimiter(','));
            }

            // 拿到所有 sheet
            List<ReadSheet> readSheetList = excelReader.excelExecutor().sheetList();
            // 如果只想读取第一个 咋样传入参数即可
            //ReadSheet readSheet = EasyExcel.readSheet(0).build();
            excelReader.read(readSheetList);
        }
    }
}
