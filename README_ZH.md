<p align="center">
    <img src="logo.svg"/>
</p>

<p align="center">
    <a href="README.md">English</a> | <a href="README_ZH.md">中文</a> | <a href="README_JP.md">日本語</a>
</p>

<p align="center">
    <a href="https://github.com/fast-excel/fastexcel/actions/workflows/ci.yml"><img alt="GitHub Actions Workflow Status" src="https://img.shields.io/github/actions/workflow/status/fast-excel/fastexcel/ci.yml?style=flat-square&logo=github"></a>
    <a href="https://github.com/fast-excel/fastexcel/actions/workflows/nightly.yml"><img alt="GitHub Actions Workflow Status" src="https://img.shields.io/github/actions/workflow/status/fast-excel/fastexcel/nightly.yml?style=flat-square&logo=github&label=nightly"></a>
    <a href="https://github.com/fast-excel/fastexcel/blob/main/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/fast-excel/fastexcel?logo=apache&style=flat-square"></a>
    <a href="https://mvnrepository.com/artifact/cn.idev.excel/fastexcel"><img alt="Maven Central Version" src="https://img.shields.io/maven-central/v/cn.idev.excel/fastexcel?logo=apachemaven&style=flat-square"></a>
</p>

<p align="center">
    <a href="https://fast-excel.github.io/fastexcel/"><img alt="Document" src="https://img.shields.io/github/actions/workflow/status/fast-excel/fastexcel/ci.yml?style=flat-square&logo=read-the-docs&label=Document"></a>
    <a href="https://deepwiki.com/fast-excel/fastexcel"><img src="https://img.shields.io/badge/DeepWiki-fast--excel%2Ffastexcel-blue.svg?logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACwAAAAyCAYAAAAnWDnqAAAAAXNSR0IArs4c6QAAA05JREFUaEPtmUtyEzEQhtWTQyQLHNak2AB7ZnyXZMEjXMGeK/AIi+QuHrMnbChYY7MIh8g01fJoopFb0uhhEqqcbWTp06/uv1saEDv4O3n3dV60RfP947Mm9/SQc0ICFQgzfc4CYZoTPAswgSJCCUJUnAAoRHOAUOcATwbmVLWdGoH//PB8mnKqScAhsD0kYP3j/Yt5LPQe2KvcXmGvRHcDnpxfL2zOYJ1mFwrryWTz0advv1Ut4CJgf5uhDuDj5eUcAUoahrdY/56ebRWeraTjMt/00Sh3UDtjgHtQNHwcRGOC98BJEAEymycmYcWwOprTgcB6VZ5JK5TAJ+fXGLBm3FDAmn6oPPjR4rKCAoJCal2eAiQp2x0vxTPB3ALO2CRkwmDy5WohzBDwSEFKRwPbknEggCPB/imwrycgxX2NzoMCHhPkDwqYMr9tRcP5qNrMZHkVnOjRMWwLCcr8ohBVb1OMjxLwGCvjTikrsBOiA6fNyCrm8V1rP93iVPpwaE+gO0SsWmPiXB+jikdf6SizrT5qKasx5j8ABbHpFTx+vFXp9EnYQmLx02h1QTTrl6eDqxLnGjporxl3NL3agEvXdT0WmEost648sQOYAeJS9Q7bfUVoMGnjo4AZdUMQku50McDcMWcBPvr0SzbTAFDfvJqwLzgxwATnCgnp4wDl6Aa+Ax283gghmj+vj7feE2KBBRMW3FzOpLOADl0Isb5587h/U4gGvkt5v60Z1VLG8BhYjbzRwyQZemwAd6cCR5/XFWLYZRIMpX39AR0tjaGGiGzLVyhse5C9RKC6ai42ppWPKiBagOvaYk8lO7DajerabOZP46Lby5wKjw1HCRx7p9sVMOWGzb/vA1hwiWc6jm3MvQDTogQkiqIhJV0nBQBTU+3okKCFDy9WwferkHjtxib7t3xIUQtHxnIwtx4mpg26/HfwVNVDb4oI9RHmx5WGelRVlrtiw43zboCLaxv46AZeB3IlTkwouebTr1y2NjSpHz68WNFjHvupy3q8TFn3Hos2IAk4Ju5dCo8B3wP7VPr/FGaKiG+T+v+TQqIrOqMTL1VdWV1DdmcbO8KXBz6esmYWYKPwDL5b5FA1a0hwapHiom0r/cKaoqr+27/XcrS5UwSMbQAAAABJRU5ErkJggg==" alt="DeepWiki"></a>
    <a href="https://readmex.com/fast-excel/fastexcel"><img src="https://raw.githubusercontent.com/CodePhiliaX/resource-trusteeship/main/readmex.svg" alt="ReadmeX"></a>
</p>

## 什么是 FastExcel

快速、简洁、解决大文件内存溢出的 Java 处理 Excel 工具。

FastExcel 将始终坚持免费开源，并采用商业友好的 Apache 协议，使其适用于任何商业化场景。这为开发者和企业提供了极大的自由度和灵活性。

我们计划在未来推出更多新特性，以不断提升用户体验和工具实用性。欢迎大家持续关注 FastExcel 的发展，FastExcel 致力于成为您处理
Excel 文件的最佳选择。

## 主要特性

- **高性能读写**：FastExcel 专注于性能优化，能够高效处理大规模的 Excel 数据。相比一些传统的 Excel 处理库，它能显著降低内存占用。
- **简单易用**：该库提供了简洁直观的 API，使得开发者可以轻松集成到项目中，无论是简单的 Excel 操作还是复杂的数据处理都能快速上手。
- **流式操作**：FastExcel 支持流式读取，将一次性加载大量数据的问题降到最低。这种设计方式在处理数十万甚至上百万行的数据时尤为重要。

## 安装

下表列出了各版本 FastExcel 基础库对 Java 语言版本最低要求的情况：

| 版本    |  jdk版本支持范围   | 备注 |
|-------|:------------:|----|
| 1.2.x | jdk8 - jdk21 |    |
| 1.1.x | jdk8 - jdk21 |    |
| 1.0.x | jdk8 - jdk21 |    |

我们强烈建议您使用最新版本的 FastExcel，因为最新版本中的性能优化、BUG修复和新功能都会让您的使用更加方便。

> 当前 FastExcel 底层使用 poi 作为基础包，如果您的项目中已经有 poi 相关组件，需要您手动排除 poi 的相关 jar 包。

### 版本更新

您可以在 [版本升级详情](./CHANGELOG.md)
中查询到具体的版本更新细节。您也可以在[Maven 中心仓库](https://mvnrepository.com/artifact/cn.idev.excel/fastexcel)
中查询到所有的版本。

### Maven

如果您使用 Maven 进行项目构建，请在 `pom.xml` 文件中引入以下配置：

```xml

<dependency>
    <groupId>cn.idev.excel</groupId>
    <artifactId>fastexcel</artifactId>
    <version>版本号</version>
</dependency>
```

### Gradle

如果您使用 Gradle 进行项目构建，请在 `build.gradle` 文件中引入以下配置：

```gradle
dependencies {
    implementation 'cn.idev.excel:fastexcel:版本号'
}
```

## 示例

### 读取 Excel 文件

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

### 创建 Excel 文件

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

## 参与贡献

欢迎社区的每一位用户和开发者成为贡献者。无论是报告问题、改进文档、提交代码，还是提供技术支持，您的参与都将帮助 FastExcel
变得更好。请查阅[贡献指南](./CONTRIBUTING.md)来参与贡献。

感谢所有的贡献者们!

<a href="https://github.com/fast-excel/fastexcel/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=fast-excel/fastexcel" />
</a>

> 备注: 由于图片大小的限制，我们默认暂只显示前100名贡献者

## 关注我们

### 公众号

关注作者“程序员小懒“的公众号加入技术交流群，获取更多技术干货和最新动态。

<a><img src="https://github.com/user-attachments/assets/b40aebe8-0552-4fb2-b184-4cb64a5b1229" width="30%"/></a>

### Star History

[![Star History Chart](https://api.star-history.com/svg?repos=fast-excel/fastexcel&type=Date)](https://www.star-history.com/#fast-excel/fastexcel&Date)


