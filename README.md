# 商品一覧の作成
## 使用した技術要素
 - Java 1.8
 - Spring Boot 1.5.4
 - H2

## 全体の設計・構成についての説明

画面構成は、
1. 商品の一覧画面
2. 商品の紹介作成画面
3. 商品の詳細画面 <br>

の3種類で構成されている。

## 開発環境のセットアップ手順
1. このプロジェクトを git clone する
2. プロジェクト内に移動し、 mvn spring-boot:run でSpringを起動する。
3. 起動後は、 localhost:8080 でページに移動できる。

## 画面ごとの内容

### 共通画面
ヘッダー部分に
 <li>一覧画面にアクセスする TOPタグ
 <li>作成画面にアクセスする CREATEタグ
が全ての画面にあります。

### 一覧画面

一番初めにアクセスする画面です。<br>
初めはDBに入っていないのでカラの状態です。
DBに登録すると、商品のタイトルや価格の一覧が表として書き出す。
表にはその商品の詳細を見ることができる画面に移動できます。<br>
検索したい商品のタイトルを入力すると、その商品の紹介のみ表に書き出されます。
タイトルは完全一致出ないと書き出されません。

### 作成画面

商品の紹介を作成する画面です。<br>
作成画面には、
 <li>商品のタイトル
 <li>商品の説明
 <li>商品の価格
 <li>商品の画像
の4項目を入力することで商品の紹介を作成することができます。<br>
作成は全ての項目を入力しなければならない。

### 詳細画面

１つの商品の紹介・編集をする画面です。<br>
商品の詳細を書き出す部分が上に、
商品の編集をする部分が下に構成されている画面です。<br>
編集は作成と同様に全ての項目を入力しなければならない。
また、その商品のデータを消去するボタンもあります。

## API一覧
|api名|HTTPメソッド|リソースパス|
|---|---|---|
|商品一件作成|POST|/api/product/|
|商品一件取得|GET|/api/product/{id}|
|商品一件更新|POST|/api/product/{id}|
|商品一件削除|DELETE|/api/product/{id}|
|商品全件取得|GET|/api/product/all|
|商品取得|POST|/api/product/sam|

## ページ一覧
|一覧ページの表示|GET|/|
|詳細ページの表示|GET|/{id}|
|作成ページの表示|GET|/create|
