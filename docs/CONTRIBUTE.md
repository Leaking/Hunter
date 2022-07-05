# 开发

## 本地调试

我们会优先使用mavenLocal再使用mavenCentral，所以我们只需要发一个最新版本到mavenLocal就可以进行测试。验证mavenLocal里有什么版本的包，在Mac下可以查看这个目录


### 发布mavenLocal

修改任意library / plugin项目，然后执行

```shell
./gradlew build
./gradlew publishToMavenLocal
```

### 查看mavenLocal

在本地确认有你刚发布的版本（如果里面的版本比较混乱，建议删除该目录后再重新发布一次到mavenLocal）

```shell
~/.m2/repository/cn/quinnchen/hunter
```

### 运行项目中对应的exmaple项目

运行任意exmpale项目


## 发布组件

### 修改版本号

```
// 修改根目录下的gradle.properties里的VERSION_NAME，以及buildSrc/src/main/java/Dependencies.kt的Versions.hunter_common_version字段。
```

### 发布
```
./gradlew build
./gradlew publishMavenPublicationToMavenCentralRepository
```