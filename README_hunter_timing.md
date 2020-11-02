# Timing-Plugin

[中文](https://github.com/Leaking/Hunter/blob/master/README_hunter_timing_ch.md)

You can use it to time all your ui-thread methods, and dump the block traces with costed-time of every step.
You also can create a custom handler to consume the block detail in your way.  

## How to use it

Add some lines to your build.gradle

```groovy

dependencies {
    implementation 'com.quinn.hunter:hunter-timing-library:0.9.1'
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
        classpath 'com.quinn.hunter:hunter-timing-plugin:1.1.0'
        classpath 'com.quinn.hunter:hunter-transform:1.1.0'
    }
}

apply plugin: 'hunter-timing'
    
```

The Hunter-Timing has a internal default BlockHandler to process
the block messages, but you can set your custom BlockHandler.

```java
IBlockHandler customBlockManager = new RankingBlockHandler();
BlockManager.installBlockManager(customBlockManager);

```

You can dump the block trace

```java

customBlockManager.dump()

```

Dump result

```xml


    ------Average Block-Time Ranking----Top 6----
    com.quinn.hunter.timing.DataSource.modifyAndMoveFile: 2403.0ms(Count : 1)
    com.quinn.hunter.timing.DataSource.readFileContent: 1400.0ms(Count : 1)
    com.quinn.hunter.timing.DataSource.saveHugeFileToDisk: 1000.0ms(Count : 2)
    com.quinn.hunter.timing.DataSource.getUserName: 900.0ms(Count : 1)
    com.quinn.hunter.timing.DataSource.<clinit>: 804.0ms(Count : 1)
    com.quinn.hunter.timing.DataSource.<init>: 801.0ms(Count : 1)
    ------Block Count Ranking----Top 6----
    com.quinn.hunter.timing.DataSource.saveHugeFileToDisk: 2(Avg : 1000.0ms)
    com.quinn.hunter.timing.DataSource.getUserName: 1(Avg : 900.0ms)
    com.quinn.hunter.timing.DataSource.modifyAndMoveFile: 1(Avg : 2403.0ms)
    com.quinn.hunter.timing.DataSource.readFileContent: 1(Avg : 1400.0ms)
    com.quinn.hunter.timing.DataSource.<init>: 1(Avg : 801.0ms)
    com.quinn.hunter.timing.DataSource.<clinit>: 1(Avg : 804.0ms)
    
   
```

If you use StacktraceBlockHandler

```xml
    
    ----BlockStackTrace----Total 5----
    Block StackTrace 0
    com.quinn.hunter.timing.DataSource.readFileContent costed 1400ms
    com.quinn.hunter.timing.DataSource.modifyAndMoveFile costed 2403ms
    com.quinn.hunter.timing.MainActivity.onCreate is root
    
    Block StackTrace 1
    com.quinn.hunter.timing.DataSource.saveHugeFileToDisk costed 1000ms
    com.quinn.hunter.timing.DataSource.modifyAndMoveFile costed 2403ms
    com.quinn.hunter.timing.MainActivity.onCreate is root
    
    Block StackTrace 2
    com.quinn.hunter.timing.DataSource.<init> costed 800ms
    com.quinn.hunter.timing.DataSource.<clinit> costed 801ms
    com.quinn.hunter.timing.DataSource.getInstance is root
    
    Block StackTrace 3
    com.quinn.hunter.timing.DataSource.getUserName costed 901ms
    com.quinn.hunter.timing.MainActivity.onCreate is root
    
    Block StackTrace 4
    com.quinn.hunter.timing.DataSource.saveHugeFileToDisk costed 1000ms
    com.quinn.hunter.timing.MainActivity.onCreate is root
  
    
```
You can use your custom IBlockHandler to analyse the block detail.
    

In addition, hunter-timing-plugin provides a extendsion

```groovy

timingHunterExt {
    runVariant = 'DEBUG'
    whitelist = ['com.quinn.hunter.timing.DataSource', 'com.foo.package2']
    blacklist = ['com.quinn.hunter.timing.black', 'com.foo.package2']
}

```
**runVariant**

its default value is ALWAYS, it means this plugin will perform 
in both debug build and release build. There are four available runVariant, 
DEBUG, RELEASE, ALWAYS, NEVER. 

**whitelist**

it means the plugin will only handle the classes in these packages, you can 
provide package with prefix name.

**blacklist** 

it means the plugin will handle all classes except the classes in these packages, you can 
provide package with prefix name.

You'd better just provide whitelist or blacklist, if you provide both of them, blacklist is ignored.
