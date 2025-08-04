---
title: 安装与配置
description: 快速开始使用 FastExcel 安装与配置
---

## 安装

下表列出了各版本 FastExcel 基础库对 Java 语言版本最低要求的情况：

| 版本    |  jdk版本支持范围   | 备注            |
|-------|:------------:|---------------|
| 1.2.x | jdk8 - jdk21 | 完全兼容easyexcel |
| 1.1.x | jdk8 - jdk21 | 完全兼容easyexcel |
| 1.0.x | jdk8 - jdk21 | 完全兼容easyexcel |

我们强烈建议您使用最新版本的 FastExcel，因为最新版本中的性能优化、BUG修复和新功能都会让您的使用更加方便。

> 当前 FastExcel 底层使用 poi 作为基础包，如果您的项目中已经有 poi 相关组件，需要您手动排除 poi 的相关 jar 包。

### 版本更新

您可以在 [版本升级详情](./CHANGELOG.md) 中查询到具体的版本更新细节。您也可以在[Maven 中心仓库](https://mvnrepository.com/artifact/cn.idev.excel/fastexcel)中查询到所有的版本。

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


## EasyExcel 与 FastExcel

### 区别

- FastExcel 支持所有 EasyExcel 的功能，但是 FastExcel 的性能更好，更稳定。
- FastExcel 与 EasyExcel 的 API 完全一致，可以无缝切换。
- FastExcel 会持续的更新，修复 bug，优化性能，增加新功能。

### 如何升级到 FastExcel

#### 替换依赖

将 EasyExcel 的依赖替换为 FastExcel 的依赖，如下：

```xml
<!-- easyexcel 依赖 -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>版本号</version>
</dependency>
```

的依赖替换为

```xml

<dependency>
    <groupId>cn.idev.excel</groupId>
    <artifactId>fastexcel</artifactId>
    <version>版本号</version>
</dependency>
```

#### 替换包名

将 EasyExcel 的包名替换为 FastExcel 的包名，如下：

```java
// 将 easyexcel 的包名替换为 FastExcel 的包名

import com.alibaba.excel.*;
```

替换为

```java
import cn.idev.excel.*;
```

### 直接依赖 FastExcel

如果由于种种原因您不想修改代码，可以直接在 `pom.xml` 文件中直接依赖 FastExcel。EasyExcel 与 FastExcel 可以共存，但是长期建议替换为 FastExcel。

### 使用建议

为了兼容性考虑保留了 EasyExcel 类，但是建议以后使用 FastExcel 类，FastExcel 类是 FastExcel 的入口类，功能包含了 EasyExcel 类的所有功能，以后新特性仅在 FastExcel 类中添加。


