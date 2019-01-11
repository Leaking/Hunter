# Hunter

[中文](https://github.com/Leaking/Hunter/blob/master/README_ch.md)

Hunter is a framework to develop android gradle plugin based on 
[ASM](https://asm.ow2.io/) and [Gradle Transform API](http://tools.android.com/tech-docs/new-build-system/transform-api).
It provides a set of useful, scalable plugins for android developers. You can use Hunter to develop more plugins
to monitor your app, enhance 3rd-dependency, enhance android framework.

Plugins based on Hunter support incremental and concurrent compile, so you don't need to
afraid of extra build time.

 + [OkHttp-Plugin](https://github.com/Leaking/Hunter/blob/master/README_hunter_okhttp.md): you can set a global [Interceptor](https://github.com/square/okhttp/wiki/Interceptors) / [Eventlistener](https://github.com/square/okhttp/wiki/Events) 
 for all your OkhttpClients(Even clients in 3rd-party library)
 + [Timing-Plugin](https://github.com/Leaking/Hunter/blob/master/README_hunter_timing.md): you can time all your ui-thread methods, and dump the block traces
 + [Debug-Plugin](https://github.com/Leaking/Hunter/blob/master/README_hunter_debug.md): you can simply add a annotation to a certain method, and the method will print all parameters and costed time, return value(JakeWharton's [hugo](https://github.com/JakeWharton/hugo)
 achieves it with AspectJ, I achieve it with ASM)
 + [LogLine-Plugin](https://github.com/Leaking/Hunter/blob/master/README_hunter_logline.md): you can add a line number into every lines of your logcat
 + More developing plugins can be found in [TODO](https://github.com/Leaking/Hunter/blob/master/TODO.md), MeanWhile, your idea is welcome





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