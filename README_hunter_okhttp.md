## OkHttp-Plugin

[中文](https://github.com/Leaking/Hunter/blob/master/README_hunter_okhttp_ch.md)

Maybe your project have serveral OkhttpClients，you need to add your custom Interceptor/EventListener/Dns 
to every OkhttpClients one by one. But some OkhttpClients come from 3rd library, and you can't add
 your custom Interceptor/EventListener/Dns to them. I have filed a [issue](https://github.com/square/okhttp/issues/4228) about this problem to Okhttp team.
 
OkHttp-Plugin can help you to achieve it, you can set a global Interceptor/EventListener/Dns to your all
OkhttpClients.

## How to use it

Add some lines to your build.gradle


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
        classpath 'com.quinn.hunter:hunter-okhttp-plugin:1.0.0'
        classpath 'com.quinn.hunter:hunter-transform:1.0.0'
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
