---
id: 'introduce'
title: 'Introduction'
slug: /
---

# FastExcel

## What is FastExcel

FastExcel is the latest work created by the original author of EasyExcel. After I left Alibaba in 2023, and with Alibaba announcing the cessation of EasyExcel updates, I decided to continue maintaining and upgrading this project. When restarting, I chose the name FastExcel to emphasize the high performance of this framework when handling Excel files, not just its simplicity and ease of use.

FastExcel will always be free and open source , using the business-friendly Apache license., making it suitable for any commercial scenarios. This provides developers and enterprises with great freedom and flexibility. Some notable features of FastExcel include:

- Full compatibility with all functionalities and features of the original EasyExcel, allowing users to transition seamlessly.
- Migrating from EasyExcel to FastExcel only requires a simple change of package name and Maven dependency to complete the upgrade.
- Functionally, it offers more innovations and improvements than EasyExcel.

We plan to introduce more new features in the future to continuously enhance user experience and tool usability. We welcome everyone to continue following the development of FastExcel. FastExcel is committed to becoming your best choice for handling Excel files.

## Features

- **High-performance Reading and Writing**: FastExcel focuses on performance optimization, capable of efficiently handling large-scale Excel data. Compared to some traditional Excel processing libraries, it can significantly reduce memory consumption.
- **Simplicity and Ease of Use**: The library offers a simple and intuitive API, allowing developers to easily integrate it into projects, whether for simple Excel operations or complex data processing.
- **Stream Operations**: FastExcel supports stream reading, minimizing the problem of loading large amounts of data at once. This design is especially important when dealing with hundreds of thousands or even millions of rows of data.

## Example

### Read

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

### Write

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
