# Hunter-SingleClick

防止快速点击的小插件。默认给所有OnClickListener的onclick方法加限制；可使用@NoSingleClick解除限制。

## 快速引入

在build.gradle中添加以下依赖


```groovy

dependencies {
    implementation project(path: ':hunter-single-click-library')
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
        classpath 'com.jun.hunter:hunter-single-click-plugin:1.1.1'
        classpath 'com.quinn.hunter:hunter-transform:0.9.3'
    }
}


apply plugin: 'hunter-single-click'

```

设置按钮的点击的冷却时间

```java

public class SingleClickExampleApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ClickUtils.setFrozenTimeMillis(3000);
    }
}

```
