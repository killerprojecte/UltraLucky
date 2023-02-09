# **UltraLucky**

- 下一代幸运系统开源解决方案
- 同类付费插件**Archeology**的竞争者

## 从UltraLucky开始

**UltraLucky** 支持自主开发模块

需要作为外部插件实现**Module**接口

将自定义**Module**向**ModuleManager**注册即可

为了配置功能的正确运行 需要将您的配置文件复制到**UltraLucky**目录下

## 额外协议

- 本内容根据GPLv3第7条发布

```
1. 禁止任何人以任何方式对UltraLucky其可执行内容或源码进行付费分发
2. 任何人对UltraLucky源码进行分发时必须附上此仓库链接
3. UltraLucky开发者有权力对违规使用(诈骗行为, 违法行为等)UltraLucky的用户撤销使用授权
```

## 添加至依赖

- Maven

```xml

<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <artifactId>example</artifactId>
    <repositories>
        <repository>
            <id>fastmcmirror-repo</id>
            <url>https://repo.fastmcmirror.org/content/repositories/releases/</url>
        </repository>
    </repositories>

    <dependencies>
        <dependency>
            <groupId>dev.rgbmc</groupId>
            <artifactId>UltraLucky</artifactId>
            <version>1.0.0</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>
</project>
```

- Gradle Groovy

```groovy
repositories {
    maven {
        name = 'fastmcmirror-repo'
        url = "https://repo.fastmcmirror.org/content/repositories/releases/"
    }
}

dependencies {
    compileOnly 'dev.rgbmc:UltraLucky:1.0.0'
}
```

- Gradle Kotlin

```kotlin
repositories {
    maven {
        name = "fastmcmirror-repo"
        url = uri("https://repo.fastmcmirror.org/content/repositories/releases/")
    }
}

dependencies {
    compileOnly("dev.rgbmc:UltraLucky:1.0.0")
}
```
