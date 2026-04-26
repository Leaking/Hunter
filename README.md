# Hunter

[![Maven Central](https://img.shields.io/maven-central/v/cn.quinnchen.hunter/hunter-transform.svg?label=Maven%20Central)](https://central.sonatype.com/search?q=cn.quinnchen.hunter&sort=name)
[![Build](https://github.com/Leaking/Hunter/actions/workflows/build.yml/badge.svg)](https://github.com/Leaking/Hunter/actions/workflows/build.yml)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

[中文](https://github.com/Leaking/Hunter/blob/master/docs/README_ch.md)

Hunter is a framework to develop android gradle plugins based on
[ASM](https://asm.ow2.io/) and the modern AGP
[Instrumentation API](https://developer.android.com/reference/tools/gradle-api/8.7/com/android/build/api/instrumentation/AsmClassVisitorFactory).
It provides a set of useful, scalable plugins for android developers. You can use Hunter to develop more plugins
to monitor your app, enhance 3rd-party deps, or enhance the android framework. Hunter-based plugins inherit
AGP's incremental, parallel instrumentation pipeline, so you don't pay for extra build time.

## Latest Version

Hunter is published to [Maven Central](https://central.sonatype.com/search?q=cn.quinnchen.hunter&sort=name).

All libraries and plugins share the same version number. Replace `LATEST_VERSION_IN_README` in the per-plugin
READMEs with the latest version shown in the badge above.

```groovy
def hunterVersion = '1.3.1'
```

## Compatibility

| Hunter | Android Gradle Plugin                        | Gradle | ASM | Java |
|--------|----------------------------------------------|--------|-----|------|
| 1.3.x  | 7.4 – 8.7+                                   | 8.0+   | 9.7 | 17   |
| 1.2.x  | 4.x (legacy Transform API, removed in AGP 8) | 7.x    | 7.1 | 8    |

`1.3.0` is a **breaking** release: the legacy `Transform` API is gone (it was
removed by AGP 8.0 — see issues
[#67](https://github.com/Leaking/Hunter/issues/67),
[#63](https://github.com/Leaking/Hunter/issues/63),
[#34](https://github.com/Leaking/Hunter/issues/34)) and Hunter now wires
itself in via `AndroidComponentsExtension.onVariants` +
`AsmClassVisitorFactory`. ASM was bumped to 9.x to support Java 17 records
and sealed classes
([#60](https://github.com/Leaking/Hunter/issues/60),
[#66](https://github.com/Leaking/Hunter/issues/66)). The constructor
instrumentation bug from
[#48](https://github.com/Leaking/Hunter/issues/48) is also fixed: the timing
probe is now injected after the mandatory `super(...)` / `this(...)` call.

## Some useful plugins based on Hunter

 + [OkHttp-Plugin](https://github.com/Leaking/Hunter/blob/master/docs/README_hunter_okhttp.md): Hack Okhttp to set a global Interceptor/Eventlistener/Dns
 for all your OkhttpClients, even clients in 3rd-party library.
 + [Timing-Plugin](https://github.com/Leaking/Hunter/blob/master/docs/README_hunter_timing.md): you can time all your ui-thread methods, and dump the block traces with costed-time of every step, you also can consume the block detail in your way.
 + [Debug-Plugin](https://github.com/Leaking/Hunter/blob/master/docs/README_hunter_debug.md): you can simply add a annotation to a certain method, and the method will print all parameters and costed time, return value. It has many advantages over JakeWharton's [hugo](https://github.com/JakeWharton/hugo)
 + [LogLine-Plugin](https://github.com/Leaking/Hunter/blob/master/docs/README_hunter_logline.md): you can add a line number into every lines of your logcat

## TODO

More developing plugins can be found in [TODO](https://github.com/Leaking/Hunter/blob/master/docs/TODO.md), MeanWhile, your idea is welcome


## Developer API
    
If you want to use hunter to develop a gradle plugin to modify bytecode, please read [Wiki](https://github.com/Leaking/Hunter/wiki/Developer-API)


## Social network

Welcome to join:

Telegram group: https://t.me/joinchat/BalX-RxnM9ETca1iwgDaFQ

WeChat group:

<img src="https://github.com/Leaking/Hunter/blob/master/pics/contact_me_qr.png?raw=true" width="340" />

如果二维码过期了，加微信拉群 742223410



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
