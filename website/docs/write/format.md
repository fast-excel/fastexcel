---
id: 'format'
title: 'Format'
---

# Formatting
This chapter introduces data formatting when writing data.

## Custom Format Writing

### Overview
Supports date, number, or other custom formats through annotations.

### POJO Class
```java
@Getter
@Setter
@EqualsAndHashCode
public class ConverterData {
    @ExcelProperty(value = "String Title", converter = CustomStringStringConverter.class)
    private String string;

    @DateTimeFormat("yyyyMMddHHmmss")
    @ExcelProperty("Date Title")
    private Date date;

    @NumberFormat("#.##%")
    @ExcelProperty("Number Title")
    private Double doubleData;
}
```

### Code Example
```java
@Test
public void converterWrite() {
    String fileName = "converterWrite" + System.currentTimeMillis() + ".xlsx";
    FastExcel.write(fileName, ConverterData.class)
        .sheet()
        .doWrite(data());
}
```

### Result
![img](/img/docs/write/converterWrite.png)
