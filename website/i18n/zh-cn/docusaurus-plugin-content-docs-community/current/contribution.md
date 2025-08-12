---
id: 'contribution'
title: '贡献指南'
---

# 贡献指南

欢迎社区的每一位用户和开发者成为贡献者。无论是报告问题、改进文档、提交代码，还是提供技术支持，您的参与都将帮助 FastExcel 变得更好。

---

## 贡献方向

对 FastExcel 贡献，贡献方向有很多:

- 修复错别字
- 修复 Bug
- 删除冗余代码
- 添加测试用例
- 增强功能
- 添加注释以提升代码可读性
- 优化代码结构
- 改进或完善文档

**原则**：
- 任何有助于项目改进的 PR 都值得鼓励！
- 贡献一个新的功能，请先在 `issue` 或 `discussion` 中提出和讨论。我们不会合并**未经讨论和确认的**的功能

---

## 贡献代码
所有改进均可通过 Pull Request (PR) 实现。在提交 Pull Request 前，请熟悉以下指南：

### 工作区准备

开发 FastExcel 需要 **3.8.1及以上版本的Maven** 和 **17及以上版本的JDK (Java Development Kit)** 。目前，开发环境推荐 **3.9.0** 及以上版本的Maven和 **21** 及以上的版本Java，但在编译过程中必须使用 **Java 1.8** 兼容的语言特性，保证 FastExcel 能在 Java 1.8 及以上版本环境中运行。

您可以使用 [SDKMAN](https://sdkman.io/) 等工具配置多版本的 Java 工具链。

### Fork 仓库

确保您已注册 GitHub 账号，并按照以下步骤完成本地开发环境配置：

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


### 分支定义

在 FastExcel 中，所有贡献应基于 `main` 开发分支。此外，还有以下分支类型：

- **release 分支**：用于版本发布（如 `1.1.0`, `1.1.1`）。
- **feature 分支**：用于开发较大的功能。
- **hotfix 分支**：用于修复重要 Bug。

提交 PR 时，请确保变更基于 `main` 分支。

### 提交规则

#### 提交信息

请确保提交消息清晰且具有描述性，务必使用**英文**，且不超过 100 个字符。

允许提交信息的类型且需遵循以下格式：

- **docs**: 更新文档，例如 `docs: update README.md`
- **feature/feat**: 新功能，例如 `feature: support for xxx`
- **bugfix/fix**: 修复 Bug，例如 `fix: fix NPE in the A class`
- **refactor**: 代码重构（不涉及功能变动），例如 `refactor: optimise data processing logic`
- **style**: 代码格式，例如 `style: update code style`
- **test**: 增加或改进测试，例如 `test: add new test cases`
- **chore**: 构建过程或辅助工具的变动，例如 `chore: improve issue template`
- **dependency**: 第三方依赖库的修改，例如 `dependency: upgrade poi version to 5.4.1`

不建议使用模糊的提交信息，如：

- ~~fixed issue~~
- ~~update code~~

如果需要帮助，请参考 [如何编写 Git 提交消息](http://chris.beams.io/posts/git-commit/)。

#### 提交内容

一次提交应包含完整且可审查的更改，确保：

- 避免提交过于庞大的改动。
- 每次提交内容独立且可通过 CI 测试。

另外，请确保提交时配置正确的 Git 用户信息：

```bash
git config --get user.name
git config --get user.email
```

### PR 说明

为了帮助审阅者快速了解 PR 的内容和目的，请使用 [PR 模板](https://github.com/fast-excel/fastexcel/blob/main/.github/pull_request_template.md)。详细的描述将极大提高代码审阅效率。

---

## 测试用例贡献

任何测试用例的贡献都值得鼓励，尤其是单元测试。建议在对应模块的 `test` 目录中创建 `XXXTest.java` 文件，推荐使用 `JUnit5` 框架。

---

## 贡献文档

文档是 FastExcel 官方网站的重要构建内容，是项目与社区之间的重要桥梁。

### 目录结构说明

```shell
.
├── quickstart     # 1. 快速开始
├── read           # 2. 读取文件
├── write          # 3. 写入
├── fill           # 4. 填充
├── community      # 5. 社区
└── help           # 6. FAQ
```

### 文档编写指南

- 使用带有 `.md` 扩展名的文件路径
``` markdown
[Example](quickstart/example.md)
```

- 使用相对于 docs/ 目录的路径
``` markdown
[Example](quickstart/example.md)
```

- 图片文件需要存储在 `docs/images` 目录，并使用相对目录的形式引用.
``` markdown
[img](../../images/fill/listFill_file.png)
```

---

## 其他参与方式

除了直接贡献代码，以下方式同样是对 FastExcel 的宝贵支持：

- 回答其他用户的问题。
- 帮助审阅他人的 PR。
- 提出改进建议。
- 撰写技术博客，宣传 FastExcel。
- 在社区中分享项目相关知识。

---

## 代码风格

FastExcel 使用 [Spotless](https://github.com/diffplug/spotless) 作为代码格式化工具。请确保在提交前运行以下命令以自动格式化代码：

```bash
mvn spotless:apply
```

---

最后，感谢您对 FastExcel 的支持！
