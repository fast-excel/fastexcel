# FastExcel 验证功能扩展

本文档介绍 FastExcel 1.2.1 版本新增的 Excel 验证功能，包括数值验证、日期验证、下拉选择、必填验证、自动序号和默认值设置等功能。

## 功能概述

### 新增注解

| 注解 | 功能 | 适用场景 |
|------|------|----------|
| `@ExcelSelect` | 下拉选择框 | 限制用户只能从预定义选项中选择 |
| `@ExcelNumberValidation` | 数值验证 | 限制数值输入范围和小数位数 |
| `@ExcelDateValidation` | 日期验证 | 限制日期输入范围和格式 |
| `@ExcelRequired` | 必填验证 | 标记必填字段，读取时验证 |
| `@AutoIncrementId` | 自动序号 | 写入时自动生成递增序号 |
| `@ExcelDefaultValue` | 默认值 | 设置默认值并可选择锁定单元格 |

### 新增处理器

| 处理器 | 功能 | 使用方式 |
|--------|------|----------|
| `ExcelSelectHandler` | 处理下拉选择 | 写入时注册 |
| `ExcelNumberValidationHandler` | 处理数值验证 | 写入时注册 |
| `ExcelDateValidationHandler` | 处理日期验证 | 写入时注册 |
| `AutoIncrementIdHandler` | 处理自动序号 | 写入时注册 |
| `ExcelDefaultValueHandler` | 处理默认值 | 写入时注册 |

### 新增工具类

| 工具类 | 功能 | 使用方式 |
|--------|------|----------|
| `ExcelValidationUtils` | 验证工具 | 读取后验证数据 |

## 使用示例

### 1. 基本用法

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
    private String projectType;
    
    @ExcelProperty("项目金额(万元)")
    @ExcelNumberValidation(
        min = 0.0,
        max = 999999.99,
        decimalPlaces = 2,
        unit = "万元"
    )
    private BigDecimal projectAmount;
    
    @ExcelProperty("开始日期")
    @ExcelDateValidation(
        format = "yyyy-MM-dd",
        minDate = "2020-01-01",
        maxDate = "2030-12-31"
    )
    private Date startDate;
    
    @ExcelProperty("负责部门")
    @ExcelDefaultValue(value = "工程部", locked = false)
    private String department;
}
```

### 2. 写入带验证的Excel

```java
// 写入Excel模板，包含各种验证规则
EasyExcel.write("template.xlsx", ProjectData.class)
    .registerWriteHandler(new ExcelSelectHandler(ProjectData.class))
    .registerWriteHandler(new ExcelNumberValidationHandler(ProjectData.class))
    .registerWriteHandler(new ExcelDateValidationHandler(ProjectData.class))
    .registerWriteHandler(new ExcelDefaultValueHandler(ProjectData.class))
    .registerWriteHandler(new AutoIncrementIdHandler())
    .sheet("项目信息")
    .doWrite(dataList);
```

### 3. 读取并验证数据

```java
// 读取Excel数据
List<ProjectData> dataList = EasyExcel.read("data.xlsx")
    .head(ProjectData.class)
    .sheet()
    .doReadSync();

// 验证必填字段
List<ExcelValidationUtils.ValidationResult> results = 
    ExcelValidationUtils.validateRequiredBatch(dataList);

// 处理验证结果
for (ExcelValidationUtils.ValidationResult result : results) {
    System.out.println("验证失败: " + result.toString());
}
```

## 注解详细说明

### @ExcelSelect - 下拉选择

```java
@ExcelSelect({
    value = {"选项1", "选项2", "选项3"},  // 下拉选项
    firstRow = 2,                        // 起始行（默认2）
    lastRow = 1000,                      // 结束行（默认1000）
    showErrorBox = true,                 // 显示错误提示（默认true）
    errorTitle = "输入错误",              // 错误标题
    errorContent = "请从下拉列表中选择"    // 错误内容
})
```

### @ExcelNumberValidation - 数值验证

```java
@ExcelNumberValidation(
    min = 0.0,                          // 最小值
    max = 999999.99,                    // 最大值
    decimalPlaces = 2,                  // 小数位数
    unit = "元",                        // 单位
    allowEmpty = true,                  // 允许空值
    errorTitle = "数值输入错误",         // 错误标题
    errorContent = "请输入{min}到{max}之间的数值", // 支持占位符
    promptTitle = "数值输入",            // 提示标题
    promptContent = "请输入有效数值"      // 提示内容
)
```

### @ExcelDateValidation - 日期验证

```java
@ExcelDateValidation(
    format = "yyyy-MM-dd",              // 日期格式
    minDate = "2020-01-01",             // 最小日期
    maxDate = "2030-12-31",             // 最大日期
    errorTitle = "日期输入错误",         // 错误标题
    errorContent = "请输入{format}格式的日期", // 支持占位符
    promptTitle = "日期输入",            // 提示标题
    promptContent = "请输入有效日期"      // 提示内容
)
```

### @ExcelRequired - 必填验证

```java
@ExcelRequired(
    message = "此字段为必填项，不能为空",  // 错误消息
    showErrorBox = true,                // 显示错误提示
    errorTitle = "必填字段验证"          // 错误标题
)
```

### @AutoIncrementId - 自动序号

```java
@AutoIncrementId(
    start = 1                           // 起始值（默认1）
)
```

### @ExcelDefaultValue - 默认值

```java
@ExcelDefaultValue(
    value = "默认值",                   // 默认值
    locked = false,                     // 是否锁定（默认false）
    startRow = 2,                       // 起始行（默认2）
    endRow = 100,                       // 结束行（默认50）
    message = "此字段已设置默认值"       // 提示信息
)
```

## 占位符支持

部分注解支持占位符，会在运行时自动替换：

### 数值验证占位符
- `{min}` - 最小值
- `{max}` - 最大值
- `{decimal}` - 小数位数
- `{unit}` - 单位

### 日期验证占位符
- `{format}` - 日期格式
- `{minDate}` - 最小日期
- `{maxDate}` - 最大日期

## 注意事项

1. **POI版本兼容性**: 确保使用兼容的POI版本
2. **性能考虑**: 大量数据时建议合理设置验证范围
3. **线程安全**: AutoIncrementIdHandler在多线程环境下是安全的
4. **默认值动态支持**: 目前默认值主要支持固定值，动态值功能待扩展
5. **工作表保护**: 使用锁定功能时会自动启用工作表保护

## 版本信息

- **引入版本**: FastExcel 1.2.1
- **兼容性**: 完全兼容现有FastExcel功能
- **依赖**: 基于Apache POI实现

## 贡献

这些功能是基于实际项目需求开发的，如有问题或建议，欢迎提交Issue或Pull Request。
