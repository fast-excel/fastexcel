---
title: 复杂读取
description: 复杂的读取操作
---

## 复杂读取 Excel 文件指南
### 概述

本指南介绍如何使用 FastExcel 完成多场景 Excel 复杂读取。涉及的场景包括：按列名或下标读取、多 Sheet 读取、日期和数字格式转换、多行表头解析、读取额外信息（如批注、超链接、合并单元格）、处理公式和异常等。

### **按列名或列下标读取**

#### 概述
您可以通过指定列名或列下标来读取 Excel 数据。这使得与动态生成的 Excel 文件交互更加灵活。

#### 示例代码
#### 示例对象：`IndexOrNameData`
```java
@Getter
@Setter
@EqualsAndHashCode
public class IndexOrNameData {
    @ExcelProperty(index = 2)
    private Double doubleData;

    @ExcelProperty("字符串标题")
    private String string;

    @ExcelProperty("日期标题")
    private Date date;
}
```

#### 监听器
与基础读取相同，仅需要修改泛型为 `IndexOrNameData`。

#### 读取代码示例
```java
@Test
public void indexOrNameRead() {
    String fileName = "path/to/demo.xlsx";

    // 指定列名或列下标读取
    FastExcel.read(fileName, IndexOrNameData.class, new DemoDataListener()).sheet().doRead();
}
```


### **读取多个 Sheet**

#### 概述
可以读取 Excel 文件中的多个 Sheet，且同一个 Sheet 不可重复读取。

#### 示例对象与监听器
与基础读取相同。

#### 读取代码示例
```java
@Test
public void repeatedRead() {
    String fileName = "path/to/demo.xlsx";

    // 读取全部 Sheet
    FastExcel.read(fileName, DemoData.class, new DemoDataListener()).doReadAll();

    // 读取指定 Sheet
    try (ExcelReader excelReader = FastExcel.read(fileName).build()) {
        ReadSheet sheet1 = FastExcel.readSheet(0).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
        ReadSheet sheet2 = FastExcel.readSheet(1).head(DemoData.class).registerReadListener(new DemoDataListener()).build();
        excelReader.read(sheet1, sheet2);
    }
}
```



### **日期、数字、自定义格式转换**

#### 示例对象：`ConverterData`
```java
@Getter
@Setter
@EqualsAndHashCode
public class ConverterData {
    @ExcelProperty(converter = CustomStringStringConverter.class)
    private String string;

    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    private String date;

    @NumberFormat("#.##%")
    private String doubleData;
}
```

#### 自定义转换器
```java
public class CustomStringStringConverter implements Converter<String> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public String convertToJavaData(ReadConverterContext<?> context) {
        return "自定义：" + context.getReadCellData().getStringValue();
    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<String> context) {
        return new WriteCellData<>(context.getValue());
    }
}
```

#### 读取代码示例
```java
@Test
public void converterRead() {
    String fileName = "path/to/demo.xlsx";

    // 自定义格式读取
    FastExcel.read(fileName, ConverterData.class, new DemoDataListener())
        .registerConverter(new CustomStringStringConverter())
        .sheet().doRead();
}
```

### **多行表头读取**

### 概述
通过设置 `headRowNumber` 参数或根据实体类的表头注解自动解析多行表头。

#### 读取代码示例
```java
@Test
public void complexHeaderRead() {
    String fileName = "path/to/demo.xlsx";

    FastExcel.read(fileName, DemoData.class, new DemoDataListener())
        .sheet()
        // 设置多行表头的行数，默认为 1
        .headRowNumber(2)
        .doRead();
}
```



### **同步返回数据**

#### 概述
同步读取 Excel 数据，直接返回数据列表。适用于小数据量场景，不推荐大数据量使用。

#### 读取代码示例
```java
@Test
public void synchronousRead() {
    String fileName = "path/to/demo.xlsx";

    // 返回实体对象的列表
    List<DemoData> list = FastExcel.read(fileName).head(DemoData.class).sheet().doReadSync();
    for (DemoData data : list) {
        log.info("读取到数据: {}", JSON.toJSONString(data));
    }

    // 返回 Map 列表，键为列索引，值为单元格内容
    List<Map<Integer, String>> listMap = FastExcel.read(fileName).sheet().doReadSync();
    for (Map<Integer, String> data : listMap) {
        log.info("读取到数据: {}", JSON.toJSONString(data));
    }
}
```



### **读取表头数据**

#### 概述
可以通过重写监听器的 `invokeHead` 方法获取表头信息。

#### 示例监听器
```java
@Slf4j
public class DemoHeadDataListener extends AnalysisEventListener<DemoData> {
    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        log.info("解析到表头数据: {}", JSON.toJSONString(headMap));
    }

    @Override
    public void invoke(DemoData data, AnalysisContext context) {}

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {}
}
```

#### 读取代码示例
```java
@Test
public void headerRead() {
    String fileName = "path/to/demo.xlsx";
    FastExcel.read(fileName, DemoData.class, new DemoHeadDataListener()).sheet().doRead();
}
```



### **额外信息读取（批注、超链接、合并单元格）**

#### 概述
通过设置 `extraRead` 参数读取额外信息，如批注、超链接、合并单元格。

#### 示例监听器
```java
@Slf4j
public class DemoExtraListener implements ReadListener<DemoExtraData> {
    @Override
    public void invoke(DemoExtraData data, AnalysisContext context) {}

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {}

    @Override
    public void extra(CellExtra extra, AnalysisContext context) {
        log.info("读取到额外信息: {}", JSON.toJSONString(extra));
        switch (extra.getType()) {
            case COMMENT:
                log.info("批注信息: {}", extra.getText());
                break;
            case HYPERLINK:
                log.info("超链接信息: {}", extra.getText());
                break;
            case MERGE:
                log.info("合并单元格范围: {} - {}", extra.getFirstRowIndex(), extra.getLastRowIndex());
                break;
            default:
                log.warn("未知的额外信息类型");
        }
    }
}
```

#### 读取代码示例
```java
@Test
public void extraRead() {
    String fileName = "path/to/demo.xlsx";

    FastExcel.read(fileName, DemoExtraData.class, new DemoExtraListener())
        .extraRead(CellExtraTypeEnum.COMMENT) // 读取批注
        .extraRead(CellExtraTypeEnum.HYPERLINK) // 读取超链接
        .extraRead(CellExtraTypeEnum.MERGE) // 读取合并单元格
        .sheet().doRead();
}
```


### **读取公式和单元格类型**

#### 概述
使用 `CellData` 类型接收单元格数据以支持公式和多种单元格格式。

#### 示例对象
```java
@Getter
@Setter
@EqualsAndHashCode
public class CellDataReadDemoData {
    private CellData<String> string;
    private CellData<Date> date;
    private CellData<Double> doubleData;
    private CellData<String> formulaValue;
}
```

#### 读取代码示例
```java
@Test
public void cellDataRead() {
    String fileName = "path/to/demo.xlsx";

    FastExcel.read(fileName, CellDataReadDemoData.class, new DemoDataListener()).sheet().doRead();
}
```


### **异常处理**

#### 概述
通过重写监听器的 `onException` 方法处理数据转换或其他读取异常。

#### 示例监听器
```java
@Slf4j
public class DemoExceptionListener extends AnalysisEventListener<ExceptionDemoData> {
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败: {}", exception.getMessage());
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException ex = (ExcelDataConvertException) exception;
            log.error("第 {} 行, 第 {} 列解析异常, 数据: {}", ex.getRowIndex(), ex.getColumnIndex(), ex.getCellData());
        }
    }

    @Override
    public void invoke(ExceptionDemoData data, AnalysisContext context) {}

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {}
}
```

#### 读取代码示例
```java
@Test
public void exceptionRead() {
    String fileName = "path/to/demo.xlsx";
    FastExcel.read(fileName, ExceptionDemoData.class, new DemoExceptionListener()).sheet().doRead();
}
```



### **不创建对象的读取**

#### 概述
通过 `Map<Integer, String>` 直接读取数据，而无需定义实体类。

#### 示例监听器
```java
@Slf4j
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {
    private static final int BATCH_COUNT = 5;
    private List<Map<Integer, String>> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("解析到一条数据: {}", JSON.toJSONString(data));
        cachedDataList.add(data);
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            cachedDataList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
    }

    private void saveData() {
        log.info("存储 {} 条数据", cachedDataList.size());
    }
}
```

#### 读取代码示例
```java
@Test
public void noModelRead() {
    String fileName = "path/to/demo.xlsx";
    FastExcel.read(fileName, new NoModelDataListener()).sheet().doRead();
}
```
