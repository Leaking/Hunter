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
    implementation 'com.quinn.hunter:hunter-okhttp-library:1.2.0'
}

repositories {
    maven {
        name = "GithubPackages"
        url = uri("https://maven.pkg.github.com/Leaking/Hunter")
        credentials {
            username = 'Leaking'
            password = '\u0067\u0068\u0070\u005f\u0072\u006a\u0041\u004b\u0037\u006d\u0048\u0047\u006b\u0031\u0045\u0039\u0063\u0048\u0044\u0076\u004f\u0039\u0078\u006f\u0046\u0048\u004d\u0049\u0032\u006a\u0047\u0057\u0047\u0068\u0032\u0036\u0065\u0075\u0043\u006b'
        }
    }
}

buildscript {
    repositories {
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/Leaking/Hunter")
            credentials {
                username = 'Leaking'
                password = '\u0067\u0068\u0070\u005f\u0072\u006a\u0041\u004b\u0037\u006d\u0048\u0047\u006b\u0031\u0045\u0039\u0063\u0048\u0044\u0076\u004f\u0039\u0078\u006f\u0046\u0048\u004d\u0049\u0032\u006a\u0047\u0057\u0047\u0068\u0032\u0036\u0065\u0075\u0043\u006b'
            }
        }
        google()
    }
    dependencies {
        classpath 'com.quinn.hunter:hunter-okhttp-plugin:1.2.0'
        classpath 'com.quinn.hunter:hunter-transform:1.2.1'
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
