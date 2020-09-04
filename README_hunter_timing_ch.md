# Timing-Plugin

[English](https://github.com/Leaking/Hunter/blob/master/README_hunter_timing.md)

timing-plugin可以帮你监控UI线程的卡顿方法，并且提供了算法，帮你dump出所有卡顿堆栈，堆栈中每一步的耗时都会标出，让你一眼就可以看出卡顿的位置。

并且提供了接口，让你按你自己方式来处理卡顿信息。


## 如何使用

在你的build.gradle中引入以下依赖


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
        classpath 'com.quinn.hunter:hunter-timing-plugin:1.0.0'
        classpath 'com.quinn.hunter:hunter-transform:1.0.0'
    }
}

apply plugin: 'hunter-timing'
    
```

Hunter-Timing 提供内置了一个默认的BlockHandler帮你打印卡顿的方法，也提供了其他两种BlockHandler实现

```java
IBlockHandler customBlockManager = new RankingBlockHandler();
BlockManager.installBlockManager(customBlockManager);

```

Dump卡顿分析结果

```java

customBlockManager.dump()

```

即将打印卡顿方法相关统计，分为平均值排行和次数排行


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

而如果你使用另一个IBlockHandler的实现，StacktraceBlockHandler，则即将等得到以下结果，一个个带有每步执行时间的堆栈

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
你可以可以实现自己的IBlockHandler，按自己的需求分析卡顿情况
    

另外，这个插件还提供一个扩展，带有几个配置参数    

```groovy

timingHunterExt {
    runVariant = 'DEBUG'
    whitelist = ['com.quinn.hunter.timing.DataSource', 'com.foo.package2']
    blacklist = ['com.quinn.hunter.timing.black', 'com.foo.package2']
}

```
**runVariant**

这个值默认是ALWAYS，表示这个插件在debug和release下都会修改字节码，这个配置全部有四个可选值，DEBUG, RELEASE, ALWAYS, NEVER，
每个值的含义，顾名思义，相信大家都懂。另外，每个基于Hunter的插件的Extension基本都提供了这个runVariant配置。

**whitelist**

白名单列表，只对这些包名下的class做监控，此处包名支持字符串前缀匹配

**blacklist**

黑名单列表，对这些包名下的class之外的所有class做监控，此处包名支持字符串前缀匹配

黑名单模式和白名单模式是互斥的，建议只用黑名单配置，或者只用白名单配置，如果两个都填了，则黑名单参数会被忽略

