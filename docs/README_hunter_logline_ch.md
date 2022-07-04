# LogLine-Plugin

[English](https://github.com/Leaking/Hunter/blob/master/README_hunter_logline.md)

这个插件会为你每行日志添加行号，可以帮你更快定位日志，尤其有时候你在一个Java文件中不同地方，打印了一样的日志。

## 快速引入

在build.gradle中添加以下依赖

```groovy

dependencies {
    implementation 'com.quinn.hunter:hunter-linelog-library:1.2.0'
}

repositories {
    maven {
        name = "GithubPackages"
        url = uri("https://maven.pkg.github.com/Leaking/Hunter")
        credentials {
            username = 'Leaking'
            password = '\u0067\u0068\u0070\u005f\u0072\u006a\u0041\u004b\u0037\u006d\u0048\u0047\u006b\u0031\u0045\u0039\u0063\u0048\u0044\u0076\u004f\u0039\u0078\u006f\u0046\u0048\u004d\u0049\u0032\u006a\u0047\u0057\u0047\u0068\u0032\u0036\u0065\u0075\u0043\u006b'
        }
    }
}

buildscript {
    repositories {
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/Leaking/Hunter")
            credentials {
                username = 'Leaking'
                password = '\u0067\u0068\u0070\u005f\u0072\u006a\u0041\u004b\u0037\u006d\u0048\u0047\u006b\u0031\u0045\u0039\u0063\u0048\u0044\u0076\u004f\u0039\u0078\u006f\u0046\u0048\u004d\u0049\u0032\u006a\u0047\u0057\u0047\u0068\u0032\u0036\u0065\u0075\u0043\u006b'
            }
        }
        google()
    }
    dependencies {
        classpath 'com.quinn.hunter:hunter-linelog-plugin:1.2.0'
        classpath 'com.quinn.hunter:hunter-transform:1.2.1'
    }
}

apply plugin: 'hunter-linelog'
    
```



未使用这个插件时打印的日志

```java

I/MainActivity: onCreate

```
使用插件后的日志

```java

I/MainActivity[21]: onCreate

```  
如果你只想在debug模式下使用该插件，则可以这样设置，

```groovy

linelogHunterExt {
    runVariant = 'DEBUG'  //'DEBUG', 'RELEASE', 'ALWAYS', 'NEVER', The 'ALWAYS' is default value
}

``` 