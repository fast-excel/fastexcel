---
id: 'extra'
title: 'Extra'
---

# Extra Information
This chapter introduces how to write extra information such as comments, hyperlinks, formulas, merged cells, etc.

## Comments

### Overview
Add comments to specific cells through interceptors, suitable for annotations or special reminders.

### Code Example
Custom interceptor
```java
@Slf4j
public class CommentWriteHandler implements RowWriteHandler {

    @Override
    public void afterRowDispose(RowWriteHandlerContext context) {
        if (BooleanUtils.isTrue(context.getHead())) {
            Sheet sheet = context.getWriteSheetHolder().getSheet();
            Drawing<?> drawingPatriarch = sheet.createDrawingPatriarch();
            // Create comment in first row, second column
            Comment comment = drawingPatriarch.createCellComment(
                new XSSFClientAnchor(0, 0, 0, 0, (short) 1, 0, (short) 2, 1));
            comment.setString(new XSSFRichTextString("Comment content"));
            sheet.getRow(0).getCell(1).setCellComment(comment);
        }
    }
}
```

Usage
```java
@Test
public void commentWrite() {
    String fileName = "commentWrite" + System.currentTimeMillis() + ".xlsx";

    FastExcel.write(fileName, DemoData.class)
        .inMemory(Boolean.TRUE) // Comments must enable in-memory mode
        .registerWriteHandler(new CommentWriteHandler())
        .sheet("Comment Example")
        .doWrite(data());
}
```

### Result
![img](/img/docs/write/commentWrite.png)

---

## Hyperlinks
Write extra hyperlink information

### POJO Class
```java
@Getter
@Setter
@EqualsAndHashCode
public class WriteCellDemoData {
    private WriteCellData<String> hyperlink;
}
```

### Code Example
```java
@Test
public void writeHyperlinkDataWrite() {
    String fileName = "writeCellDataWrite" + System.currentTimeMillis() + ".xlsx";
    WriteCellDemoData data = new WriteCellDemoData();
    // Set hyperlink
    data.setHyperlink(new WriteCellData<>("Click to visit").hyperlink("https://example.com"));

    FastExcel.write(fileName, WriteCellDemoData.class)
        .sheet()
        .doWrite(Collections.singletonList(data));
}
```

### Result
![img](/img/docs/write/writeCellDataWrite.png)

---

## Formulas
Write extra formula information

### POJO Class
```java
@Getter
@Setter
@EqualsAndHashCode
public class WriteCellDemoData {
    private WriteCellData<String> formulaData;
}
```

### Code Example
```java
@Test
public void writeFormulaDataWrite() {
    String fileName = "writeCellDataWrite" + System.currentTimeMillis() + ".xlsx";
    WriteCellDemoData data = new WriteCellDemoData();
    // Set formula
    data.setFormulaData(new WriteCellData<>("=SUM(A1:A10)"));

    FastExcel.write(fileName, WriteCellDemoData.class)
        .sheet()
        .doWrite(Collections.singletonList(data));
}
```

### Result
![img](/img/docs/write/writeCellDataWrite.png)

---

## Template-based Writing

### Overview
Supports using existing template files and filling data into templates, suitable for standardized output.

### Code Example
```java
@Test
public void templateWrite() {
    String templateFileName = "path/to/template.xlsx";
    String fileName = "templateWrite" + System.currentTimeMillis() + ".xlsx";

    FastExcel.write(fileName, DemoData.class)
        .withTemplate(templateFileName)
        .sheet()
        .doWrite(data());
}
```

---

## Merged Cells

### Overview
Supports merged cells through annotations or custom merge strategies.

### Code Example

Annotation approach
```java
@Getter
@Setter
@EqualsAndHashCode
public class DemoMergeData {
    @ContentLoopMerge(eachRow = 2) // Merge every 2 rows
    @ExcelProperty("String Title")
    private String string;

    @ExcelProperty("Date Title")
    private Date date;

    @ExcelProperty("Number Title")
    private Double doubleData;
}
```

Custom merge strategy
```java
public class CustomMergeStrategy extends AbstractMergeStrategy {
    @Override
    protected void merge(Sheet sheet, WriteSheetHolder writeSheetHolder) {
        // Custom merge rules
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 1)); // Example merge range
    }
}
```

Usage
```java
@Test
public void mergeWrite() {
    String fileName = "mergeWrite" + System.currentTimeMillis() + ".xlsx";

    // Annotation approach
    FastExcel.write(fileName, DemoMergeData.class)
        .sheet("Merge Example")
        .doWrite(data());

    // Custom merge strategy
    FastExcel.write(fileName, DemoData.class)
        .registerWriteHandler(new CustomMergeStrategy())
        .sheet("Custom Merge")
        .doWrite(data());
}
```

### Result
![img](/img/docs/write/mergeWrite.png)

---

## Custom Interceptors

### Overview
Implement custom logic (such as adding dropdowns) through interceptor operations.

### Code Example

Setting dropdowns
```java
public class DropdownWriteHandler implements SheetWriteHandler {
    @Override
    public void afterSheetCreate(SheetWriteHandlerContext context) {
        DataValidationHelper helper = context.getWriteSheetHolder().getSheet().getDataValidationHelper();
        CellRangeAddressList range = new CellRangeAddressList(1, 10, 0, 0); // Dropdown area
        DataValidationConstraint constraint = helper.createExplicitListConstraint(new String[] {"Option 1", "Option 2"});
        DataValidation validation = helper.createValidation(constraint, range);
        context.getWriteSheetHolder().getSheet().addValidationData(validation);
    }
}
```

### Result
![img](/img/docs/write/customHandlerWrite.png)
