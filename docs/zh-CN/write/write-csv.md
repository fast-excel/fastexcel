## 写入 CSV 文件
本章节介绍如何使用 FastExcel 来写入自定义 CSV 文件。

## 概述
FastExcel 在 `1.3.0` 版本中提供了高度定制化的 CSV 写入功能。

现在，你可以透过灵活的参数设定来精确控制写入 CSV 文件的方式。FastExcel 提供了两种主要途径来实现此目标：你既可以直接操作各个独立的参数进行设定，也可以利用 CSVFormat 对象来统一配置，从而达成你的多元写入需求。

常用参数可以参考下表：

| 名称 | 默认值 | 描述 |
| :--- | :--- | :--- |
| `delimiter` | `,` (逗号) | 字段分隔符。推荐使用 `CsvConstant` 中预定义的常量，例如 `CsvConstant.COMMA` (`","`) 或 `CsvConstant.TAB` 等。 |
| `quote` | `"` (双引号) | 字段引用符号，推荐使用 `CsvConstant` 中预定义的常量，例如 `CsvConstant.DOUBLE_QUOTE` (`"`)。 |
| `recordSeparator` | `CRLF` | 记录（行）分隔符。根据操作系统不同而变化，例如 `CsvConstant.CRLF` (Windows) 或 `CsvConstant.LF` (Unix/Linux)。 |
| `nullString` | `null` | 用于表示 `null` 值的字符串。 |
| `escape` | `null` | 转义字符，确认是否进行特殊符号的转义。 |

---

## 参数详解与示例

下面将详细介绍每一个参数的用法，并提供代码示例。

### delimiter

`delimiter` 用于指定 CSV 文件中的字段分隔符。默认值为逗号 `,`。

#### 推荐使用
一般来说，常见的CSV delimiter有`, (逗号)`、`; (分号)`、`\t (tab)`、`| (管道)`及` (空格)`，可使用`CsvConstant`中对应的常量。

#### 代码示例
如果您的 CSV 文件使用 `♥` 作为分隔符，可以如下设置：
```java
@Test
public void writeCsvWithDelimiter() {
    String csvFile = "path/to/your.csv";
    FastExcel.write(csvFile, DemoData.class)
            .csv()
            .delimiter("\u2665") // 使用 Unicode 字符作为分隔符
            .doWrite(List<DemoData>);
}
```

### quote

`quote` 用于指定包裹字段的引用符号。默认值为双引号 `"`。
> 注意不可和`recordSeparator`有重复的状况。

除此之外，此选项还可进行`QuoteMode`调整，对应设定可以参考[Apache Commons CSV](https://commons.apache.org/proper/commons-csv/apidocs/org/apache/commons/csv/CSVFormat.html)。

#### 推荐使用
一般来说，常见的CSV quote有`" (双引号)`，可使用`CsvConstant`中对应的常量。

#### 代码示例
如果您希望对于整份CSV文件使用单引号作为引用，可以如下设置：
```java
public void writeCsvWithQuote() {
    String csvFile = "path/to/your.csv";
    FastExcel.write(csvFile, DemoData.class)
            .csv()
            .quote('\'', QuoteMode.ALL)
            .doWrite(List<DemoData>);
}
```

### recordSeparator

`recordSeparator` 用于指定文件中的换行符。不同操作系统的换行符可能不同（例如，Windows 使用 `CRLF`，而 Unix/Linux 使用 `LF`）。

#### 代码示例
```java
public void writeCsvWithRecordSeparator() {
    String csvFile = "path/to/your.csv";
    FastExcel.write(csvFile, DemoData.class)
            .csv()
            .recordSeparator(CsvConstant.LF)
            .doWrite(List<DemoData>);
}
```

### nullString

`nullString` 用于写入文件中将 `null` 值置换成特定字符串。

#### 代码示例
```java
public void writeCsvWithNullString() {
    String csvFile = "path/to/your.csv";
    FastExcel.write(csvFile, DemoData.class)
            .csv()
            .nullString(CsvConstant.UNICODE_EMPTY)
            .doWrite(List<DemoData>);
}
```

### escape

`escape` 用于指定转义字符。当使用了`escape`，输出的CSV有包含会保留显示。

#### 代码示例
```java
public void writeCsvWithEscape() {
    String csvFile = "path/to/your.csv";
    FastExcel.write(csvFile, DemoData.class)
            .csv()
            .escape(CsvConstant.BACKSLASH)
            .doWrite(List<DemoData>);
}
```

## CSVFormat设置详解与示例

上述章节所提及的参数，与`CSVFormat`设置皆有对应配置，可参考[Apache Commons CSV](https://commons.apache.org/proper/commons-csv/apidocs/org/apache/commons/csv/CSVFormat.html)。
> 目前FastExcel仍然支持使用，但并非最推荐的使用方法。

### 代码示例

```java
public void writeCsvWithCSVFormat() {
    String csvFile = "path/to/your.csv";
    // 上面列出的其他参数可以在这里进行设置
    CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setDelimiter(CsvConstant.AT).build();
    try (ExcelWriter excelWriter = FastExcel.write(csvFile, DemoData.class).excelType(ExcelTypeEnum.CSV).build()) {
        WriteWorkbookHolder writeWorkbookHolder = excelWriter.writeContext().writeWorkbookHolder();
        Workbook workbook = writeWorkbookHolder.getWorkbook();
        if (workbook instanceof CsvWorkbook) {
            CsvWorkbook csvWorkbook = (CsvWorkbook) workbook;
            csvWorkbook.setCsvFormat(csvFormat);
            writeWorkbookHolder.setWorkbook(csvWorkbook);
        }
        WriteSheet writeSheet = FastExcel.writerSheet(0).build();
        excelWriter.write(List<DemoData>, writeSheet);
    }
}
```
