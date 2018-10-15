# Hunter

Hunter是这么一个框架，帮你快速开发插件，在编译过程中修改字节码，它底层基于[ASM](https://asm.ow2.io/) 和 [Gradle Transform API](http://tools.android.com/tech-docs/new-build-system/transform-api)
实现。在这个框架基础上，我尝试开发了几款实用的插件。你也可以用Hunter开发自己的插件，诸如实现App性能监控（UI，网络等等），加强或修改第三方库以满足你的需求，甚至可以加强、修改Android framework的接口。

Hunter本身支持增量、并发编译，所以不用担心使用这一系列插件会增加太多编译时间。

 + [Timing-Plugin](#timing-plugin): 帮你监控所有UI线程的执行耗时，并且提供了算法，帮你打印出一个带有每步耗时的堆栈，统计卡顿方法分布
 + [OkHttp-Plugin](#okhttp-plugin): 可以为你的应用所有的OkhttpClient设置全局 [Interceptor](https://github.com/square/okhttp/wiki/Interceptors) / [Eventlistener](https://github.com/square/okhttp/wiki/Events) 
(包括第三方依赖里的OkhttpClient)
 + [LogLine-Plugin](#logline-plugin): 为你的日志加上行号
 + [Debug-Plugin](#debug-plugin): 只要为指定方法加上某个annotation，就可以帮你打印出这个方法所有输入参数的值，以及返回值，执行时间(JakeWharton的[hugo](https://github.com/JakeWharton/hugo)
用AspectJ实现了类似功能, 而我的实现方式是基于ASM，ASM处理字节码的速度更快)
 + 你可以在这里查看我想继续开发的一些插件 [TODO](https://github.com/Leaking/Hunter/blob/master/TODO.md)，另外，欢迎你提供你宝贵的idea


## Timing-Plugin




```groovy


dependencies {
    implementation 'com.quinn.hunter:hunter-timing-library:0.8.5'
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
        classpath 'com.quinn.hunter:hunter-timing-plugin:0.8.5'
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
runVariant, 这个值默认是ALWAYS，表示这个插件在debug和release下都会修改字节码，这个配置全部有四个可选值，DEBUG, RELEASE, ALWAYS, NEVER，
每个值的含义，顾名思义，相信大家都懂。另外，每个基于Hunter的插件的Extension基本都提供了这个runVariant配置。

whitelist, 白名单列表，只对这些包名下的class做监控，此处包名支持字符串前缀匹配

blacklist, 黑名单列表，对这些包名下的class之外的所有class做监控，此处包名支持字符串前缀匹配

黑名单模式和白名单模式是互斥的，建议只是用黑名单配置，或者只是用白名单配置，如果两个都填了，则黑名单参数会被忽略


## OkHttp-Plugin


一个稍微上规模的项目，很大可能有很多个OkhttpClient散落在各个业务中，那么如果你想使用Interceptor/EventListener做网络监控，或者设置自定义Dns，
那就很麻烦，需要一处处为所有OkhttpClient设置Interceptor/EventListener/Dns，而且有些第三方依赖库里的OkhttpClient你是无能为力的。当然，你可以
反射去处理，但是这会遇到很多麻烦。

我在Okhtp提过类似问题的[issue](https://github.com/square/okhttp/issues/4228) 
 
OkHttp-Plugin 这个插件就可以帮忙你解决这个问题，让你两三句代码就可以为所有OkhttpClient设置统一的Interceptor/EventListener/Dns


```groovy


dependencies {
    implementation 'com.quinn.hunter:hunter-okhttp-library:0.8.5'
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
        classpath 'com.quinn.hunter:hunter-okhttp-plugin:0.8.5'
    }
}

apply plugin: 'hunter-okhttp'
    
```


```java

OkHttpHooker.installEventListenerFactory(CustomGlobalEventListener.FACTORY);
OkHttpHooker.installDns(new CustomGlobalDns());
OkHttpHooker.installInterceptor(new CustomGlobalInterceptor());
        
```



## Debug-Plugin

只要为指定方法加上@HunterDebug注解，就可以帮你打印出这个方法所有输入参数的值，以及返回值，执行时间


```groovy

dependencies {
    implementation 'com.quinn.hunter:hunter-debug-library:0.8.5'
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
        classpath 'com.quinn.hunter:hunter-debug-plugin:0.8.5'
    }
}

apply plugin: 'hunter-debug'

```

支持指定某个编译模式下才使用该插件

```groovy

debugHunterExt {
    runVariant = 'DEBUG'  //'DEBUG', 'RELEASE', 'ALWAYS', 'NEVER', The 'ALWAYS' is default value
}

``` 

比如以下代码

```java


@HunterDebug
private String appendIntAndString(int a, String b) {
    SystemClock.sleep(100);
    return a + " " + b;
}

```

运行时即将自动打印如下日志

```xml 

I/com/quinn/hunter/debug/MainActivity: ⇢ appendIntAndString[a="5", b="billions"]
                                       ⇠ appendIntAndString[0ms]="5 billions"

```

JakeWharton的[hugo](https://github.com/JakeWharton/hugo)用AspectJ实现了类似功能, 而我的实现方式是基于ASM，ASM处理字节码的速度更快

## LogLine-Plugin

这个插件会为你每行日志添加行号，可以帮你更快定位日志，尤其有时候你在一个Java文件中不同地方，打印了一样的日志。


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
        classpath 'com.quinn.hunter:hunter-linelog-plugin:0.8.6'
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



## Developer API
    
   
   [Wiki](https://github.com/Leaking/Hunter/wiki/Developer-API)
   

## License


    Copyright 2018 Quinn Chen

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.