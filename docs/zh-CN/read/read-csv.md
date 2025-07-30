---
title: fastexcel 读取CSV文件
description: 支持读取CSV文件
---

## FastExcel读取CSV文件
### 概述

FastExcel在`1.3.0`版本引入了定制化的CSV读取。现在可以通过不同的参数设计进行CSV的解析，常用参数可以参考下表：
|设置	|方法	| 默认值 | 描述 |
| --- | --- | --- | --- |
|分隔符	|delimiter	|,	|分隔使用的符号|
|引用符	|quote	|"	|引用的区块|
|换行符	|recordSeparator	|CRLF	|根据不同系统会有不同的换行符
|无效字符串	|nullString	|null	|该区域无值（需注意与空白不同）
|转义字符	|escape	|null	|特殊字符

### 读取 CSV 文件并返回整份文件内容
#### 代码

```java
FastExcel.read(csvFile, CsvData.class, new CsvDataListener())
    .csv()
    .delimiter(CsvConstant.AT)
    .quote(CsvConstant.DOUBLE_QUOTE,QuoteMode.ALL)
    .recordSeparator(CsvConstant.LF)
    .nullString(CsvConstant.UNICODE_EMPTY)
    .escape(escapse)
    .doReadSync();
```
