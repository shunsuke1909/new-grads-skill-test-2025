# はじめに
適性テスト用環境です。

- フロントエンド：Next.js
- バックエンド：Spring Boot
- DB：postgresql

テスト時、バックエンドへのリクエストurlは一律`http://localhost:80/api`を使用してください。

# SetUp
## git clone
C:\Users\{ユーザー名}配下でgit clone

```
git clone https://gitlab.insight-inc.biz/insight/new-grads/new-grads-skill-test-2025.git
```

## 適性テスト用ローカルブランチの作成
```
git checkout -b {自分の名字}-skill-test

# リモートリポジトリにpush
git push origin {自分の名字}-skill-test
```

## wsl
以下のドライブからalma-docker.tarをダウンロードし、C:\wsl\repository\dev-defaultに配置する
https://drive.google.com/drive/folders/1D596OKXws4wXZPbhROjlDP28hOjfC-t_?usp=drive_link

```
wsl --import skill-test c:\wsl\skill-test c:\wsl\repository\dev-default\alma-docker.tar
wsl -d skill-test
```

## apiコンテナ起動
本リポジトリをクローンした場所までcdし、以下のコマンドを実行
```
# docker起動
docker-compose up -d

# spring boot起動
docker-compose exec api /bin/bash
cd api
mvn spring-boot:run
```

### 動作確認①
http://localhost:8080/

Hello, World!と出力されること

## front
wsl上でclientディレクトリまでcdし、以下のコマンドを実行
```
npm install
```

### build command
`http://localhost:3000`でアクセスする場合
```
npm run dev
```

`http://localhost:80`でアクセスする場合
```
npm run build
```

### 動作確認②
http://localhost:3000/test

This is a test page.と出力されること

http://localhost:80/test

This is a test page.と出力されること

http://localhost:80/api

Hello, World!と出力されること


## DB
- A5M2をホストPCにインストールする
- docker-compose.ymlに定義しているdbコンテナの接続情報を参照し、A5M2にデータベースを接続してください