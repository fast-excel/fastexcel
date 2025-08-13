---
id: 'guide'
title: 'Guide'
---

# Installation

## Compatibility Information

The following table lists the minimum Java language version requirements for each version of the FastExcel library:

| Version | JDK Version Support Range | Notes                                                  |
|---------|---------------------------|--------------------------------------------------------|
| 1.2.x   | JDK8 - JDK21              | Current master branch, fully compatible with EasyExcel |
| 1.1.x   | JDK8 - JDK21              | Current master branch, fully compatible with EasyExcel |
| 1.0.x   | JDK8 - JDK21              | Current master branch, fully compatible with EasyExcel |

We strongly recommend using the latest version of FastExcel, as performance optimizations, bug fixes, and new features in the latest version will enhance your experience.

> Currently, FastExcel uses POI as its underlying package. If your project already includes POI-related components, you will need to manually exclude POI-related jar files.

## Version Update

For detailed update logs, refer to [Details of version updates](https://github.com/fast-excel/fastexcel/blob/main/CHANGELOG.md). You can also find all available versions in the [Maven Central Repository](https://mvnrepository.com/artifact/cn.idev.excel/fastexcel).

## Maven

If you are using Maven for project building, add the following configuration in the `pom.xml` file:

```xml
<dependency>
    <groupId>cn.idev.excel</groupId>
    <artifactId>fastexcel</artifactId>
    <version>version</version>
</dependency>
```

## Gradle

If you are using Gradle for project building, add the following configuration in the `build.gradle` file:

```gradle
dependencies {
    implementation 'cn.idev.excel:fastexcel:version'
}
```

## EasyExcel and FastExcel

### Differences

- FastExcel supports all the features of EasyExcel but with better performance and stability.
- FastExcel has an identical API to EasyExcel, allowing seamless switching.
- FastExcel will continue to update, fix bugs, optimize performance, and add new features.

### How to Upgrade from EasyExcel to FastExcel

#### Update Dependencies

Replace the EasyExcel dependency with the FastExcel dependency, as follows:

```xml
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>version</version>
</dependency>
```

Replace with:

```xml
<dependency>
    <groupId>cn.idev.excel</groupId>
    <artifactId>fastexcel</artifactId>
    <version>version</version>
</dependency>
```

#### Update Code

Replace the EasyExcel package name with the FastExcel package name, as follows:

```java
// Replace EasyExcel package name with FastExcel package name
import com.alibaba.excel.*;
```

Replace with:

```java
import cn.idev.excel.*;
```

### Import FastExcel Without Modifying Code

If you do not want to modify the code for various reasons, you can directly depend on FastExcel by directly adding the dependency in the pom.xml file. EasyExcel and FastExcel can coexist, but long-term switching to FastExcel is recommended.

### Future Use of FastExcel Classes Recommended

To maintain compatibility, EasyExcel classes are retained, but using FastExcel classes in the future is recommended. FastExcel classes are the entry classes for FastExcel and encompass all features of EasyExcel. New features will only be added to FastExcel classes.
