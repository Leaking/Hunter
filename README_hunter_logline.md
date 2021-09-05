# LogLine-Plugin

[中文](https://github.com/Leaking/Hunter/blob/master/README_hunter_logline_ch.md)

This is a plaything tool/plugin, sometimes you may need line number to help you locate the logcat content,
especially, same logcat print in different lines in the same one java class.

This plugin can help you.

## How to use it

Add some lines to your build.gradle

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
            password = '\u0067\u0068\u0070\u005f\u0058\u006d\u0038\u006e\u0062\u0057\u0031\u0053\u0053\u0042\u006a\u004a\u0064\u006f\u0071\u0048\u0064\u006b\u0036\u0034\u0077\u0031\u0054\u0066\u0074\u0071\u0052\u0046\u0068\u0042\u0032\u0047\u0057\u0037\u0046\u0070'
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
                password = '\u0067\u0068\u0070\u005f\u0058\u006d\u0038\u006e\u0062\u0057\u0031\u0053\u0053\u0042\u006a\u004a\u0064\u006f\u0071\u0048\u0064\u006b\u0036\u0034\u0077\u0031\u0054\u0066\u0074\u0071\u0052\u0046\u0068\u0042\u0032\u0047\u0057\u0037\u0046\u0070'
            }
        }
        google()
    }
    dependencies {
        classpath 'com.quinn.hunter:hunter-linelog-plugin:1.2.0'
        classpath 'com.quinn.hunter:hunter-transform:1.2.0'
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

如果你只想在debug模式下使用该插件，则可以这样设置，

```groovy

debugHunterExt {
    runVariant = 'DEBUG'  //'DEBUG', 'RELEASE', 'ALWAYS', 'NEVER', The 'ALWAYS' is default value
}