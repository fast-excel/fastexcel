<p align="center">
    <img src="logo.svg"/>
</p>

<p align="center">
    <a href="README.md">中文</a> | <a href="README_EN.md">English</a> | <a href="README_JP.md">日本語</a>
</p>

<p align="center">
    <a href="https://github.com/fast-excel/fastexcel/actions/workflows/ci.yml"><img alt="GitHub Actions Workflow Status" src="https://img.shields.io/github/actions/workflow/status/fast-excel/fastexcel/ci.yml?style=flat-square&logo=github"></a>
    <a href="https://github.com/fast-excel/fastexcel/actions/workflows/nightly.yml"><img alt="GitHub Actions Workflow Status" src="https://img.shields.io/github/actions/workflow/status/fast-excel/fastexcel/nightly.yml?style=flat-square&logo=nightly"></a>
    <a href="https://github.com/fast-excel/fastexcel/blob/main/LICENSE"><img alt="GitHub License" src="https://img.shields.io/github/license/fast-excel/fastexcel?logo=apache&style=flat-square"></a>
    <a href="https://mvnrepository.com/artifact/cn.idev.excel/fastexcel"><img alt="Maven Central Version" src="https://img.shields.io/maven-central/v/cn.idev.excel/fastexcel?logo=apachemaven&style=flat-square"></a>
</p>

<p align="center">
    <a href="https://github.com/fast-excel/fastexcel/actions/workflows/deploy-docs.yml"><img alt="Document" src="https://img.shields.io/github/actions/workflow/status/fast-excel/fastexcel/ci.yml?style=flat-square&logo=read-the-docs"></a>
    <a href="https://deepwiki.com/fast-excel/fastexcel"><img src="https://img.shields.io/badge/DeepWiki-fast--excel%2Ffastexcel-blue.svg?logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACwAAAAyCAYAAAAnWDnqAAAAAXNSR0IArs4c6QAAA05JREFUaEPtmUtyEzEQhtWTQyQLHNak2AB7ZnyXZMEjXMGeK/AIi+QuHrMnbChYY7MIh8g01fJoopFb0uhhEqqcbWTp06/uv1saEDv4O3n3dV60RfP947Mm9/SQc0ICFQgzfc4CYZoTPAswgSJCCUJUnAAoRHOAUOcATwbmVLWdGoH//PB8mnKqScAhsD0kYP3j/Yt5LPQe2KvcXmGvRHcDnpxfL2zOYJ1mFwrryWTz0advv1Ut4CJgf5uhDuDj5eUcAUoahrdY/56ebRWeraTjMt/00Sh3UDtjgHtQNHwcRGOC98BJEAEymycmYcWwOprTgcB6VZ5JK5TAJ+fXGLBm3FDAmn6oPPjR4rKCAoJCal2eAiQp2x0vxTPB3ALO2CRkwmDy5WohzBDwSEFKRwPbknEggCPB/imwrycgxX2NzoMCHhPkDwqYMr9tRcP5qNrMZHkVnOjRMWwLCcr8ohBVb1OMjxLwGCvjTikrsBOiA6fNyCrm8V1rP93iVPpwaE+gO0SsWmPiXB+jikdf6SizrT5qKasx5j8ABbHpFTx+vFXp9EnYQmLx02h1QTTrl6eDqxLnGjporxl3NL3agEvXdT0WmEost648sQOYAeJS9Q7bfUVoMGnjo4AZdUMQku50McDcMWcBPvr0SzbTAFDfvJqwLzgxwATnCgnp4wDl6Aa+Ax283gghmj+vj7feE2KBBRMW3FzOpLOADl0Isb5587h/U4gGvkt5v60Z1VLG8BhYjbzRwyQZemwAd6cCR5/XFWLYZRIMpX39AR0tjaGGiGzLVyhse5C9RKC6ai42ppWPKiBagOvaYk8lO7DajerabOZP46Lby5wKjw1HCRx7p9sVMOWGzb/vA1hwiWc6jm3MvQDTogQkiqIhJV0nBQBTU+3okKCFDy9WwferkHjtxib7t3xIUQtHxnIwtx4mpg26/HfwVNVDb4oI9RHmx5WGelRVlrtiw43zboCLaxv46AZeB3IlTkwouebTr1y2NjSpHz68WNFjHvupy3q8TFn3Hos2IAk4Ju5dCo8B3wP7VPr/FGaKiG+T+v+TQqIrOqMTL1VdWV1DdmcbO8KXBz6esmYWYKPwDL5b5FA1a0hwapHiom0r/cKaoqr+27/XcrS5UwSMbQAAAABJRU5ErkJggg==" alt="DeepWiki"></a>
    <a href="https://readmex.com/fast-excel/fastexcel"><img src="https://raw.githubusercontent.com/CodePhiliaX/resource-trusteeship/main/readmex.svg" alt="ReadmeX"></a>
</p>

## FastExcelとは

FastExcelは、元EasyExcelの作者によって作成された最新の作品です。2023年に私がアリババを退職した後、アリババがEasyExcelの更新を停止することを発表したことに伴い、このプロジェクトを引き続きメンテナンスおよびアップグレードすることを決定しました。再び始める際に、私はこのフレームワークの名前をFastExcelとし、Excelファイルの処理における高性能を強調しました。その使いやすさだけではありません。

FastExcelは常にフリーでオープンソースであり、ビジネスに適したApacheオープンソースプロトコルを使用しています。。これにより、開発者や企業に大きな自由度と柔軟性が提供されます。FastExcelのいくつかの顕著な特徴は以下の通りです：

- 1. 元のEasyExcelのすべての機能と特性との完全な互換性があるため、ユーザーはシームレスに移行できます。
- 2. EasyExcelからFastExcelへの移行は、パッケージ名とMaven依存関係を単純に変更するだけでアップグレードが完了します。
- 3. 機能的には、EasyExcelよりも多くの革新と改善を提供します。
- 4. FastExcel 1.0.0バージョンでは、指定行数のExcelを読み取り、ExcelをPDFに変換する機能を新たに追加しました。

私たちは、将来的にさらなる新機能を導入し続けて、ユーザーエクスペリエンスとツールの実用性を継続的に向上させる計画です。開発の進捗を追い、FastExcelの発展をサポートするため、「プログラマー・シャオラン」の公式アカウントをお見逃しなく。FastExcelは、Excelファイルの処理におけるお客様の最良の選択肢となることに専念しています。

## 主な機能

- 1. 高性能な読み書き：FastExcelはパフォーマンスの最適化に重点を置き、大規模なExcelデータを効率的に処理することができます。いくつかの伝統的なExcel処理ライブラリと比較して、メモリ消費を大幅に削減できます。
- 2. 簡単で使いやすい：このライブラリは簡潔で直感的なAPIを提供しており、開発者がプロジェクトに簡単に統合でき、簡単なExcel操作から複雑なデータ処理まで迅速に習得できます。
- 3. ストリーム操作：FastExcelはストリーム読み取りをサポートしており、大量のデータを一度にロードする問題を最小限に抑えます。この設計方式は数十万行、または数百万行のデータを処理する際に特に重要です。

## インストール

以下の表は、各バージョンのFastExcel基礎ライブラリのJava言語バージョンの最低要件を一覧にしたものです：

| バージョン   | JDKバージョンサポート範囲 | 備考                             |
|--------------|:--------------------------:|----------------------------------|
| 1.0.0+       | JDK8 - JDK21               | 現在のマスターブランチ、EasyExcelと完全互換 |

最新のFastExcelバージョンを使用することを強くお勧めします。最新バージョンのパフォーマンス最適化、バグ修正、および新機能は、使用の利便性を向上させます。

> 現在、FastExcelの基盤としてPOIを使用しています。プロジェクトに既にPOI関連のコンポーネントが含まれている場合、POI関連のjarファイルを手動で除外する必要があります。

### Maven
Mavenでプロジェクトを構築する場合、`pom.xml`ファイルに次の構成を含めてください：
```xml
<dependency>
    <groupId>cn.idev.excel</groupId>
    <artifactId>fastexcel</artifactId>
    <version>1.2.0</version>
</dependency>
```
### Gradle

Gradleでプロジェクトを構築する場合、build.gradleファイルに次の構成を含めてください：
```gradle
dependencies {
    implementation 'cn.idev.excel:fastexcel:1.2.0'
}
```
## 更新する
具体的なバージョンアップ内容は[バージョンアップ詳細](CHANGELOG.md)で確認できます。 [Maven Central Repository](https://mvnrepository.com/artifact/cn.idev.excel/fastexcel) 内のすべてのバージョンをクエリすることもできます。
## EasyExcelとFastExcelの違い
- FastExcelはEasyExcelのすべての機能をサポートしていますが、FastExcelのパフォーマンスはより良く、より安定しています。
- FastExcelとEasyExcelのAPIは完全に一致しているため、シームレスに切り替えることができます。
- FastExcelは継続して更新され、バグを修正し、パフォーマンスを最適化し、新機能を追加します。
## EasyExcelをFastExcelにアップグレードする方法
### 1. 依存関係の変更
EasyExcelの依存関係をFastExcelの依存関係に置き換えます。以下のように：
```xml
<!-- easyexcel 依存 -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>easyexcel</artifactId>
    <version>xxxx</version>
</dependency>
```
を以下に置き換えます
```xml
<dependency>
    <groupId>cn.idev.excel</groupId>
    <artifactId>fastexcel</artifactId>
    <version>1.2.0</version>
</dependency>
```
### 2. コードの修正
EasyExcelのパッケージ名をFastExcelのパッケージ名に置き換えます。以下のように：
```java
// EasyExcelのパッケージ名をFastExcelのパッケージ名に置き換えます
import com.alibaba.excel.*;
```
を以下に置き換えます
```java
import cn.idev.excel.*;
```
### 3. コードを修正せずにFastExcelを直接依存する
何らかの理由でコードを修正したくない場合は、FastExcelに直接依存し、pom.xmlファイル内でFastExcelに直接依存できます。EasyExcelとFastExcelは共存できますが、長期的にはFastExcelを使用することを推奨します。

### 4. 以後はFastExcelクラスを使用することをお勧めします
互換性を考慮してEasyExcelクラスが保持されていますが、今後はFastExcelクラスを使用することをお勧めします。FastExcelクラスはFastExcelのエントリークラスであり、EasyExcelクラスのすべての機能を含んでいます。新機能は以後、FastExcelクラスにのみ追加されます。

## シンプルな例：Excelファイルを読む
以下にExcelドキュメントを読んでいる例を示します：
```java
// ReadListenerインターフェースを実装してデータを読む操作を設定します
public class DemoDataListener implements ReadListener<DemoData> {
    @Override
    public void invoke(DemoData data, AnalysisContext context) {
        System.out.println("データエントリを解析しました" + JSON.toJSONString(data));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("すべてのデータの解析が完了しました！");
    }
}

public static void main(String[] args) {
    String fileName = "demo.xlsx";
    // Excelファイルを読む
    FastExcel.read(fileName, DemoData.class, new DemoDataListener()).sheet().doRead();
}
```
## シンプルな例：Excelファイルを作成
以下はExcelドキュメントを作成する簡単な例です：
```java
// サンプルデータクラス
public class DemoData {
    @ExcelProperty("文字列タイトル")
    private String string;
    @ExcelProperty("日付タイトル")
    private Date date;
    @ExcelProperty("数字タイトル")
    private Double doubleData;
    @ExcelIgnore
    private String ignore;
}

// 書き込むデータを準備します
private static List<DemoData> data() {
    List<DemoData> list = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
        DemoData data = new DemoData();
        data.setString("文字列" + i);
        data.setDate(new Date());
        data.setDoubleData(0.56);
        list.add(data);
    }
    return list;
}

public static void main(String[] args) {
    String fileName = "demo.xlsx";
    // 「テンプレート」と名付けたシートを作成し、データを書き込みます
    FastExcel.write(fileName, DemoData.class).sheet("テンプレート").doWrite(data());
}
```

