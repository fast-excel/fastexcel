
# FastExcel 介绍

## 什么是 FastExcel

[FastExcel](https://github.com/fast-excel/fastexcel) 是由原 EasyExcel 作者创建的新项目。2023 年我已从阿里离职，近期阿里宣布停止更新 EasyExcel，我决定继续维护和升级这个项目。在重新开始时，我选择为它起名为 FastExcel，以突出这个框架在处理 Excel 文件时的高性能表现，而不仅仅是简单易用。

FastExcel 将始终坚持免费开源，并采用商业友好的 Apache 协议，使其适用于任何商业化场景。这为开发者和企业提供了极大的自由度和灵活性。其一些显著特点包括：

- 完全兼容原 EasyExcel 的所有功能和特性，这使得用户可以无缝过渡。
- 从 EasyExcel 迁移到 FastExcel 只需简单地更换包名和 Maven 依赖即可完成升级。
- 在功能上，比 EasyExcel 提供更多新的特性和改进。

我们计划在未来推出更多新特性，以不断提升用户体验和工具实用性。欢迎大家持续关注 FastExcel 的发展，FastExcel 致力于成为您处理 Excel 文件的最佳选择。

## 主要特性

- **高性能读写**：FastExcel 专注于性能优化，能够高效处理大规模的 Excel 数据。相比一些传统的 Excel 处理库，它能显著降低内存占用。
- **简单易用**：该库提供了简洁直观的 API，使得开发者可以轻松集成到项目中，无论是简单的 Excel 操作还是复杂的数据处理都能快速上手。
- **流式操作**：FastExcel 支持流式读取，将一次性加载大量数据的问题降到最低。这种设计方式在处理数十万甚至上百万行的数据时尤为重要。

## 示例

### 读取

下面是读取 Excel 文档的例子：

```java
// 实现 ReadListener 接口，设置读取数据的操作
public class DemoDataListener implements ReadListener<DemoData> {
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        System.out.println("解析到一条数据" + JSON.toJSONString(data));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("所有数据解析完成！");
    }
}

public static void main(String[] args) {
    String fileName = "demo.xlsx";
    // 读取 Excel 文件
    FastExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
}
```

### 写入

下面是一个创建 Excel 文档的简单例子：

```java
// 示例数据类
public class DemoData {
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty("日期标题")
    private Date date;
    @ExcelProperty("数字标题")
    private Double doubleData;
    @ExcelIgnore
    private String ignore;
}

// 填充要写入的数据
private static List<DemoData> data() {
    List<DemoData> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
        DemoData data = new DemoData();
        data.setString("字符串" + i);
        data.setDate(new Date());
        data.setDoubleData(0.56);
        list.add(data);
    }
    return list;
}

public static void main(String[] args) {
    String fileName = "demo.xlsx";
    // 创建一个名为“模板”的 sheet 页，并写入数据
    FastExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
}
```
