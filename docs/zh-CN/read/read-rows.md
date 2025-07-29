---
title: fastexcel 读取excel前多少行
description: ffastexcel 读取excel前多少行
---

## FastExcel读取excel前多少行
### 概述

在数据分析和处理过程中，快速查看 Excel 文件的前几行内容可以帮助我们更好地理解数据结构和内容。本文将介绍如何使用 FastExcel 读取 Excel 文件的前 100 行。
使用numRows方法可以指定读取的行数，通过设置numRows(100)可以读取前100行数据。默认情况下，FastExcel 会读取整个 Excel 文件的所有数据，通过设置numRows方法可以限制读取的行数。
numRows从0开始，0表示不限制行数，即读取所有行。行数包括表头行，例如设置numRows(100)表示读取前100行数据，包括表头行。

### 1. 所有的Sheet读取前100行
#### 代码

```java
 EasyExcel.read(fileName, DemoData.class, new PageReadListener<DemoData>(dataList -> {
            for (DemoData demoData : dataList) {
                log.info("读取到一条数据{}", JSON.toJSONString(demoData));
            }
        })).numRows(100).sheet().doRead();

```
### 单个sheet读取前100行

```java
 try (ExcelReader excelReader = EasyExcel.read(fileName, DemoData.class, new DemoDataListener()).build()) {
            // 构建一个sheet 这里可以指定名字或者no
            ReadSheet readSheet = EasyExcel.readSheet(0).build();
            readSheet.setNumRows(100);
            // 读取一个sheet
            excelReader.read(readSheet);
        }
```

