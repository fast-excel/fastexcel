# Contribution Guidelines

Welcome every user and developer in the community to become contributors. Whether it's reporting issues, improving documentation, submitting code, or providing technical support, your participation will help make FastExcel better.

---

## Contribution Directions

There are many ways to contribute to FastExcel:

- Fix typos
- Fix bugs
- Remove redundant code
- Add test cases
- Enhance functionality
- Add comments to improve code readability
- Optimize code structure
- Improve or refine documentation

**Principle**:
- Any pull request that contributes to the improvement of the project should be encouraged.
- Before contributing a new feature, please propose and discuss it in an `issue` or `discussion`. We will not merge features that have not been discussed and confirmed.

---

## Contributing Code

All improvements can be implemented through Pull Request (PR). Before submitting a Pull Request, please familiarise yourself with the following guidelines:

### Workspace Preparation

To develop FastExcel, you need **Maven 3.8.1 or above** and **JDK (Java Development Kit) 17 or above**. Currently, it is recommended to use **Maven 3.9.0 or above** and **Java 21 or above** for the development environment. However, you must use **Java 1.8** compatible language features during compilation to ensure FastExcel can run in environments with Java 1.8 or above.

> You can use tools such as [SDKMAN](https://sdkman.io/) to configure multiple versions of the Java toolchain.

### Fork the repository

Ensure that you have registered a GitHub account and follow the steps below to configure your local development environment:

**Fork the repository**: Click the `Fork` button on the FastExcel [GitHub page](https://github.com/fast-excel/fastexcel) to copy the project to your GitHub account.

```
https://github.com/<your-username>/fastexcel
```

**Clone Repository**: Run the following command to clone the forked project to your local machine:
```bash
git clone git@github.com:<your-username>/fastexcel.git
```

**Set Upstream Repository**: Set the official repository as `upstream` for easy synchronization of updates:
```bash
git remote add upstream git@github.com:fast-excel/fastexcel.git
git remote set-url --push upstream no-pushing
```

Running `git remote -v` can verify if the configuration is correct.


### Branch definition

In FastExcel, all contributions should be based on the `main` development branch. Additionally, there are the following branch types:

- **release branches**: Used for version releases (e.g., `1.1.0`, `1.1.1`).
- **feature branches**: Used for developing major features.
- **hotfix branches**: Used for fixing critical bugs.

When submitting a PR, please ensure that the changes are based on the `main` branch.

### Commit Rules

#### Commit Message

Please ensure that commit messages are clear and descriptive, use **English**, and do not exceed 100 characters.

The following types of commit messages are allowed and must follow the following format:

- **docs**: Update documentation, e.g., `docs: update README.md`
- **feature/feat**: New features, e.g., `feature: support for xxx`
- **bugfix/fix**: Bug fixes, e.g., `fix: fix NPE in the A class`
- **refactor**: Code refactoring (no functional changes), e.g., `refactor: optimise data processing logic`
- **style**: Code formatting, e.g., `style: update code style`
- **test**: Adding or improving tests, e.g., `test: add new test cases`
- **chore**: Changes to the build process or auxiliary tools, e.g., `chore: improve issue template`
- **dependency**: Modifications to third-party dependency libraries, e.g., `dependency: upgrade poi version to 5.4.1`

Avoid using vague commit messages like:

- ~~fixed issue~~
- ~~update code~~

For assistance, refer to [How to Write a Git Commit Message](http://chris.beams.io/posts/git-commit/).

#### Commit Content

Each commit should contain complete and reviewable changes, ensuring:

- Avoid committing overly large changes.
- Each commit content is independent and can pass CI tests.

Also, ensure the correct Git user information is configured when committing:

```bash
git config --get user.name
git config --get user.email
```

### PR Description

To help reviewers quickly understand the content and purpose of the PR, use the  [PR 模板](https://github.com/fast-excel/fastexcel/blob/main/.github/pull_request_template.md). A detailed description greatly improves code review efficiency.

---

## Contribution of Test Cases

Any contribution of test cases is encouraged, especially unit tests. It is recommended to create `XXXTest.java` files in the corresponding module's `test` directory, preferably using the `JUnit5` framework.

---

## Contribution Document

Documentation is an important component of the FastExcel official website and serves as a vital bridge between the project and the community.

### Directory Structure Description

```bash
.
├── quickstart     # 1. Quick Start
├── read           # 2. Read
├── write          # 3. Write
├── fill           # 4. Fill
├── community      # 5. Community
└── help           # 6. FAQ
```

### Documentation Writing Guidelines

- Use file paths with the `.md` extension
``` markdown
[Example](quickstart/example.md)
```

- Use paths relative to the docs/ directory
``` markdown
[Example](quickstart/example.md)
```

- Image files must be stored in the `docs/images` directory and referenced using relative directory paths.
``` markdown
[img](../../images/fill/listFill_file.png)
```

---

## Other Ways to Contribute

Apart from directly contributing code, the following ways are also valuable support for FastExcel:

- Answering other users' questions.
- Assisting in reviewing others' PRs.
- Providing improvement suggestions.
- Writing technical blogs to promote FastExcel.
- Sharing project-related knowledge in the community.

---

## Code Style

FastExcel uses [Spotless](https://github.com/diffplug/spotless) as its code formatting tool. Please ensure you run the following command to automatically format your code before submitting:

```bash
mvn spotless:apply
```

---

Finally, thank you for your support of FastExcel!
