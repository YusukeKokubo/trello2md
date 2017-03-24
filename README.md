# About

Trelloのボードidを指定すると、ボードにあるカードをMarkdown形式で出力してくれるツールです。

## Example

![01](https://raw.githubusercontent.com/YusukeKokubo/trello2md/master/screenshots/trello2md-01.png)
↓
![02](https://raw.githubusercontent.com/YusukeKokubo/trello2md/master/screenshots/trello2md-02.png)


# Requirements

- Gradle

# Install

```
git clone git@github.com:YusukeKokubo/trello2md.git
cd trello2md
cp secret.gradle.sample ./secret.gradle
```

- [ここ](https://developers.trello.com/get-started/start-building) からAPIのKeyとTokenを取得します
- `secret.gradle` を開いてKeyとTokenを設定します

# Run

```
gradle run -PprojectId="" # projectIdを指定してください
```
