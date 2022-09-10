# OkHttp-Plugin

[中文](https://github.com/Leaking/Hunter/blob/master/README_hunter_okhttp_ch.md)

Maybe your project have serveral OkHttpClients，you need to add your custom Interceptor/EventListener/Dns
to every OkHttpClients one by one. But some OkHttpClients come from 3rd library, and you can't add
 your custom Interceptor/EventListener/Dns to them. I have filed a [issue](https://github.com/square/okhttp/issues/4228) about this problem to Okhttp team.

OkHttp-Plugin can help you to achieve it, you can set a global Interceptor/EventListener/Dns to your all
OkHttpClients.

## How to use it

Add some lines to your build.gradle


```groovy

dependencies {
    implementation 'cn.quinnchen.hunter:hunter-okhttp-library:${LATEST_VERSION_IN_README}'
}

repositories {
    mavenCentral()
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'cn.quinnchen.hunter:hunter-okhttp-plugin:${LATEST_VERSION_IN_README}'
        classpath 'cn.quinnchen.hunter:hunter-transform:${LATEST_VERSION_IN_README}'
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
