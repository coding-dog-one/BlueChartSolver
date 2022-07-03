# BlueChartSolver

[![test](https://github.com/coding-dog-one/BlueChartSolver/actions/workflows/gradle.yml/badge.svg)](https://github.com/coding-dog-one/BlueChartSolver/actions/workflows/gradle.yml) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=coding-dog-one_BlueChartSolver&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=coding-dog-one_BlueChartSolver) [![Coverage](https://sonarcloud.io/api/project_badges/measure?project=coding-dog-one_BlueChartSolver&metric=coverage)](https://sonarcloud.io/summary/new_code?id=coding-dog-one_BlueChartSolver)

（数学の復習を兼ねて）Javaで「チャート式基礎からの数学」（通称、青チャート）全問制覇を目指します。

---

## 心構え

* 実装の美しさ、バグがないこと、数学的正確さよりも青チャートが解けることを最優先します（早く先に進みたいので）
* 書き心地の良さを考慮する。我慢しない。めんどくさいという気持ちを大事にする。

## ディレクトリ構成

もう少し整理したいが…とりあえず現状は以下のような感じ。

<pre>
.
├── LICENSE
├── README.md
├── .github
│    └── workflows: GitHub Actionsの定義ファイル。
│        ├── build.yml: SonarCloudで静的解析をします。（ビルドじゃないじゃん）
│        └── gradle.yml: JUnit5の自動テストを実行します。（build.ymlでもテストを実行しているからいらないかもしれないなぁ）
├── app
│   ├── build.gradle
│   └── src
│       ├── main
│       │   ├── java
│       │   │   └── blue_chart_solver: 青本を解くプログラム本体。
│       │   │       ├── app
│       │   │       │   └── App.java: エントリーポイント。いらない子。
│       │   │       ├── helpers: Solverの使い勝手を良くするための便利ツール。
│       │   │       │   ├── OperatorParser.java: 文字列を演算子として解析する。SimpleParserから呼び出される。
│       │   │       │   ├── ReadResult.java: StringReaderが読みとった結果。
│       │   │       │   ├── SimpleParser.java: 文字列を多項式として解析する。パーサーの本体。
│       │   │       │   ├── StringReader.java: 文字列を左から右に読み進めるリーダー。ReadResultを返す。
│       │   │       │   └── TermParser.java: 文字列を項として解析する。SimpleParserに呼び出される。
│       │   │       └── models: 数学的要素をモデル化したもの。
│       │   │           ├── OrderedTermsList.java: 指定した条件で多項式の項をソートした結果。
│       │   │           ├── Polynomial.java: 多項式。項の集合体。
│       │   │           ├── Term.java: 項。変数の集合体。
│       │   │           ├── Variable.java: 変数。
│       │   │           └── operators: 演算子たち。
│       │   │               ├── Addition.java
│       │   │               ├── Multiply.java
│       │   │               ├── Operator.java
│       │   │               └── Subtraction.java
│       │   └── resources
│       │       ├── logback-common.xml
│       │       ├── logback-test.xml
│       │       └── logback.xml
│       └── test
│           ├── java
│           │   ├── BlueChart: Solverを使って実際に青本を解いてみようのコーナー。
│           │   │   ├── Chapter1
│           │   │   │   └── Section1
│           │   └── blue_chart_solver: 単体テスト。
│           │       ├── helpers
│           │       │   ├── OperatorParserTest.java
│           │       │   ├── ReadResultTest.java
│           │       │   ├── SimpleParserTest.java
│           │       │   ├── StringReaderTest.java
│           │       │   └── TermParserTest.java
│           │       └── models
│           │           ├── MonomialTest.java
│           │           ├── PolynomialTest.java
│           │           └── VariableTest.java
│           └── resources
├── buildSrc
├── gradle
├── gradlew
├── gradlew.bat
└── settings.gradle
</pre>

## SimpleParserについて

青本に登場する数式のみサポートする簡易的なパーサー。  
現在解釈可能な文字列はおおむね以下の通り。
* 単項式  
eg. "-5ab^2"
* 単項式(\\(多項式\\))+  
eg. "-5ab^2(x + y)(x - y)"
* (\\(多項式\\)(^指数)?)+  
eg. "(x + y)(x - y)(a + b)^3"
* (\\(多項式\\)(^指数)?)+( 演算子 \\(多項式\\)(^指数)?)+  
eg. "(x + y)(x - y)(a + b)^3 + (x - y)^3 - (x + y)^2 - (x^2 - x + y^2)"

凡例
* 変数 = `[a-zA-Z]`
* 定数・係数・指数 = `[1-9][0-9]*`
* 単項式 = `-?{定数}|-?{係数}?({変数}(^{指数})?)+`
* 演算子 = `+|-`
* 多項式 = `{単項式}( {演算子} {単項式})*`