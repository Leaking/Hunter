# Hunter-Debug

[中文](https://github.com/Leaking/Hunter/blob/master/README_hunter_debug_ch.md)


Hunter-debug is a gradle plugin based on [Hunter](https://github.com/Leaking/Hunter), It's inspired by JakeWharton's [hugo](https://github.com/JakeWharton/hugo), But Hunter-debug
has some advantages over hugo.

|       | Hugo     | Hunter-Debug     |
| ---------- | :-----------:  | :-----------: |
| support kotlin     | no     | yes     |
| custom logger     | no     | yes     |
| object toString     | no     | yes     |
| compile speed     | normal     | fast     |



Hunter-Debug's developed with ASM instead of AspectJ so that it has a faster compile speed.

## How to use it

Add some lines to your build.gradle

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
Simply add @HunterDebug to your methods will print all parameters and costed time, return value.

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
(You can use both `@HunterDebug` and `@HunterDebugImpl` at the same time)

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