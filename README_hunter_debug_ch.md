# Hunter-Debug

[English](https://github.com/Leaking/Hunter/blob/master/README_hunter_debug.md)


Hunter-debug是基于[Hunter](https://github.com/Leaking/Hunter)开发的，灵感来自于JakeWharton's [hugo](https://github.com/JakeWharton/hugo)，不过相比之下，Hunter-debug有以下优点

|       | Hugo     | Hunter-Debug     |
| ---------- | :-----------:  | :-----------: |
| 支持kotlin     | no     | yes     |
| 自定义logger     | no     | yes     |
| 对象输出toString     | no     | yes     |
| 编译速度     | 一般     | 快     |


Hunter-Debug是用ASM修改字节码，而非使用AspectJ，所以自然会更快。

## 快速引入

在build.gradle中添加以下依赖


```groovy

dependencies {
    implementation 'com.quinn.hunter:hunter-debug-library:0.9.6'
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
        classpath 'com.quinn.hunter:hunter-debug-plugin:0.9.6'
        classpath 'com.quinn.hunter:hunter-transform:0.9.3'
    }
}

apply plugin: 'hunter-debug'

```
在某个方法开头添加注解@HunterDebug，就会打印方法参数，以及方法返回值，还有方法耗时。

比如

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
如果你想将输出结果使用你们项目中自定义的logger处理，可以使用
`@HunterDebugImpl`，然后设置一个接受日志输出的`HunterLoggerHandler`

```groovy 

HunterLoggerHandler.installLogImpl(new HunterLoggerHandler(){
    @Override
    protected void log(String tag, String msg) {
        //you can use your custom logger here
        YourLog.i(tag, msg);
    }
});
        
```

如果你只想在debug模式下使用该插件，则可以这样设置，

```groovy

debugHunterExt {
    runVariant = 'DEBUG'  //'DEBUG', 'RELEASE', 'ALWAYS', 'NEVER', The 'ALWAYS' is default value
}

``` 

欢迎引入Hunter-Debug到你项目中使用，使用过程有遇到什么问题，或者有什么建议，都可以提issue或者邮件联系我，只要有空我会第一时间回应。

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