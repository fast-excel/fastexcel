# 运行编译

本章节介绍如何从源代码构建 FastExcel。

## 开发环境

开发 FastExcel 需要 **3.8.1及以上版本的Maven** 和 **17及以上版本的JDK (Java Development Kit)** 。目前，开发环境推荐 **3.9.0** 及以上版本的Maven和 **21** 及以上的版本Java，但在编译过程中必须使用 **Java 1.8** 兼容的语言特性，保证 FastExcel 能在 Java 1.8 及以上版本环境中运行。

> 您可以使用 [SDKMAN](https://sdkman.io/) 等工具配置多版本的 Java 工具链。

## IDEA 设置
以下指南针对使用 `IntelliJ IDEA` 开发，其他版本可能存在一些细节差异。请务必遵循所有步骤。

### 工程项目设置
设置项目 [Language Level](https://www.jetbrains.com/help/idea/project-settings-and-structure.html#language-level) 为 `8` 以确保后向兼容。

## Git使用
确保您已注册 `GitHub` 账号，并按照以下步骤完成本地开发环境配置：

**Fork 仓库**：在 FastExcel 的 [GitHub 页面](https://github.com/fast-excel/fastexcel) 点击 `Fork` 按钮，将项目复制到您的 GitHub 账户下，
```
https://github.com/<your-username>/fastexcel
```

**克隆代码库**：运行以下命令将 Fork 的项目克隆到本地：
```bash
git clone git@github.com:<your-username>/fastexcel.git
```

**设置上游仓库**：将官方仓库设置为 `upstream`，方便同步更新：
```bash
git remote add upstream git@github.com:fast-excel/fastexcel.git
git remote set-url --push upstream no-pushing
```

运行 `git remote -v` 可检查配置是否正确。

## 编译

运行以下命令编译
```bash
mvn clean install -DskipTests
```

为了加快构建速度，您可以：
- 使用`-DskipTests `跳过单元测试
- 使用 Maven 的并行构建功能，例如，`mvn clean install -DskipTests -T 1C`


## 代码格式化

FastExcel 使用 [Spotless](https://github.com/diffplug/spotless) 检查并修复代码风格和格式问题。 您可以执行如下的命令，`Spotless` 将会自动检查并修复代码风格和格式问题。

```bash
mvn spotless:apply
```
