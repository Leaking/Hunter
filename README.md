# Hunter

[中文](https://github.com/Leaking/Hunter/blob/master/README_ch.md)

Hunter is a framework to develop android gradle plugin based on 
[ASM](https://asm.ow2.io/) and [Gradle Transform API](http://tools.android.com/tech-docs/new-build-system/transform-api).
It provides a set of useful, scalable plugins for android developers. You can use Hunter to develop more plugins
to monitor your app, enhance 3rd-dependency, enhance android framework.

Plugins based on Hunter support incremental and concurrent compile, so you don't need to
afraid of extra build time.

 + [OkHttp-Plugin](#okhttp-plugin): you can set a global [Interceptor](https://github.com/square/okhttp/wiki/Interceptors) / [Eventlistener](https://github.com/square/okhttp/wiki/Events) 
 for all your OkhttpClients(Even clients in 3rd-party library)
 + [Timing-Plugin](#timing-plugin): you can time all your ui-thread methods, and dump the block traces
 + [LogLine-Plugin](#logline-plugin): you can add a line number into every lines of your logcat
 + [Debug-Plugin](#debug-plugin): you can simply add a annotation to a certain method, and the method will print all parameters and costed time, return value(JakeWharton's [hugo](https://github.com/JakeWharton/hugo)
 achieves it with AspectJ, I achieve it with ASM)
 + More developing plugins can be found in [TODO](https://github.com/Leaking/Hunter/blob/master/TODO.md), MeanWhile, your idea is welcome


## OkHttp-Plugin

Maybe your project have serveral OkhttpClients，you need to add your custom Interceptor/EventListener/Dns 
to every OkhttpClients one by one. But some OkhttpClients come from 3rd library, and you can't add
 your custom Interceptor/EventListener/Dns to them. I have filed a [issue](https://github.com/square/okhttp/issues/4228) about this problem to Okhttp team.
 
OkHttp-Plugin can help you to achieve it, you can set a global Interceptor/EventListener/Dns to your all
OkhttpClients.


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
        classpath 'com.quinn.hunter:hunter-okhttp-plugin:0.9.1'
    }
}

apply plugin: 'hunter-okhttp'
    
```


```java

OkHttpHooker.installEventListenerFactory(CustomGlobalEventListener.FACTORY);
OkHttpHooker.installDns(new CustomGlobalDns());
OkHttpHooker.installInterceptor(new CustomGlobalInterceptor());
        
```

I recommend you to upgrade your okhttp to 3.11 or above, it almost costs nothing. If you want to use this plugin for okhttp below 3.11, please
read this wiki page: [wiki for okhttp-below-3.11](https://github.com/Leaking/Hunter/wiki/Okhttp-below-3.11)


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
        classpath 'com.quinn.hunter:hunter-timing-plugin:0.9.1'
    }
}

apply plugin: 'hunter-timing'
    
```

The Hunter-Timing has a internal default BlockHandler to process
the block messages, you can set your custom BlockHandler.

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
runVariant, its default value is ALWAYS, it means this plugin will perform 
in both debug build and release build. There are four available runVariant, 
DEBUG, RELEASE, ALWAYS, NEVER. 

whitelist, it means the plugin will only handle the classes in these packages, you can 
provide package with prefix name.

blacklist, it means the plugin will handle all classes except the classes in these packages, you can 
provide package with prefix name.

You'd better just provide whitelist or blacklist, if you provide both of them, blacklist is ignored.



## Debug-Plugin

It's a plugin similar to hugo but it's developed with ASM instead of AspectJ. Amd it has a
quicker compile speed.

Simply add @HunterDebug to your methods will print all parameters and costed time, return value.



```groovy

dependencies {
    implementation 'com.quinn.hunter:hunter-debug-library:0.9.4'
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
        classpath 'com.quinn.hunter:hunter-debug-plugin:0.9.4'
    }
}

apply plugin: 'hunter-debug'

```

For example:

```java


@HunterDebug
private String appendIntAndString(int a, String b) {
    SystemClock.sleep(100);
    return a + " " + b;
}

```


```xml 

MainActivity: ⇢ appendIntAndString[a="5", b="billions"]
              ⇠ appendIntAndString[0ms]="5 billions"

```

If you want to print the debug log with your custom logger. You can use `@HunterDebugImpl` instead of `@HunterDebug`, and 
install a custom HunterLoggerHandler to receive the log message, and send it to your custom logger.

```groovy 

HunterLoggerHandler.installLogImpl(new HunterLoggerHandler(){
    @Override
    protected void log(String tag, String msg) {
        //you can use your custom logger here
        YourLog.i(tag, msg);
    }
});
        
```

Logging works in both debug and release build mode, but you can specify certain mode or disable it.

```groovy

debugHunterExt {
    runVariant = 'DEBUG'  //'DEBUG', 'RELEASE', 'ALWAYS', 'NEVER', The 'ALWAYS' is default value
}

``` 


## LogLine-Plugin

This is a interesting tool/plugin, sometimes you may need line number to help you locate the logcat content,
especially, same logcat print in different lines in the same one java class.

This plugin can help you.


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