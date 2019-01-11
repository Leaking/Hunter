# LogLine-Plugin

This is a plaything tool/plugin, sometimes you may need line number to help you locate the logcat content,
especially, same logcat print in different lines in the same one java class.

This plugin can help you.

## How to use it

Add some lines to your build.gradle

```groovy

dependencies {
    implementation 'com.quinn.hunter:hunter-linelog-library:0.8.5'
}

repositories {
    jcenter()
}

buildscript {
    repositories {
        jcenter()
        google()
    }
    dependencies {
        classpath 'com.quinn.hunter:hunter-linelog-plugin:0.9.1'
    }
}

apply plugin: 'hunter-linelog'
    
```

You logcat will be inserted line number automatically.


Origin logcat

```java

I/MainActivity: onCreate

```
Tranformed logcat

```java

I/MainActivity[21]: onCreate

```  