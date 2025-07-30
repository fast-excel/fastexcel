# 读取 CSV 文件

本章节介绍如何使用 FastExcel 来读取自定义 CSV 文件。

## 概述

FastExcel 在`1.3.0`版本引入了定制化的 CSV 读取。
可以通过不同的参数设计进行 CSV 的解析，常用参数可以参考下表：

| 设置       | 方法            | 默认值 | 描述                           |
| ---------- | --------------- | ------ | ------------------------------ |
| 分隔符     | delimiter       | ,      | 分隔使用的符号                 |
| 引用符     | quote           | "      | 引用的区块                     |
| 换行符     | recordSeparator | CRLF   | 根据不同系统会有不同的换行符   |
| 无效字符串 | nullString      | null   | 该区域无值（需注意与空白不同） |
| 转义字符   | escape          | null   | 特殊字符                       |

### 代码示例

以下代码示范如何读取 CSV 文件并返回整份文件内容。

```java
@Test
public void csvRead() {
    String csvFile = "path/to/demo.csv";

    FastExcel.read(csvFile, CsvData.class, new CsvDataListener())
            .csv()
            .delimiter(CsvConstant.AT)
            .quote(CsvConstant.DOUBLE_QUOTE,QuoteMode.ALL)
            .recordSeparator(CsvConstant.LF)
            .nullString(CsvConstant.UNICODE_EMPTY)
            .escape(escapse)
            .doReadSync();
}

```
