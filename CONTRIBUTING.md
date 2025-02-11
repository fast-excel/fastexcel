# 为 FastExcel 做贡献

FastExcel 欢迎社区的每一位用户和开发者成为贡献者。无论是报告问题、改进文档、提交代码，还是提供技术支持，您的参与都将帮助 FastExcel 变得更好。

---

## 报告问题

我们鼓励用户在使用 FastExcel 的过程中随时提供反馈。您可以通过 [NEW ISSUE](https://github.com/CodePhiliaX/fastexcel/issues/new/choose) 提交问题。

### 高质量问题报告

为了提高沟通效率，请在提交问题前：
1. **搜索现有问题**：检查您的问题是否已被报告。如果存在，请直接在现有问题下评论补充详细信息，而不是创建新问题。
2. **使用问题模板**：问题模板位于 [ISSUE TEMPLATE](./.github/ISSUE_TEMPLATE)，请按照模板要求填写，以确保问题描述准确且完整。

以下情况适合提交新问题：
- Bug 报告
- 新功能需求
- 性能问题
- 功能提案或设计
- 文档改进
- 测试覆盖率优化
- 需要技术支持
- 其他与项目相关的问题

> **注意**：请勿在问题中包含敏感信息，如密码、密钥、服务器地址或私人数据。

---

## 贡献代码与文档

所有对 FastExcel 的改进均可通过 Pull Request (PR) 实现。无论是修复 Bug、优化代码、增强功能，还是改进文档，都非常欢迎！

### 您可以贡献的方向
- 修复错别字
- 修复 Bug
- 删除冗余代码
- 添加测试用例
- 增强功能
- 添加注释以提升代码可读性
- 优化代码结构
- 改进或完善文档

**原则**：**任何有助于项目改进的 PR 都值得鼓励！**

在提交 PR 前，请熟悉以下指南：
1. [工作区准备](#工作区准备)
2. [分支定义](#分支定义)
3. [提交规则](#提交规则)
4. [PR 说明](#pr说明)

---

### 工作区准备

确保您已注册 GitHub 账号，并按照以下步骤完成本地开发环境配置：
1. **Fork 仓库**：在 FastExcel 的 [GitHub 页面](https://github.com/CodePhiliaX/fastexcel) 点击 `Fork` 按钮，将项目复制到您的 GitHub 账户下，例如：`https://github.com/<your-username>/fastexcel`。
2. **克隆代码库**：运行以下命令将 Fork 的项目克隆到本地：
   ```bash
   git clone git@github.com:<your-username>/fastexcel.git
   ```
3. **设置上游仓库**：将官方仓库设置为 `upstream`，方便同步更新：
   ```bash
   git remote add upstream git@github.com:CodePhiliaX/fastexcel.git
   git remote set-url --push upstream no-pushing
   ```
   运行 `git remote -v` 可检查配置是否正确。

---

### 分支定义

在 FastExcel 中，所有贡献应基于 `main` 开发分支。此外，还有以下分支类型：
- **release 分支**：用于版本发布（如 `0.6.0`, `0.6.1`）。
- **feature 分支**：用于开发较大的功能。
- **hotfix 分支**：用于修复重要 Bug。

提交 PR 时，请确保变更基于 `main` 分支。

---

### 提交规则

#### 提交信息
请确保提交消息清晰且具有描述性，遵循以下格式：
- **docs**: 更新文档，例如 `docs: 更新 PR 提交指南`。
- **feature**: 新功能，例如 `feature: 支持 并发写入`。
- **bugfix**: 修复 Bug，例如 `bugfix: 修复空指针异常`。
- **refactor**: 重构代码，例如 `refactor: 优化数据处理逻辑`。
- **test**: 增加或改进测试，例如 `test: 添加单元测试`。

不建议使用模糊的提交信息，如：
- ~~修复问题~~
- ~~更新代码~~

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

---

### PR 说明

为了帮助审阅者快速了解 PR 的内容和目的，请使用 [PR 模板](.github/PULL_REQUEST_TEMPLATE/pull_request_template.md)。详细的描述将极大提高代码审阅效率。

---

## 测试用例贡献

任何测试用例的贡献都值得鼓励，尤其是单元测试。建议在对应模块的 `test` 目录中创建 `XXXTest.java` 文件，推荐使用 JUnit5 框架。

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

请遵循 [阿里巴巴 Java 编码规范](https://alibaba.github.io/Alibaba-Java-Coding-Guidelines/) 进行代码编写。  
您可以选择安装以下插件（非必需）以帮助检查代码风格：
- **IntelliJ IDEA 插件**：[安装指南](https://github.com/alibaba/p3c/blob/master/idea-plugin/README.md)
- **Eclipse 插件**：[安装指南](https://github.com/alibaba/p3c/blob/master/eclipse-plugin/README.md)

---

**最后，感谢您对 FastExcel 的支持！每一份帮助，都是我们前进的动力。**