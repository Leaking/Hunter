# Hunter-SingleClick

Plugin to prevent quick clicks. By default, all OnClickListener's onclick methods are restricted; you can use @NoSingleClick to remove restrictions.

## How to use it

Add some lines to your build.gradle


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

Set the cooldown for button click

```java

public class SingleClickExampleApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ClickUtils.setFrozenTimeMillis(3000);
    }
}

```
