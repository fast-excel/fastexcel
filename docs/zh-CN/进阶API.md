---
title: 进阶API
description: 高级API
---

FastExcel 提供了一套简单易用的 API 来处理 Excel 文件，支持读、写、拦截器、自定义样式等高级特性，让开发者可以灵活处理各种复杂的业务场景。



# 核心概念
如果你需要对 excel 进行大量或者详细的读写操作，你需要知道一些类，FastExcel 中比较重要的概念和类。它们在你尝试自定义操作时，能够提供丰富的选项。
- `FastExcel`：入口类，用于构建开始各种操作
- `ExcelReaderBuilder`：ExcelWriterBuilder 构建出一个 ReadWorkbook WriteWorkbook，可以理解成一个excel对象，一个excel只要构建一个
- `ExcelReaderSheetBuilder`：ExcelWriterSheetBuilder 构建出一个 ReadSheet WriteSheet对象，可以理解成excel里面的一页,每一页都要构建一个
- `ReadListener`：在每一行读取完毕后都会调用ReadListener来处理数据
- `WriteHandler`：在每一个操作包括创建单元格、创建表格等都会调用WriteHandler来处理数据

所有配置都是继承的，Workbook的配置会被Sheet继承，所以在用FastExcel设置参数的时候，在FastExcel...sheet()方法之前作用域是整个sheet,之后针对单个sheet


# 实体类注释

实体类是读写操作的基础。FastExcel 提供了多种注解，帮助开发者轻松定义字段和格式。
## **`@ExcelProperty`**
定义 Excel 列名和映射的字段名。 具体参数如下：

| 名称                  | 默认值               | 描述                                                                                                                                                  |
|---------------------|-------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| value           | 空                 | 用于匹配excel中的头，必须全匹配,如果有多行头，会匹配最后一行头                                                                                                                  |
| order           | Integer.MAX_VALUE | 优先级高于`value`，会根据`order`的顺序来匹配实体和excel中数据的顺序                                                                                                         |
| index           | &#45;1            | 优先级高于`value`和`order`，会根据`index`直接指定到excel中具体的哪一列                                                                                                    |
| converter           | 自动选择              | 指定当前字段用什么转换器，默认会自动选择。读的情况下只要实现`cn.idev.excel.converters.Converter#convertToJavaData(com.idev.excel.converters.ReadConverterContext<?>)` 方法即可 |

## **`@ColumnWidth`**
指定列宽。

## **`@DateTimeFormat`**
日期转换，用`String`去接收excel日期格式的数据会调用这个注解,参数如下：

| 名称                  | 默认值  | 描述                                                             |
|---------------------|------|----------------------------------------------------------------|
| value           | 空    | 参照`java.text.SimpleDateFormat`书写即可                             |
| use1904windowing           | 自动选择 | excel中时间是存储1900年起的一个双精度浮点数，但是有时候默认开始日期是1904，所以设置这个值改成默认1904年开始 |

## **`@NumberFormat`**

数字转换，用`String`去接收excel数字格式的数据会调用这个注解。

| 名称                  | 默认值  | 描述                          |
|---------------------|------|-----------------------------|
| value           | 空    | 参照`java.text.DecimalFormat`书写即可 |
| roundingMode           | RoundingMode.HALF_UP | 格式化的时候设置舍入模式                    |



# 读操作

`ReadWorkbook`,`ReadSheet` 都会有的参数，如果为空，默认使用上级。
| 名称                  | 默认值   | 描述                                                       |
|---------------------|-------|----------------------------------------------------------|
| converter           | 空     | 默认加载了很多转换器，这里可以加入不支持的字段                                  |
| readListener           | 空     | 可以注册多个监听器，读取excel的时候会不断的回调监听器中的方法                        |
| headRowNumber           | 1     | excel中头的行数，默认1行                                          |
| head           | 空     | 与`clazz`二选一。读取文件头对应的列表，会根据列表匹配数据，建议使用class               |
| clazz           | 空     | 与`head`二选一。读取文件的头对应的class，也可以使用注解。如果两个都不指定，则会读取全部数据      |
| autoTrim           | true  | 会对头、读取数据等进行自动trim                                        |
| use1904windowing           | false | excel中时间是存储1900年起的一个双精度浮点数，但是有时候默认开始日期是1904，所以设置这个值改成默认1904年开始 |
| useScientificFormat           | false | 数字转文本的时候在较大的数值的是否是否采用科学计数法                               |
## ReadWorkbook（理解成excel对象）参数
| 名称                  | 默认值                                                     | 描述                                                                                                                                                                                                                                                                                                                           |
|---------------------|---------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| excelType           | 空                                                       | 当前excel的类型,支持XLS、XLSX、CSV                                                                                                                                                                                                                                                                                                    |
| inputStream           | 空                                                       | 与`file`二选一。读取文件的流，如果接收到的是流就只用，不用流建议使用`file`参数。因为使用了`inputStream` FastExcel会帮忙创建临时文件，最终还是`file`                                                                                                                                                                                                                               |
| file           | 空                                                       | 与`inputStream`二选一。读取文件的文件。                                                                                                                                                                                                                                                                                                   |
| mandatoryUseInputStream           | false                                                   | 强制使用  `inputStream` 来创建对象，性能会变差，但是不会创建临文件。                                                                                                                                                                                                                                                                                   |
| charset           | Charset#defaultCharset                 | 只有csv文件有用，读取文件的时候使用的编码                                                                                                                                                                                                                                                                                                       |
| autoCloseStream           | true                                                    | 自动关闭读取的流。                                                                                                                                                                                                                                                                                                                    |
| readCache           | 空                                                       | 默认小于5M用 内存，超过5M会使用 `EhCache`,这里不建议使用这个参数。                                                                                                                                                                                                                                                                                    |
| readCacheSelector           | SimpleReadCacheSelector | 用于选择什么时候用内存去存储临时数据，什么时候用磁盘存储临时数据                                                                                                                                                                                                                                                                                             |
| ignoreEmptyRow           | true                                                    | 忽略空的行                                                                                                                                                                                                                                                                                                                        |
| password           | 空                                                       | 读取文件的密码                                                                                                                                                                                                                                                                                                                      |
| xlsxSAXParserFactoryName           | 空                                                       | 指定sax读取使用的class的名称，例如：`com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl`                                                                                                                                                                                                                                         |
| useDefaultListener           | true                                                    | `@since 2.1.4` <br/>默认会加入`ModelBuildEventListener` 来帮忙转换成传入`class`的对象，设置成`false`后将不会协助转换对象，自定义的监听器会接收到`Map<Integer,CellData>`对象，如果还想继续接听到`class`对象，请调用`readListener`方法，加入自定义的`beforeListener`、 `ModelBuildEventListener`、 自定义的`afterListener`即可。                                                                             |
| extraReadSet           | 空                                                       | 额外需要读取内容的set，默认不读取这些数据                                                                                                                                                                                                                                                                                                       |
| readDefaultReturn           | STRING                                                       | `@since 3.2.0`<br/>STRING:会返回一个Map&lt;IntegerString&gt;的数组，返回值就是你在excel里面不点击单元格看到的内容<br/>   ACTUAL_DATA：会返回一个Map&lt;Integer,Object&gt;的数组，返回实际上存储的数据，会帮自动转换类型，Object类型为`BigDecimal`、`Boolean`、`String`、`LocalDateTime`、null，中的一个，<br/>READ_CELL_DATA: 会返回一个Map&lt;Integer,ReadCellData&lt;?&gt;&gt;的数组,其中`?`类型参照ACTUAL_DATA的 |

## ReadSheet（就是excel的一个Sheet）参数
| 名称                  | 默认值 | 描述                                  |
|---------------------|-----|-------------------------------------|
| sheetNo           | 0   | 需要读取Sheet的编码，建议使用这个来指定读取哪个Sheet            |
| sheetName           | 空   | 根据名字去匹配Sheet                     |

# 写操作

## 通用参数
`WriteWorkbook`,`WriteSheet` ,`WriteTable`都会有的参数，如果为空，默认使用上级。
| 名称                  | 默认值   | 描述                                                                                                                                                                                                                                                                                  |
|---------------------|-------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| converter           | 空     | 默认加载了很多转换器，这里可以加入不支持的字段                                                                                                                                                                                                                                                             |
| writeHandler           | 空     | 写的处理器。可以实现`WorkbookWriteHandler`,`SheetWriteHandler`,`RowWriteHandler`,`CellWriteHandler`，在写入excel的不同阶段会调用                                                                                                                                                                          |
| relativeHeadRowIndex           | 0     | 写入到excel和上面空开几行                                                                                                                                                                                                                                                                     |
| head           | 空     | 与`clazz`二选一。读取文件头对应的列表，会根据列表匹配数据，建议使用class                                                                                                                                                                                                                                          |
| clazz           | 空     | 与`head`二选一。读取文件的头对应的class，也可以使用注解。如果两个都不指定，则会读取全部数据                                                                                                                                                                                                                                 |
| autoTrim           | true  | 会对头、读取数据等进行自动trim                                                                                                                                                                                                                                                                   |
| use1904windowing           | false | excel中时间是存储1900年起的一个双精度浮点数，但是有时候默认开始日期是1904，所以设置这个值改成默认1904年开始                                                                                                                                                                                                                      |
| useScientificFormat           | false | 数字转文本的时候在较大的数值的是否是否采用科学计数法                                                                                                                                                                                                                                                          |
| needHead           | true  | 是否需要写入头到excel                                                                                                                                                                                                                                                                       |
| useDefaultStyle           | true  | 是否使用默认的样式                                                                                                                                                                                                                                                                           |
| automaticMergeHead           | true  | 自动合并头，头中相同的字段上下左右都会去尝试匹配                                                                                                                                                                                                                                                            |
| excludeColumnIndexes           | 空     | 需要排除对象中的index的数据                                                                                                                                                                                                                                                                    |
| excludeColumnFieldNames           | 空     | 需要排除对象中的字段的数据                                                                                                                                                                                                                                                                       |
| includeColumnIndexes           | 空     | 只要导出对象中的index的数据                                                                                                                                                                                                                                                                    |
| includeColumnFieldNames           | 空     | 只要导出对象中的字段的数据                                                                                                                                                                                                                                                                       |
| orderByIncludeColumn           | false | @since 3.3.0 在使用了参数includeColumnFieldNames 或者 includeColumnIndexes的时候，会根据传入集合的顺序排序                                                                                                                                                                                                  |
| filedCacheLocation           | THREAD_LOCAL | @since 3.3.0 解析class的field会有缓存，以前全局放到Map里面，3.3.0 以后默认放到ThreadLocal ，也就是说每次读写都会重新解析class，可以反射修改class的注解，并发场景不会相互影响。<br/>  THREAD_LOCAL：默认，每次读写都会缓存，但是不是同一次不会影响<br/>  MEMORY：放到全局的内存里面，理论上性能会更好，但是无法通过反射、排除等方法修改导出的对象<br/> NONE：不缓存，性能会变差，涉及到读的同时要写，而且还要反射、排除等方法去修改对象的情况下可以考虑使用。<br/> |

## WriteWorkbook（理解成excel对象）参数
| 名称                  | 默认值                    | 描述                                       |
|---------------------|------------------------|------------------------------------------|
| excelType           | 空                      | 当前excel的类型,支持XLS、XLSX、CSV                |
| outputStream           | 空                      | 与`file`二选一。写入文件的流                        |
| file           | 空                      | 与`outputStream`二选一。写入的文件                 |
| templateInputStream           | 空                      | 模板的文件流                                   |
| templateFile           | 空                      | 模板文件                                     |
| charset           | Charset#defaultCharset | 只有csv文件有用，写入文件的时候使用的编码                   |
| autoCloseStream           | true                   | 自动关闭写入的流。                                |
| password           | 空                      | 读取文件的密码                                  |
| inMemory           | false                  | 是否在内存处理，默认会生成临时文件以节约内存。内存模式效率会更好，但是容易OOM |
| writeExcelOnException           | false                  | 写入过程中抛出异常了，是否尝试把数据写入到excel               |
## WriteSheet（就是excel的一个Sheet）参数
| 名称 | 默认值 | 描述 |
|---------------------|-----|---------------------------------|
| sheetNo | 0 | 需要写入的编码 |
| sheetName | 空 | 需要写的Sheet名称，默认同`sheetNo`                |
## WriteTable（就把excel的一个Sheet,一块区域看一个table）参数
| 名称                  | 默认值 | 描述                              |
|---------------------|-----|---------------------------------|
| tableNo           | 0   | 需要写入的编码            |

#  **WriteHandler**

**`WriteHandler`** 是 FastExcel 提供的接口，用于在写入 Excel 文件时拦截写入过程，允许开发者自定义操作，如设置单元格样式、合并单元格、添加超链接、插入批注等。
通过实现 `WriteHandler`，开发者可以深入控制写入流程，以满足复杂的业务需求。

---

## **WriteHandler 接口分类**

FastExcel 提供了以下几种 WriteHandler 接口，分别用于处理不同的写入场景：

| 接口名                     | 描述                                                                 |
|----------------------------|----------------------------------------------------------------------|
| **CellWriteHandler**        | 单元格级别的拦截器，允许对单元格数据和样式进行自定义操作              |
| **RowWriteHandler**         | 行级别的拦截器，用于在行数据写入完成后执行额外操作                  |
| **SheetWriteHandler**       | 工作表级别的拦截器，可用于设置工作表级别的属性（如冻结窗格、下拉框等） |

---

## **实现 WriteHandler 的步骤**

1. **实现对应的 `WriteHandler` 接口**：
    - 选择适合需求的接口（`CellWriteHandler`、`RowWriteHandler` 或 `SheetWriteHandler`）。
    - 实现接口中的方法，在方法中定义自定义逻辑。

2. **注册 WriteHandler**：
    - 在调用 `FastExcel.write()` 时通过 `.registerWriteHandler()` 注册自定义的 WriteHandler。

---

## **CellWriteHandler 示例：设置单元格样式**

### 功能：为所有内容单元格设置背景颜色为黄色，字体为蓝色。
```java
@Slf4j
public class CustomCellStyleHandler implements CellWriteHandler {

    @Override
    public void afterCellDispose(CellWriteHandlerContext context) {
        // 确保只操作内容单元格（非表头）
        if (BooleanUtils.isNotTrue(context.getHead())) {
            WriteCellData<?> cellData = context.getFirstCellData();
            WriteCellStyle style = cellData.getOrCreateStyle();

            // 设置背景颜色为黄色
            style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            style.setFillPatternType(FillPatternType.SOLID_FOREGROUND);

            // 设置字体为蓝色
            WriteFont font = new WriteFont();
            font.setColor(IndexedColors.BLUE.getIndex());
            style.setWriteFont(font);

            log.info("已设置样式: 行 {}, 列 {}", context.getRowIndex(), context.getColumnIndex());
        }
    }
}
```

### 注册并使用
```java
@Test
public void customCellStyleWrite() {
    String fileName = "customCellStyleWrite.xlsx";

    FastExcel.write(fileName, DemoData.class)
        .registerWriteHandler(new CustomCellStyleHandler())
        .sheet("自定义样式")
        .doWrite(data());
}
```

---

## **RowWriteHandler 示例：插入批注**

### 功能：为表头的第一行第二列插入批注。
```java
@Slf4j
public class CommentRowWriteHandler implements RowWriteHandler {

    @Override
    public void afterRowDispose(RowWriteHandlerContext context) {
        if (BooleanUtils.isTrue(context.getHead())) {
            Sheet sheet = context.getWriteSheetHolder().getSheet();
            Drawing<?> drawing = sheet.createDrawingPatriarch();

            // 创建批注
            Comment comment = drawing.createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 1, 0, (short) 2, 1));
            comment.setString(new XSSFRichTextString("这是一个批注"));
            sheet.getRow(0).getCell(1).setCellComment(comment);

            log.info("批注已插入到第一行第二列");
        }
    }
}
```

### 注册并使用
```java
@Test
public void commentWrite() {
    String fileName = "commentWrite.xlsx";

    FastExcel.write(fileName, DemoData.class)
        .registerWriteHandler(new CommentRowWriteHandler())
        .sheet("插入批注")
        .doWrite(data());
}
```

---

## **SheetWriteHandler 示例：添加下拉框**

### 功能：为第一列的前两行数据添加下拉框。
```java
@Slf4j
public class DropdownSheetWriteHandler implements SheetWriteHandler {

    @Override
    public void afterSheetCreate(SheetWriteHandlerContext context) {
        Sheet sheet = context.getWriteSheetHolder().getSheet();

        // 创建下拉框区域
        CellRangeAddressList range = new CellRangeAddressList(1, 2, 0, 0);
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createExplicitListConstraint(new String[]{"选项1", "选项2"});
        DataValidation validation = helper.createValidation(constraint, range);
        sheet.addValidationData(validation);

        log.info("下拉框已添加到第一列的前两行");
    }
}
```

### 注册并使用
```java
@Test
public void dropdownWrite() {
    String fileName = "dropdownWrite.xlsx";

    FastExcel.write(fileName, DemoData.class)
        .registerWriteHandler(new DropdownSheetWriteHandler())
        .sheet("添加下拉框")
        .doWrite(data());
}
```

---

## **总结**

| WriteHandler 类型    | 使用场景                          | 示例                       |
|---------------------|-----------------------------------|---------------------------|
| **CellWriteHandler** | 操作单元格的样式、内容、合并等      | 设置背景色、字体等样式      |
| **RowWriteHandler**  | 针对整行操作，如批注插入、行级样式 | 插入批注                   |
| **SheetWriteHandler**| 针对整表操作，如冻结窗格、下拉框   | 添加下拉框、设置冻结窗格    |


#  **ReadListener**

**`ReadListener`** 是 FastExcel 提供的接口，用于在读取 Excel 文件时对每一行数据进行处理。
它是 FastExcel 核心组件之一，允许开发者实现自定义逻辑来处理数据行、处理表头，甚至在读取完成后执行特定操作。

---

## **ReadListener 的核心方法**

`ReadListener` 是一个泛型接口，泛型类型是要读取的对象类型（如 `DemoData`）。其核心方法如下：

| 方法                                 | 描述                                                                                 |
|------------------------------------|------------------------------------------------------------------------------------|
| `void invoke(T data, AnalysisContext context)` | 当读取到一行数据时触发，`data` 是解析后的当前行对象，`context` 包含读取的上下文信息。|
| `void doAfterAllAnalysed(AnalysisContext context)` | 在所有数据解析完成后调用，可用于资源释放或统计数据处理。                             |
| `void onException(Exception exception, AnalysisContext context)` *(可选)* | 捕获读取过程中的异常，方便处理解析错误。                                             |
| `void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context)` *(可选)* | 获取 Excel 表头信息，用于动态处理表头。                                             |

---

### **实现 ReadListener 的步骤**

1. **实现 `ReadListener` 接口**：
    - 使用实体类作为泛型类型（如 `ReadListener<DemoData>`）。
    - 实现核心方法，根据需要添加数据处理逻辑。

2. **在读取时注册自定义 `ReadListener`**：
    - 调用 `FastExcel.read()` 方法时，传入自定义监听器实例。

---

## **基本示例：处理行数据**

### 示例实体类
```java
@Getter
@Setter
@EqualsAndHashCode
public class DemoData {
    private String string;
    private Date date;
    private Double doubleData;
}
```

### 自定义 ReadListener
```java
@Slf4j
public class DemoDataListener implements ReadListener<DemoData> {

    private static final int BATCH_COUNT = 100; // 批量处理阈值
    private List<DemoData> cachedDataList = new ArrayList<>(BATCH_COUNT);

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        log.info("解析到一条数据: {}", JSON.toJSONString(data));
        cachedDataList.add(data);

        // 达到批量阈值，执行处理
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 处理剩余数据
        saveData();
        log.info("所有数据解析完成！");
    }

    private void saveData() {
        log.info("存储 {} 条数据到数据库", cachedDataList.size());
        // TODO: 实现批量入库逻辑
    }
}
```

### 使用示例
```java
@Test
public void simpleRead() {
    String fileName = "path/to/demo.xlsx";

    FastExcel.read(fileName, DemoData.class, new DemoDataListener())
        .sheet() // 默认读取第一个 Sheet
        .doRead();
}
```

---

## **处理表头**

### 自定义 ReadListener（获取表头）
```java
@Slf4j
public class HeadDataListener implements ReadListener<DemoData> {

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        log.info("解析到表头: {}", JSON.toJSONString(headMap));
    }

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        log.info("解析到数据: {}", JSON.toJSONString(data));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }
}
```

### 使用示例
```java
@Test
public void readWithHead() {
    String fileName = "path/to/demo.xlsx";

    FastExcel.read(fileName, DemoData.class, new HeadDataListener())
        .sheet() // 默认读取第一个 Sheet
        .doRead();
}
```

---

## **异常处理**

### 自定义 ReadListener（处理异常）
```java
@Slf4j
public class ExceptionHandlingListener implements ReadListener<DemoData> {

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析异常: {}", exception.getMessage());
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException ex = (ExcelDataConvertException) exception;
            log.error("第 {} 行, 第 {} 列解析错误，数据: {}", ex.getRowIndex(), ex.getColumnIndex(), ex.getCellData());
        }
    }

    @Override
    public void invoke(DemoData data, AnalysisContext context) {}

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {}
}
```

### 使用示例
```java
@Test
public void readWithExceptionHandling() {
    String fileName = "path/to/demo.xlsx";

    FastExcel.read(fileName, DemoData.class, new ExceptionHandlingListener())
        .sheet()
        .doRead();
}
```

---

## **高级功能：分页处理**

### 示例代码
```java
@Test
public void pageRead() {
    String fileName = "path/to/demo.xlsx";

    FastExcel.read(fileName, DemoData.class, new PageReadListener<>(dataList -> {
        // 分页批量处理
        log.info("读取到一批数据: {}", JSON.toJSONString(dataList));
        // TODO: 实现数据处理逻辑
    }))
    .sheet()
    .doRead();
}
```

> **说明**：
> - `PageReadListener` 是 FastExcel 提供的便捷工具类，支持基于分页的批量处理。
> - 默认分页大小为 100，可通过构造器指定。

---

## **总结**

| 方法                                 | 场景                         | 示例                       |
|------------------------------------|----------------------------|---------------------------|
| `invoke`                           | 处理行数据                    | 基本示例                  |
| `doAfterAllAnalysed`               | 在所有数据读取完成后执行逻辑     | 批量保存                  |
| `invokeHead`                       | 解析表头信息                  | 表头处理                  |
| `onException`                      | 捕获解析过程中的异常            | 异常处理                  |

通过实现和注册 **ReadListener**，开发者可以自定义读取过程的各个环节，轻松应对各种业务需求，包括动态解析、异常捕获、批量处理等。



# **`AnalysisEventListener`**

`AnalysisEventListener` 是 **FastExcel** 中用于处理读取 Excel 数据的核心监听器。它基于事件驱动机制，允许开发者在读取每一行数据时执行自定义操作，并在所有数据解析完成后进行相应处理。它通常用于流式读取大量数据，适合需要处理大数据量、进行批量操作（如批量插入数据库）的场景。

## **核心特性**

- **逐行读取**：`AnalysisEventListener` 按行读取 Excel 文件的数据，在读取每行数据时执行 `invoke` 方法，适合流式处理。
- **内存控制**：可以设置 `BATCH_COUNT` 来控制每次处理的数据量，避免内存溢出。
- **批量处理**：可以缓存一定数量的数据并批量处理，适用于大数据量场景。
- **事件驱动**：当读取每一行数据时，调用 `invoke` 方法；所有数据读取完毕后，调用 `doAfterAllAnalysed` 方法。

## **方法**

`AnalysisEventListener` 主要包含以下方法：

| 方法名                          | 描述                                           |
|---------------------------------|------------------------------------------------|
| `invoke(T data, AnalysisContext context)`   | 当读取到一行数据时触发，`data` 为解析后的当前行数据，`context` 为读取上下文。 |
| `doAfterAllAnalysed(AnalysisContext context)` | 在所有数据解析完成后调用，用于资源清理或批量操作后处理。   |
| `onException(Exception exception, AnalysisContext context)` *(可选)* | 捕获并处理解析过程中抛出的异常，方便处理错误数据。 |
| `invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context)` *(可选)* | 获取 Excel 表头数据，常用于动态表头处理。 |

---

### **如何使用 `AnalysisEventListener`**

1. **继承 `AnalysisEventListener`** 并实现其方法。
2. **在读取时传入自定义监听器**，通过 `FastExcel.read()` 注册。

#### **示例 1：处理每行数据并批量入库**

```java
@Slf4j
public class DemoDataListener extends AnalysisEventListener<DemoData> {

    private static final int BATCH_COUNT = 100;  // 每批处理的数据量
    private List<DemoData> cachedDataList = new ArrayList<>(BATCH_COUNT);

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        log.info("解析到一条数据: {}", JSON.toJSONString(data));
        cachedDataList.add(data);

        // 如果缓存的数据量达到 BATCH_COUNT，执行批量处理
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList.clear();  // 清空缓存
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 处理剩余的数据
        if (!cachedDataList.isEmpty()) {
            saveData();
        }
        log.info("所有数据解析完成！");
    }

    private void saveData() {
        log.info("存储 {} 条数据到数据库", cachedDataList.size());
        // TODO: 批量保存到数据库
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析过程中发生异常: {}", exception.getMessage());
        // 可以记录异常数据，或者重新抛出异常
    }
}
```

#### **使用示例**

```java
@Test
public void simpleRead() {
    String fileName = "path/to/demo.xlsx";

    FastExcel.read(fileName, DemoData.class, new DemoDataListener())
        .sheet()  // 默认读取第一个 Sheet
        .doRead();
}
```

### **如何处理表头**

可以使用 `invokeHead` 方法获取表头信息，用于处理动态表头场景，或者进行表头数据的自定义解析。

#### **示例 2：获取并处理表头**

```java
@Slf4j
public class DemoDataListenerWithHead extends AnalysisEventListener<DemoData> {

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        log.info("解析到表头数据: {}", JSON.toJSONString(headMap));
    }

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        log.info("解析到数据: {}", JSON.toJSONString(data));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("读取过程中发生异常: {}", exception.getMessage());
    }
}
```

#### **使用示例**

```java
@Test
public void readWithHead() {
    String fileName = "path/to/demo.xlsx";

    FastExcel.read(fileName, DemoData.class, new DemoDataListenerWithHead())
        .sheet()  // 默认读取第一个 Sheet
        .doRead();
}
```

---

### **异常处理**

`AnalysisEventListener` 提供了 `onException` 方法，开发者可以在读取过程中捕获异常，并进行处理（例如记录错误、跳过错误行等）。

#### **示例 3：异常处理**

```java
@Slf4j
public class ExceptionHandlingListener extends AnalysisEventListener<DemoData> {

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析异常: {}", exception.getMessage());
        // 捕获解析异常后，进行自定义处理
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException ex = (ExcelDataConvertException) exception;
            log.error("第 {} 行, 第 {} 列解析异常, 数据: {}", ex.getRowIndex(), ex.getColumnIndex(), ex.getCellData());
        }
    }

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        // 处理正常行数据
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }
}
```

#### **使用示例**

```java
@Test
public void readWithExceptionHandling() {
    String fileName = "path/to/demo.xlsx";

    FastExcel.read(fileName, DemoData.class, new ExceptionHandlingListener())
        .sheet()
        .doRead();
}
```

---

## **总结**

`AnalysisEventListener` 提供了灵活的数据读取处理能力，适合处理复杂的数据导入场景，特别是在需要对每一行数据进行自定义操作（如批量存储、动态表头处理、异常处理等）时非常有用。

| 特性                       | `AnalysisEventListener`                |
|----------------------------|---------------------------------------|
| **逐行读取**                | 支持按行读取，处理每一行数据            |
| **内存控制**                | 支持批量处理，缓存一定数量的数据并批量处理 |
| **事件驱动**                | `invoke`（逐行处理），`doAfterAllAnalysed`（所有数据处理完成后） |
| **异常处理**                | 支持捕获解析过程中的异常并自定义处理      |
| **表头处理**                | 支持通过 `invokeHead` 获取和处理表头数据  |

通过 `AnalysisEventListener`，你可以实现更复杂的 Excel 数据处理需求，如批量插入数据库、处理大量数据时的内存优化、对每行数据进行精确的操作等。



# **`AnalysisEventListener` 与 `ReadListener` 的区别**

`AnalysisEventListener` 和 `ReadListener` 都是 FastExcel 提供的接口，目的是为了让开发者在读取 Excel 时进行自定义处理，但它们在功能和使用场景上有一些关键的区别。

## **1. `AnalysisEventListener`**

`AnalysisEventListener` 是 FastExcel 的底层监听器，主要用于事件驱动的逐行读取，适用于需要对每一行数据进行操作的场景。

### **主要特点：**
- **逐行读取**：`AnalysisEventListener` 每次读取一行数据，适合流式处理或需要按行进行大数据处理的场景。
- **事件驱动**：每次读取一行时，会触发 `invoke` 方法，读取完所有数据后，会调用 `doAfterAllAnalysed` 方法。
- **内存控制**：支持内存中的数据批处理（`BATCH_COUNT`）机制，可以缓存一定数量的数据并进行批量处理。

### **方法：**
- `invoke(T data, AnalysisContext context)`：读取到一行数据时调用该方法。
- `doAfterAllAnalysed(AnalysisContext context)`：所有数据读取完成时调用。
- `onException(Exception exception, AnalysisContext context)`：处理异常，捕获读取过程中的错误。

### **典型使用场景：**
- **流式数据处理**：比如读取大量数据时，可以边读取边处理，减少内存消耗。
- **批量插入数据库**：如批量处理 Excel 中的行数据并存储到数据库。

### **示例代码：**
```java
@Slf4j
public class DemoDataListener extends AnalysisEventListener<DemoData> {

    private static final int BATCH_COUNT = 100; // 批量处理阈值
    private List<DemoData> cachedDataList = new ArrayList<>(BATCH_COUNT);

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        log.info("解析到一条数据: {}", JSON.toJSONString(data));
        cachedDataList.add(data);

        // 达到批量阈值，执行处理
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 处理剩余数据
        saveData();
        log.info("所有数据解析完成！");
    }

    private void saveData() {
        log.info("存储 {} 条数据到数据库", cachedDataList.size());
        // TODO: 批量入库逻辑
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析异常: {}", exception.getMessage());
    }
}
```

## **2. `ReadListener`**

`ReadListener` 是 FastExcel 提供的核心接口之一，它提供了类似 `AnalysisEventListener`的功能，但对 `AnalysisEventListener` 进行了抽象和扩展，简化了处理，并且更符合现代的编程方式。

### **主要特点：**
- **易于使用**：`ReadListener` 更为简化，通常用来进行按行数据处理。
- **事件驱动**：和 `AnalysisEventListener` 类似，`ReadListener` 也是逐行读取数据。
- **回调接口**：`ReadListener` 提供了更简洁的接口方法，和 `AnalysisEventListener` 的实现方式相似，但更为抽象。

### **方法：**
- `invoke(T data, AnalysisContext context)`：处理每一行数据。
- `doAfterAllAnalysed(AnalysisContext context)`：所有数据解析完成后触发。
- `onException(Exception exception, AnalysisContext context)`：异常处理。

### **典型使用场景：**
- **简化代码**：适用于简单的按行处理，减少对内存和处理逻辑的控制。
- **异常处理**：能够捕获和处理读取过程中的异常。

### **示例代码：**
```java
@Slf4j
public class SimpleReadListener implements ReadListener<DemoData> {

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        log.info("解析到数据: {}", JSON.toJSONString(data));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("读取异常: {}", exception.getMessage());
    }
}
```

## **区别总结**

| 特性                     | `AnalysisEventListener`                       | `ReadListener`                                |
|--------------------------|----------------------------------------------|---------------------------------------------|
| **接口设计**              | 基于事件驱动，处理每行数据并保存批量数据        | 基于回调接口，简化了处理，适合简单用法          |
| **内存控制**              | 通过 `BATCH_COUNT` 控制内存使用，适合大数据量处理 | 无专门内存控制，通常用于简单的读取操作         |
| **使用场景**              | 复杂场景，流式处理、批量入库、分页处理等         | 简单的按行数据处理和异常捕获                   |
| **方法**                  | `invoke`, `doAfterAllAnalysed`, `onException`   | `invoke`, `doAfterAllAnalysed`, `onException`  |
| **易用性**                | 需要更多的内存管理和复杂的逻辑处理            | 更加简洁易用，适合简单的读取操作               |

## **选择使用哪一个**

- **使用 `AnalysisEventListener`**：
    - 如果你需要控制内存消耗、批量处理数据或者需要处理复杂的读取逻辑（如分页读取、批量写入数据库）。
    - 适用于大数据量处理，提供更多的灵活性。

- **使用 `ReadListener`**：
    - 如果你希望简化代码，并且没有复杂的内存控制需求，只需处理每一行数据的逻辑。
    - 适合简单的 Excel 数据读取和异常捕获场景。



总的来说，`ReadListener` 是更为简化的接口，适用于较为简单的场景，而 `AnalysisEventListener` 提供了更强的控制力和扩展性，适合复杂的数据处理需求。开发者可以根据实际需求选择合适的监听器。