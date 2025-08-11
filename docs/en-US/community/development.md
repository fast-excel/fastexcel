# Run or Build

This section explains how to build FastExcel from source code.

## Development environment

Developing FastExcel requires **Maven 3.8** and **JDK (Java Development Kit)**. Currently, the development environment requires **Java 21** or higher, but during compilation, you must use language features compatible with **Java 1.8** to ensure that FastExcel can run in Java 1.8 or higher environments.

> You can use tools such as [SDKMAN](https://sdkman.io/) to configure multiple versions of the Java toolchain.

## IDEA Settings

The following guidelines are for development using `IntelliJ IDEA`. Other versions may have some minor differences. Please follow all steps carefully.

### Project Settings

Set the project [Language Level](https://www.jetbrains.com/help/idea/project-settings-and-structure.html#language-level) to `8` to ensure backward compatibility.

## Git Usage

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

## Compilation

Run the following command to compile
```bash
mvn clean install -DskipTests
```

To speed up the build process, you can:
- Use `-DskipTests` to skip unit tests
- Use Maven's parallel build feature, e.g., `mvn clean install -DskipTests -T 1C`

## Code Style

FastExcel uses [Spotless](https://github.com/diffplug/spotless) as its code formatting tool. Please ensure you run the following command to automatically format your code before submitting:

```bash
mvn spotless:apply
```
