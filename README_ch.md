# Hunter

相关技术文章： [一起玩转Android项目中的字节码](http://quinnchen.cn/2018/09/13/2018-09-13-asm-transform/)

Hunter是这么一个框架，帮你快速开发插件，在编译过程中修改字节码，它底层基于[ASM](https://asm.ow2.io/) 和 [Gradle Transform API](http://tools.android.com/tech-docs/new-build-system/transform-api)
实现。在这个框架基础上，我尝试开发了几款实用的插件。你也可以用Hunter开发自己的插件，诸如实现App性能监控（UI，网络等等），加强或修改第三方库以满足你的需求，甚至可以加强、修改Android framework的接口。Hunter本身支持增量、并发编译，所以不用担心使用这一系列插件会增加编译时间。


## 基于Hunter的插件

 + [OkHttp-Plugin](https://github.com/Leaking/Hunter/blob/master/README_hunter_okhttp_ch.md): 通过修改字节码的方式hack掉okhttp，为你的应用所有的OkhttpClient设置全局 [Interceptor](https://github.com/square/okhttp/wiki/Interceptors) / [Eventlistener](https://github.com/square/okhttp/wiki/Events) 
(包括第三方依赖里的OkhttpClient)
 + [Timing-Plugin](https://github.com/Leaking/Hunter/blob/master/README_hunter_timing_ch.md): 帮你监控所有UI线程的执行耗时，并且提供了算法，帮你打印出一个带有每步耗时的堆栈，统计卡顿方法分布，并且提供接口让你选择自己的方式来处理卡顿信息。
 + [Debug-Plugin](https://github.com/Leaking/Hunter/blob/master/README_hunter_debug_ch.md): 只要为指定方法加上某个annotation，就可以帮你打印出这个方法所有输入参数的值，以及返回值，执行时间。这个插件相比JakeWharton的[hugo](https://github.com/JakeWharton/hugo)有很多优点：支持koltin，支持自定义logger，不影响断点调试，支持打印对象toString内容，编译速度更快
 + [LogLine-Plugin](https://github.com/Leaking/Hunter/blob/master/README_hunter_logline_ch.md): 为你的日志加上行号


## TODO 

你可以在这里查看我想继续开发的一些插件 [TODO](https://github.com/Leaking/Hunter/blob/master/TODO.md)，另外，欢迎你提供你宝贵的idea

## Developer API
    
如果想开发基于hunter的gradle插件来修改项目中的字节码，可以参考[Wiki](https://github.com/Leaking/Hunter/wiki/Developer-API)
   

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
