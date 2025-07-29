---
title: 简单读取
description: 简单读取一个excel文件
---


## 简单读取 Excel

## 示例一：定义pojo、定义监听器读取一个简单的 Excel 文件
### 概述

FastExcel 提供了一种简单的方式来读取 Excel 文件。用户只需定义一个 POJO 类来表示数据结构，然后通过 FastExcel 的监听器机制读取数据。

### 示例对象：`DemoData`
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

### 数据监听器：`DemoDataListener`
`DemoDataListener` 是一个自定义监听器，用于处理从 Excel 中读取的数据。
> **注意**：监听器不能被 Spring 管理，每次读取 Excel 文件时需要重新实例化。

```java
@Slf4j
public class DemoDataListener implements ReadListener<DemoData> {

    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }
}
```

### 读取代码示例
```java
@Test
public void simpleRead() {
    String fileName = "path/to/demo.xlsx";

    // 使用方式1：Lambda表达式直接处理数据
    FastExcel.read(fileName, DemoData.class, new PageReadListener<>(dataList -> {
        for (DemoData demoData : dataList) {
            log.info("读取到一条数据: {}", JSON.toJSONString(demoData));
        }
    })).sheet().doRead();

    // 使用方式2：匿名内部类
    FastExcel.read(fileName, DemoData.class, new ReadListener<DemoData>() {
        @Override
        public void invoke(DemoData data, AnalysisContext context) {
           log.info("读取到一条数据: {}", JSON.toJSONString(data));
        }
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) { }
    }).sheet().doRead();

    // 使用方式3：自定义监听器
    FastExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();

    // 使用方式4：多 Sheet 读取
    try (ExcelReader excelReader = FastExcel.read(fileName, DemoData.class, new DemoDataListener()).build()) {
        ReadSheet readSheet = FastExcel.readSheet(0).build();
        excelReader.read(readSheet);
    }
}
```

## 示例二：不定义POJO读取一个简单的 Excel 文件

### 概述
FastExcel 还支持不定义 POJO 类直接读取 Excel 文件，通过 `Map<Integer, String>` 直接读取数据。

```java
@Slf4j
public class NoModelDataListener extends AnalysisEventListener<Map<Integer, String>> {
    private static final int BATCH_COUNT = 5;
    private List<Map<Integer, String>> cachedDataList = new ArrayList<>(BATCH_COUNT);

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
        // 确保全部数据被处理
        saveData();
    }

    private void saveData() {
        // 实际业务处理逻辑
        log.info("存储 {} 条数据", cachedDataList.size());
    }
}
```

## 示例三：不定义监听器读取一个简单的 Excel 文件

### 概述
使用 doReadSync 方法直接将 Excel 数据读取为内存中的列表，这种方法适用于数据量较小的场景。读取的数据可以是 POJO 对象列表或 Map 列表。

### 示例代码
#### 读取为对象列表

假设有一个与 Excel 结构对应的 POJO 类 DemoData。

```java


@Getter
@Setter
@EqualsAndHashCode
public class DemoData {
    private String string;
    private Date date;
    private Double doubleData;
}
同步读取为对象列表
@Test
public void synchronousReadToObjectList() {
    String fileName = "path/to/demo.xlsx";

    // 使用 FastExcel 同步读取 Excel 数据为对象列表
    List<DemoData> list = FastExcel.read(fileName).head(DemoData.class).sheet().doReadSync();

    // 处理读取的数据列表
    for (DemoData data : list) {
        log.info("读取到的数据: {}", JSON.toJSONString(data));
    }
}
```

### 读取为 Map 列表
在不使用 POJO 情况下，可以将每一行读取为 Map，键为列索引，值为单元格内容。

```java
@Test
public void synchronousReadToMapList() {
    String fileName = "path/to/demo.xlsx";

    // 直接读取为 Map 列表
    List<Map<Integer, String>> list = FastExcel.read(fileName).sheet().doReadSync();

    // 处理读取的数据列表
    for (Map<Integer, String> data : list) {
        log.info("读取到的数据: {}", JSON.toJSONString(data));
    }
}
```