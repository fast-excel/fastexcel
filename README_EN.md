[中文](README.md) |[English](README_EN.md) | [日本語](README_JP.md)

## What is FastExcel

FastExcel is the latest work created by the original author of EasyExcel. After I left Alibaba in 2023, and with Alibaba announcing the cessation of EasyExcel updates, I decided to continue maintaining and upgrading this project. When restarting, I chose the name FastExcel to emphasize the high performance of this framework when handling Excel files, not just its simplicity and ease of use.

FastExcel will always remain free and open-source, adopting the most open MIT license, making it suitable for any commercial scenarios. This provides developers and enterprises with great freedom and flexibility. Some notable features of FastExcel include:

- 1. Full compatibility with all functionalities and features of the original EasyExcel, allowing users to transition seamlessly.
- 2. Migrating from EasyExcel to FastExcel only requires a simple change of package name and Maven dependency to complete the upgrade.
- 3. Functionally, it offers more innovations and improvements than EasyExcel.
- 4. The FastExcel 1.0.0 version introduced the ability to read a specified number of Excel rows and convert Excel to PDF.

We plan to introduce more new features in the future to continually enhance user experience and tool usability. Stay tuned to "Programmer Xiao Lan's" public account for updates on the development of FastExcel. FastExcel is committed to being your best choice for handling Excel files.

## Key Features

- 1. High-performance Reading and Writing: FastExcel focuses on performance optimization, capable of efficiently handling large-scale Excel data. Compared to some traditional Excel processing libraries, it can significantly reduce memory consumption.
- 2. Simplicity and Ease of Use: The library offers a simple and intuitive API, allowing developers to easily integrate it into projects, whether for simple Excel operations or complex data processing.
- 3. Stream Operations: FastExcel supports stream reading, minimizing the problem of loading large amounts of data at once. This design is especially important when dealing with hundreds of thousands or even millions of rows of data.

## Installation

The following table lists the minimum Java language version requirements for each version of the FastExcel library:

| Version | JDK Version Support Range | Notes                          |
|---------|:-------------------------:|--------------------------------|
| 1.0.0+  | JDK8 - JDK21              | Current master branch, fully compatible with EasyExcel |

We strongly recommend using the latest version of FastExcel, as performance optimizations, bug fixes, and new features in the latest version will enhance your experience.

> Currently, FastExcel uses POI as its underlying package. If your project already includes POI-related components, you will need to manually exclude POI-related jar files.

### Maven
If you are using Maven for project building, add the following configuration in the `pom.xml` file:
```xml
<dependency>
    <groupId>cn.idev.excel</groupId>
    <artifactId>fastexcel</artifactId>
    <version>1.1.0</version>
</dependency>
```
### Gradle
If you are using Gradle for project building, add the following configuration in the build.gradle file:

```gradle
dependencies {
    implementation 'cn.idev.excel:fastexcel:1.1.0'
}
```
## Update
For detailed update logs, refer to [Details of version updates](update.md). You can also find all available versions in the [Maven Central Repository](https://mvnrepository.com/artifact/cn.idev.excel/fastexcel).

## Differences Between EasyExcel and FastExcel
- FastExcel supports all the features of EasyExcel but with better performance and stability.
- FastExcel has an identical API to EasyExcel, allowing seamless switching.
- FastExcel will continue to update, fix bugs, optimize performance, and add new features.
## How to Upgrade from EasyExcel to FastExcel

### 1. Modify Dependencies
Replace the EasyExcel dependency with the FastExcel dependency, as follows:

```xml
<!-- EasyExcel dependency -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>xxxx</version>
</dependency>
```
Replace with:
```xml
<dependency>
    <groupId>cn.idev.excel</groupId>
    <artifactId>fastexcel</artifactId>
    <version>1.1.0</version>
</dependency>
```
### 2. Modify Code
Replace the EasyExcel package name with the FastExcel package name, as follows:
```java
// Replace EasyExcel package name with FastExcel package name
import com.alibaba.excel.**;
```

Replace with:

```java
import cn.idev.excel.** ;
```

### 3. Depend on FastExcel Without Modifying Code
If you do not want to modify the code for various reasons, you can directly depend on FastExcel by directly adding the dependency in the pom.xml file. EasyExcel and FastExcel can coexist, but long-term switching to FastExcel is recommended.

### 4. Future Use of FastExcel Classes Recommended
To maintain compatibility, EasyExcel classes are retained, but using FastExcel classes in the future is recommended. FastExcel classes are the entry classes for FastExcel and encompass all features of EasyExcel. New features will only be added to FastExcel classes.

## Simple Example: Reading Excel Files
Below is an example of reading an Excel document:
```java
// Implement the ReadListener interface to set up operations for reading data
public class DemoDataListener implements ReadListener<DemoData> {
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        System.out.println("Parsed a data entry" + JSON.toJSONString(data));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("All data parsed!");
    }
}

public static void main(String[] args) {
    String fileName = "demo.xlsx";
    // Read Excel file
    FastExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
}
```

### Simple Example: Creating Excel Files
Below is a simple example of creating an Excel document:
```java
// Sample data class
public class DemoData {
    @ExcelProperty("String Title")
    private String string;
    @ExcelProperty("Date Title")
    private Date date;
    @ExcelProperty("Number Title")
    private Double doubleData;
    @ExcelIgnore
    private String ignore;
}

// Prepare data to write
private static List<DemoData> data() {
    List<DemoData> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
        DemoData data = new DemoData();
        data.setString("String" + i);
        data.setDate(new Date());
        data.setDoubleData(0.56);
        list.add(data);
    }
    return list;
}

public static void main(String[] args) {
    String fileName = "demo.xlsx";
    // Create a "Template" sheet and write data
    FastExcel.write(fileName, DemoData.class).sheet("Template").doWrite(data());
}
```

