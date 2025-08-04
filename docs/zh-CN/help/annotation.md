# 注解
本章节介绍读取 FastExcel 中的注解。

## 实体类注解

实体类是读写操作的基础。FastExcel 提供了多种注解，帮助开发者轻松定义字段和格式。
### **`@ExcelProperty`**
定义 Excel 列名和映射的字段名。 具体参数如下：

| 名称                  | 默认值               | 描述                                                                                                                                                  |
|---------------------|-------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------|
| value           | 空                 | 用于匹配excel中的头，必须全匹配,如果有多行头，会匹配最后一行头                                                                                                                  |
| order           | Integer.MAX_VALUE | 优先级高于`value`，会根据`order`的顺序来匹配实体和excel中数据的顺序                                                                                                         |
| index           | &#45;1            | 优先级高于`value`和`order`，会根据`index`直接指定到excel中具体的哪一列                                                                                                    |
| converter           | 自动选择              | 指定当前字段用什么转换器，默认会自动选择。读的情况下只要实现`cn.idev.excel.converters.Converter#convertToJavaData(com.idev.excel.converters.ReadConverterContext<?>)` 方法即可 |

### **`@ColumnWidth`**
指定列宽。

### **`@DateTimeFormat`**
日期转换，用`String`去接收excel日期格式的数据会调用这个注解,参数如下：

| 名称                  | 默认值  | 描述                                                             |
|---------------------|------|----------------------------------------------------------------|
| value           | 空    | 参照`java.text.SimpleDateFormat`书写即可                             |
| use1904windowing           | 自动选择 | excel中时间是存储1900年起的一个双精度浮点数，但是有时候默认开始日期是1904，所以设置这个值改成默认1904年开始 |

### **`@NumberFormat`**

数字转换，用`String`去接收excel数字格式的数据会调用这个注解。

| 名称                  | 默认值  | 描述                          |
|---------------------|------|-----------------------------|
| value           | 空    | 参照`java.text.DecimalFormat`书写即可 |
| roundingMode           | RoundingMode.HALF_UP | 格式化的时候设置舍入模式                    |

