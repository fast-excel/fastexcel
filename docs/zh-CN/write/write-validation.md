---
title: Excel数据验证
description: 使用FastExcel的数据验证功能，在Excel模板中生成各种验证规则，确保数据输入的准确性和一致性。
---

## 概述

FastExcel 提供了强大的数据验证功能，通过验证注解和处理器，可以在Excel模板中生成各种数据验证规则，包括下拉选择、数值范围、日期范围、必填字段等验证，确保数据输入的准确性和一致性。

## 支持的验证类型

| 验证类型 | 注解 | 功能描述 |
|---------|------|----------|
| 下拉选择验证 | `@ExcelSelect` | 限制用户只能从预定义选项中选择 |
| 数值范围验证 | `@ExcelNumberValidation` | 控制数值输入范围和精度 |
| 日期范围验证 | `@ExcelDateValidation` | 限制日期输入格式和范围 |
| 必填字段验证 | `@ExcelRequired` | 标记和验证必填项 |
| 自动序号生成 | `@AutoIncrementId` | 写入时自动生成递增ID |
| 默认值设置 | `@ExcelDefaultValue` | 预设默认值并可锁定单元格 |

## 基本用法

### 1. 定义验证实体类

```java
@Data
public class ProjectData {
    @ExcelProperty("序号")
    @AutoIncrementId(start = 1)
    private Integer id;
    
    @ExcelProperty("项目名称")
    @ExcelRequired(message = "项目名称不能为空")
    private String projectName;
    
    @ExcelProperty("项目类型")
    @ExcelSelect({"基础设施", "房屋建筑", "市政工程", "水利工程", "其他"})
    @ExcelRequired(message = "请选择项目类型")
    private String projectType;
    
    @ExcelProperty("项目金额(万元)")
    @ExcelNumberValidation(
        min = 0.0,
        max = 999999.99,
        decimalPlaces = 2,
        unit = "万元"
    )
    @ExcelRequired(message = "项目金额不能为空")
    private BigDecimal projectAmount;
    
    @ExcelProperty("开始日期")
    @ExcelDateValidation(
        format = "yyyy-MM-dd",
        minDate = "2020-01-01",
        maxDate = "2030-12-31"
    )
    @ExcelRequired(message = "开始日期不能为空")
    private Date startDate;
    
    @ExcelProperty("负责部门")
    @ExcelDefaultValue(value = "工程部")
    private String department;
}
```

### 2. 写入带验证的Excel模板

```java
@Test
public void writeValidationTemplate() {
    String fileName = "validation_template.xlsx";
    List<ProjectData> dataList = createTestData();
    
    EasyExcel.write(fileName, ProjectData.class)
        // 注册验证处理器
        .registerWriteHandler(new ExcelSelectHandler(ProjectData.class))
        .registerWriteHandler(new ExcelNumberValidationHandler(ProjectData.class))
        .registerWriteHandler(new ExcelDateValidationHandler(ProjectData.class))
        .registerWriteHandler(new ExcelDefaultValueHandler(ProjectData.class))
        .registerWriteHandler(new AutoIncrementIdHandler())
        .sheet("项目信息模板")
        .doWrite(dataList);
}
```

### 3. 读取并验证数据

```java
@Test
public void readAndValidateData() {
    String fileName = "project_data.xlsx";
    
    // 读取Excel数据
    List<ProjectData> dataList = EasyExcel.read(fileName)
        .head(ProjectData.class)
        .sheet()
        .doReadSync();
    
    // 验证必填字段
    List<ExcelValidationUtils.ValidationResult> results = 
        ExcelValidationUtils.validateRequiredBatch(dataList);
    
    // 处理验证结果
    if (results.isEmpty()) {
        System.out.println("✅ 所有数据验证通过");
    } else {
        System.out.println("❌ 发现验证失败的数据:");
        for (ExcelValidationUtils.ValidationResult result : results) {
            System.out.println("第" + (result.getRowIndex() + 1) + "行: " + 
                             String.join(", ", result.getErrors()));
        }
    }
}
```

## 详细功能说明

### @ExcelSelect - 下拉选择验证

创建下拉选择框，限制用户只能从预定义选项中选择。

```java
@ExcelProperty("项目类型")
@ExcelSelect(
    value = {"基础设施", "房屋建筑", "市政工程", "水利工程", "其他"},
    firstRow = 2,           // 起始行
    lastRow = 1000,         // 结束行
    showErrorBox = true,    // 显示错误提示
    errorTitle = "输入错误",
    errorContent = "请从下拉列表中选择有效选项"
)
private String projectType;
```

### @ExcelNumberValidation - 数值验证

控制数值输入范围和精度，支持占位符错误消息。

```java
@ExcelProperty("项目金额(万元)")
@ExcelNumberValidation(
    min = 0.0,
    max = 999999.99,
    decimalPlaces = 2,
    unit = "万元",
    errorContent = "请输入{min}到{max}之间的金额，最多{decimal}位小数",
    promptContent = "请输入项目金额，单位：{unit}"
)
private BigDecimal projectAmount;
```

**支持的占位符：**
- `{min}` - 最小值
- `{max}` - 最大值  
- `{decimal}` - 小数位数
- `{unit}` - 单位

### @ExcelDateValidation - 日期验证

限制日期输入格式和范围，支持占位符错误消息。

```java
@ExcelProperty("开始日期")
@ExcelDateValidation(
    format = "yyyy-MM-dd",
    minDate = "2020-01-01",
    maxDate = "2030-12-31",
    errorContent = "请输入{format}格式的日期，范围：{minDate}到{maxDate}"
)
private Date startDate;
```

**支持的占位符：**
- `{format}` - 日期格式
- `{minDate}` - 最小日期
- `{maxDate}` - 最大日期

### @ExcelRequired - 必填验证

标记必填字段，在数据读取后进行验证。

```java
@ExcelProperty("项目名称")
@ExcelRequired(message = "项目名称不能为空")
private String projectName;
```

### @AutoIncrementId - 自动序号

写入时自动生成递增序号。

```java
@ExcelProperty("序号")
@AutoIncrementId(start = 1)
private Integer id;
```

### @ExcelDefaultValue - 默认值

设置字段默认值。

```java
@ExcelProperty("负责部门")
@ExcelDefaultValue(value = "工程部")
private String department;
```

## 高级用法

### 组合使用多个验证注解

```java
@ExcelProperty("项目金额(万元)")
@ExcelNumberValidation(min = 0.0, max = 999999.99, decimalPlaces = 2)
@ExcelRequired(message = "项目金额不能为空")
private BigDecimal projectAmount;
```

### 自定义验证范围

```java
@ExcelProperty("项目进度(%)")
@ExcelNumberValidation(
    min = 0.0,
    max = 100.0,
    firstRow = 3,    // 从第4行开始验证
    lastRow = 50     // 到第51行结束
)
private BigDecimal progress;
```

### 批量验证处理

```java
public void validateAndProcess(List<ProjectData> dataList) {
    // 验证必填字段
    List<ExcelValidationUtils.ValidationResult> results = 
        ExcelValidationUtils.validateRequiredBatch(dataList);
    
    if (!results.isEmpty()) {
        // 收集所有错误信息
        List<String> allErrors = new ArrayList<>();
        for (ExcelValidationUtils.ValidationResult result : results) {
            allErrors.add("第" + (result.getRowIndex() + 1) + "行: " + 
                         String.join(", ", result.getErrors()));
        }
        
        // 抛出验证异常或记录日志
        throw new ValidationException("数据验证失败:\n" + String.join("\n", allErrors));
    }
    
    // 验证通过，继续处理数据
    processValidData(dataList);
}
```

## 注意事项

1. **处理器注册**: 验证处理器必须在写入时注册才能生效
2. **必填验证时机**: 必填字段验证在读取数据后使用 `ExcelValidationUtils` 进行
3. **Excel兼容性**: 验证规则仅在Excel应用程序中生效，不影响程序读取
4. **性能考虑**: 大量数据时建议合理设置验证范围（firstRow、lastRow）

## 最佳实践

1. **合理使用验证范围**: 避免对整个工作表设置验证，影响性能
2. **提供清晰的错误提示**: 使用占位符提供具体的错误信息
3. **组合使用注解**: 将相关的验证注解组合使用，提高数据质量
4. **统一验证处理**: 建立统一的验证错误处理机制
5. **测试验证规则**: 在生产环境使用前充分测试验证规则的效果
