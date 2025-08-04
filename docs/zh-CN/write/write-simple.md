---
title: 简单写入
description: 利用fastexcel快速写入Excel
---
## 简单写入

### 概述
使用 FastExcel 进行简单的 Excel 数据写入，可以快速地将实体对象写入 Excel 文件，是最基本、最常用的写入方式。

### 示例对象

```java
@Getter
@Setter
@EqualsAndHashCode
public class DemoData {
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;
    @ExcelIgnore
    private String ignore; // 忽略的字段
}
```

### 代码示例

```java
@Test
public void simpleWrite() {
    String fileName = "simpleWrite" + System.currentTimeMillis() + ".xlsx";

    // 写法1：使用 Lambda 表达式分页获取数据
    FastExcel.write(fileName, DemoData.class)
        .sheet("模板")
        .doWrite(() -> data());

    // 写法2：直接传递数据列表
    FastExcel.write(fileName, DemoData.class)
        .sheet("模板")
        .doWrite(data());

    // 写法3：使用 ExcelWriter 对象
    try (ExcelWriter excelWriter = FastExcel.write(fileName, DemoData.class).build()) {
        WriteSheet writeSheet = FastExcel.writerSheet("模板").build();
        excelWriter.write(data(), writeSheet);
    }
}
```