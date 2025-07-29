---
title:  Spring Boot 集成写入 Excel
description:  Spring Boot 集成写入 Excel
---

## 概述
Spring Boot 项目中可以通过 HTTP 接口生成 Excel 文件并提供下载功能，便于在 Web 环境下使用 FastExcel。

### Spring 控制器示例

### 概述
通过 HTTP 接口生成 Excel 文件并提供下载功能。

#### 示例代码
```java
@GetMapping("download")
public void download(HttpServletResponse response) throws IOException {
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    response.setCharacterEncoding

("utf-8");
    String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

    FastExcel.write(response.getOutputStream(), DemoData.class)
        .sheet("模板")
        .doWrite(data());
}
```