# Hunter-Debug

[English](https://github.com/Leaking/Hunter/blob/master/README_hunter_debug.md)


Hunter-Debug 是基于 [Hunter](https://github.com/Leaking/Hunter) 开发的，灵感来自于 Jake Wharton 的 [Hugo](https://github.com/JakeWharton/hugo)，不过相比之下，Hunter-Debug 有以下优点：

|       | Hugo     | Hunter-Debug     |
| ---------- | :-----------:  | :-----------: |
| 支持 Kotlin     | no     | yes     |
| 自定义 logger     | no     | yes     |
| 对象输出 toString     | no     | yes     |
| 编译速度     | 一般     | 快     |


Hunter-Debug 是用 ASM 修改字节码，而非使用 AspectJ，所以自然会更快。

## 快速引入

在 build.gradle 中添加以下依赖：


```groovy

dependencies {
    implementation 'cn.quinnchen.hunter:Hunter-Debug-library:${LATEST_VERSION_IN_README}'
}

repositories {
    mavenCentral()
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'cn.quinnchen.hunter:Hunter-Debug-plugin:${LATEST_VERSION_IN_README}'
        classpath 'cn.quinnchen.hunter:hunter-transform:${LATEST_VERSION_IN_README}'
    }
}

apply plugin: 'Hunter-Debug'

```
在某个方法开头添加注解 `@HunterDebug`，就会打印方法参数，以及方法返回值，还有方法耗时。

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
如果你想将输出结果使用你们项目中自定义的 logger 处理，可以使用
`@HunterDebugImpl`，然后设置一个接受日志输出的`HunterLoggerHandler`：

```groovy

HunterLoggerHandler.installLogImpl(new HunterLoggerHandler(){
    @Override
    protected void log(String tag, String msg) {
        // 在这里使用你自己的日志输出方式
        YourLog.i(tag, msg);
    }
});

```

如果你只想在 debug 模式下使用该插件，则可以这样设置：

```groovy

debugHunterExt {
    runVariant = 'DEBUG'  // 'DEBUG', 'RELEASE', 'ALWAYS', 'NEVER', 默认为 'ALWAYS'
}

```

欢迎引入 Hunter-Debug 到你项目中使用，使用过程有遇到什么问题，或者有什么建议，都可以提 issue 或者邮件联系我，只要有空我会第一时间回应。

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
