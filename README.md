# Hunter (Unpublish)

Hunter is a framework to develop android gradle plugin based on 
[ASM](https://asm.ow2.io/) and [Gradle Transform API](http://tools.android.com/tech-docs/new-build-system/transform-api).
It provides a set of useful, salable plugins for android developers.

 + [Timing-Plugin](#timing-plugin): you can time all your ui-thread methods, and dump the block traces
 + [OkHttp-Plugin](#okhttp-plugin): you can set a global [Interceptor](https://github.com/square/okhttp/wiki/Interceptors) / [Eventlistener](https://github.com/square/okhttp/wiki/Events) 
 for all your OkhttpClients(Okhttp does not provide interface to set a global Interceptor/EventListener)
 + [LogLine-Plugin](#logline-plugin): you can add a line number into every lines of your logcat
 + [Debug-Plugin](#debug-plugin): you can simply add a annotation to a certain method, and the method will print all parameters and costed time, return value(JakeWharton's [hugo](https://github.com/JakeWharton/hugo)
 achieves it with AspectJ, I achieve it with ASM)
 + More developing plugins can be found in [TODO](https://github.com/Leaking/Hunter/blob/master/TODO.md), MeanWhile, your idea is welcome.

## Timing-Plugin




```groovy


dependencies {
    implementation 'com.quinn.hunter:hunter-timing-library::1.0.0'
}

repositories {
    maven {
        url 'https://dl.bintray.com/leaking/maven'
        }
}

buildscript {
    repositories {
        maven {
            url 'https://dl.bintray.com/leaking/maven'
            }
    }
    dependencies {
        classpath 'com.quinn.hunter:hunter-timing-plugin:1.0.0'
    }
}

apply plugin: 'hunter-timing'
    
```

The Hunter-Timing has a internal default BlockHandler to process
the block messages, you can set your custom BlockHandler.

```java

BlockManager.installBlockManager(new RankingBlockHandler());

```


## OkHttp-Plugin

Maybe your project have serveral OkhttpClients，you need to add your custom Interceptor/EventListener/Dns 
to every OkhttpClients one by one. But some OkhttpClients come from 3rd library, and you can't add
 your custom Interceptor/EventListener/Dns to them. I have filed a [issue](https://github.com/square/okhttp/issues/4228) to Okhttp team.
 
OkHttp-Plugin can help you to achieve it, you can set a global Interceptor/EventListener/Dns to your all
OkhttpClients.


```groovy


dependencies {
    implementation 'com.quinn.hunter:hunter-okhttp-library::1.0.0'
}

repositories {
    maven {
        url 'https://dl.bintray.com/leaking/maven'
        }
}

buildscript {
    repositories {
        maven {
            url 'https://dl.bintray.com/leaking/maven'
            }
    }
    dependencies {
        classpath 'com.quinn.hunter:hunter-okhttp-plugin:1.0.0'
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

It's a plugin similar to hugo but it's developed with ASM instead of AspectJ. Amd it has a
quicker compile speed.

Simply add @HunterDebug to your methods will print all parameters and costed time, return value.



```groovy

dependencies {
    implementation 'com.quinn.hunter:hunter-debug-library::1.0.0'
}

repositories {
    maven {
        url 'https://dl.bintray.com/leaking/maven'
        }
}

buildscript {
    repositories {
        maven {
            url 'https://dl.bintray.com/leaking/maven'
            }
    }
    dependencies {
        classpath 'com.quinn.hunter:hunter-debug-plugin:1.0.0'
    }
}

apply plugin: 'hunter-debug'
    
    


```

Logging works in both debug and release build mode, but you can specify certain mode or disable it.

```groovy

debugHunterExt {
    runVariant = 'DEBUG'
}

``` 

There are four available runVariant, 'DEBUG', 'RELEASE', 'ALWAYS', 'NEVER', The 'ALWAYS' is default value.


## LogLine-Plugin

This is a interesting tool/plugin, sometimes you may need line number to help you locate the logcat content,
especially, same logcat print in different lines in the same one java class.

This plugin can help you.


```groovy

dependencies {
    implementation 'com.quinn.hunter:hunter-linelog-library::1.0.0'
}

repositories {
    maven {
        url 'https://dl.bintray.com/leaking/maven'
        }
}

buildscript {
    repositories {
        maven {
            url 'https://dl.bintray.com/leaking/maven'
            }
    }
    dependencies {
        classpath 'com.quinn.hunter:hunter-linelog-plugin:1.0.0'
    }
}

apply plugin: 'hunter-linelog'
    
```

You logcat will be inserted line number automatically.


For example, the following line is which you write to try to print logcat

```java

Log.i("MainActivity", "onCreate");

```
The 'tranformed' logcat

```xml

I/MainActivity[21]: onCreate

```  



## Developer API



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