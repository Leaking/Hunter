# Hunter

Hunter is a framework to develop android gradle plugin based on 
[ASM](https://asm.ow2.io/) and [Gradle Transform API](http://tools.android.com/tech-docs/new-build-system/transform-api).
It provides a set of useful, salable plugins for android developers.

 + [Timing-Plugin](#timing-plugin): you can time all you ui-thread method, and dump the block traces
 + [OkHttp-Plugin](#okhttp-plugin): you can set a global [Interceptor](https://github.com/square/okhttp/wiki/Interceptors) or [Eventlistener](https://github.com/square/okhttp/wiki/Events) 
 for all your OkhttpClients(Okhttp does not provide interface to set a global Interceptor or a global EventListener)
 + [LogLine-Plugin](#logline-plugin): you can add a line number into every lines of your logcat
 + [Debug-Plugin](#debug-plugin): you can simply add a annotation to a certain method, and the method will print all parameters and costed time, return value(JakeWharton's [hugo](https://github.com/JakeWharton/hugo)
 achieves it with AspectJ, I achieve it with ASM)

## Timing-Plugin


## OkHttp-Plugin


## LogLine-Plugin


## Debug-Plugin


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