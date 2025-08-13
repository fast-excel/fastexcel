---
id: 'head'
title: 'Head'
---

# Headers
This chapter introduces how to write header data in Excel.

## Complex Header Writing

### Overview
Supports setting multi-level headers by specifying main titles and subtitles through the `@ExcelProperty` annotation.

### POJO Class
```java
@Getter
@Setter
@EqualsAndHashCode
public class ComplexHeadData {
    @ExcelProperty({"Main Title", "String Title"})
    private String string;
    @ExcelProperty({"Main Title", "Date Title"})
    private Date date;
    @ExcelProperty({"Main Title", "Number Title"})
    private Double doubleData;
}
```

### Code Example
```java
@Test
public void complexHeadWrite() {
    String fileName = "complexHeadWrite" + System.currentTimeMillis() + ".xlsx";
    FastExcel.write(fileName, ComplexHeadData.class)
        .sheet()
        .doWrite(data());
}
```

### Result
![img](/img/docs/write/complexHeadWrite.png)

---

## Dynamic Header Writing

### Overview
Generate dynamic headers in real-time, suitable for scenarios where header content changes dynamically.

### Code Example
```java
@Test
public void dynamicHeadWrite() {
    String fileName = "dynamicHeadWrite" + System.currentTimeMillis() + ".xlsx";

    List<List<String>> head = Arrays.asList(
        Collections.singletonList("Dynamic String Title"),
        Collections.singletonList("Dynamic Number Title"),
        Collections.singletonList("Dynamic Date Title"));

    FastExcel.write(fileName)
        .head(head)
        .sheet()
        .doWrite(data());
}
```

### Result
![img](/img/docs/write/dynamicHeadWrite.png)
