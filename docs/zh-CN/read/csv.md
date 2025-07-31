# 读取 CSV 文件

本章节介绍如何使用 FastExcel 来读取自定义 CSV 文件。

## 概述

FastExcel 在`1.3.0`版本引入了定制化的 CSV 读取。
可以通过不同的参数设计进行 CSV 的解析。
FastExcel可以通过对各个参数进行操作设定，也可以通过`CSVFormat`进行设定来达成读取的目标。

常用参数可以参考下表：

| 名称 | 默认值 | 描述 |
| :--- | :--- | :--- |
| `delimiter` | `,` (逗号) | 字段分隔符。推荐使用 `CsvConstant` 中预定义的常量，例如 `CsvConstant.AT` (`@`)、`CsvConstant.TAB` 等。 |
| `quote` | `"` (双引号) | 字段引用符号，推荐使用 `CsvConstant` 中预定义的常量，例如 `CsvConstant.DOUBLE_QUOTE` (`"`)。 |
| `recordSeparator` | `CRLF` | 记录（行）分隔符。根据操作系统不同而变化，例如 `CsvConstant.CRLF` (Windows) 或 `CsvConstant.LF` (Unix/Linux)。 |
| `nullString` | `null` | 用于表示 `null` 值的字符串。注意这与空字符串 `""` 不同。 |
| `escape` | `null` | 转义字符，用于转义引用符号自身。 |

---

## 参数详解与示例

下面将详细介绍每一个参数的用法，并提供代码示例。

### delimiter

`delimiter` 用于指定 CSV 文件中的字段分隔符。默认值为逗号 `,`。

#### 推荐使用
- `, (逗號)`：可使用`CsvConstant.COMMA`
- `; (分號)`
- `\t (tab)`：可使用`CsvConstant.TAB`
- `| (管道)`：可使用`CsvConstant.PIPE`
- ` (空格)`：可使用`CsvConstant.SPACE`

#### 代码示例
如果您的 CSV 文件使用 `@` 作为分隔符，可以如下设置：
```java
@Test
public void readCsvByDelimiter() {
        String csvFile = "path/to/your.csv";
        List<DemoData> dataList = FastExcel.read(csvFile, DemoData.class, new DemoDataListener())
                .csv()
                .delimiter(CsvConstant.AT) // 推荐使用 CsvConstant.AT
                .doReadSync();
}
```

### quote

`quote` 用于指定包裹字段的引用符号。默认值为双引号 `"`。当字段内容本身包含分隔符或换行符时，此设置非常有用。
> 注意不可和`recordSeparator`有重复的状况。
除此之外，此选项还可进行`QuoteMode`调整，对应设定可以参考Apache Commons CSV。

#### 推荐使用
- `CsvConstant.DOUBLE_QUOTE`

#### 代码示例
```java
String csvFile = "path/to/your.csv";
List<DemoData> dataList = FastExcel.read(csvFile, DemoData.class, new DemoDataListener())
        .csv()
        .quote(CsvConstant.DOUBLE_QUOTE, QuoteMode.MINIMAL)
        .doReadSync();
```

### recordSeparator

`recordSeparator` 用于指定文件中的换行符。不同操作系统的换行符可能不同（例如，Windows 使用 `CRLF`，而 Unix/Linux 使用 `LF`）。

#### 推荐使用
- `CsvConstant.CR`
- `CsvConstant.CRLF` (Windows)
- `CsvConstant.FF`
- `CsvConstant.LF` (Unix/Linux)

#### 代码示例
```java
String csvFile = "path/to/your.csv";
List<DemoData> dataList = FastExcel.read(csvFile, DemoData.class, new DemoDataListener())
        .csv()
        .recordSeparator(CsvConstant.LF)
        .doReadSync();
```

### nullString

`nullString` 用于定义文件中代表 `null` 值的特定字符串。例如，可以将字符串 `"N/A"` 解析为 `null` 对象。

#### 代码示例
```java
String csvFile = "path/to/your.csv";
List<DemoData> dataList = FastExcel.read(csvFile, DemoData.class, new DemoDataListener())
        .csv()
        .nullString("N/A")
        .doReadSync();
```

### escape

`escape` 用于指定转义字符，当引用符号（`quote`）本身出现在字段值中时，可以使用转义字符来处理。

#### 代码示例
```java
String csvFile = "path/to/your.csv";
List<DemoData> dataList = FastExcel.read(csvFile, DemoData.class, new DemoDataListener())
        .csv()
        .escape(CsvConstant.BACKSLASH)
        .doReadSync();
```

## CSVFormat设置详解与示例

上述章节所提及的参数，与`CSVFormat`设置皆有对应配置。
> 目前FastExcel仍然支持使用，但并非最推荐的使用方法。

### 代码示例

```java
// 上面列出的其他参数可以在这里进行设置
CSVFormat csvFormat = CSVFormat.DEFAULT.builder().setDelimiter(CsvConstant.AT).build();
String csvFile = "path/to/your.csv";
// 如果有需要储存资料可以在监听器做设定
try (ExcelReader excelReader = FastExcel.read(csvFile, DemoData.class, new DemoDataListener()).build()) {
    ReadWorkbookHolder readWorkbookHolder = excelReader.analysisContext().readWorkbookHolder();
    // 判断是否为CsvReadWorkbookHolder实例
    if (readWorkbookHolder instanceof CsvReadWorkbookHolder) {
        CsvReadWorkbookHolder csvReadWorkbookHolder = (CsvReadWorkbookHolder) readWorkbookHolder;
        csvReadWorkbookHolder.setCsvFormat(csvFormat);
    }
    // 只会有一个 (index=0) Sheet
    ReadSheet readSheet = FastExcel.readSheet(0).build();
    excelReader.read(readSheet);
}
```