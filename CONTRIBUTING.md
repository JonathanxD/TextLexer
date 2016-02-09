# Contributing

## Requirements

- Git
- Java 8
- Gradle (latest) **Optional**

### Clone

```
git clone https://github.com/JonathanxD/TextLexer.git
```

### Copy scripts

```
$ cd TextLexer
$ cp ./scripts/pre-commit ./.git/hooks/
```

### Prepare environment

Eclipse:

```
./gradlew eclipse
```

Open eclipse and go to `File > Import > General`, search `TextLexer` project, check `Search for nested projects` box and click `Finish`.

IntelliJ:

Open IntelliJ IDEA and go to `File > Open`, search `TextLexer` project, open the directory and click in `build.gradle` and click `Ok`.

**Note**

You need to have `Gradle Plugin` installed.

### Make modifications

Yep :D

### Build the project

Via [Command Line](BUILDING.md)

or via `Gradle Plugin`

### Create a pull request

You need to know how to `create a pull request`!

You can read these topics to learn about [`Collaborating on projects using pull request`](https://help.github.com/categories/collaborating-on-projects-using-pull-requests/)