# Hunter

[中文](https://github.com/Leaking/Hunter/blob/master/docs/README_ch.md)

Hunter is a framework to develop android gradle plugin based on 
[ASM](https://asm.ow2.io/) and [Gradle Transform API](http://tools.android.com/tech-docs/new-build-system/transform-api).
It provides a set of useful, scalable plugins for android developers. You can use Hunter to develop more plugins
to monitor your app, enhance 3rd-dependency, enhance android framework. Plugins based on Hunter support incremental and concurrent compile, so you don't need to
be afraid of extra build time.

## Latest Version

Recently, Hunter is published to [MavenCentral](https://repo1.maven.org/maven2/cn/quinnchen/hunter/).

Every libraies / plugins use same latest version, replace LATEST_VERSION_IN_README in others README with this latest version

```groovy

1.2.3

```

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
