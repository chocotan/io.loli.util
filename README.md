###io.loli.util
============


[![Build Status](https://drone.io/github.com/chocotan/io.loli.util/status.png)](https://drone.io/github.com/chocotan/io.loli.util/latest)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.loli/util/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/io.loli/util)


这是我个人使用的工具类
已经发布到maven中央仓库中


```xml
<depencency>
  <groupId>io.loli</groupId>
  <artifactId>util</artifactId>
  <version>0.0.13</version>
</dependency>
```

如需使用SNAPSHOT版本，则要将sonatype snapshot repo添加到pom.xml中
```xml
<repositories>
    <repository>
      <id>snapshots-repo</id>
      <url>https://oss.sonatype.org/content/repositories/snapshots</url>
      <releases>
        <enabled>false</enabled>
      </releases>
      <snapshots>
        <enabled>true</enabled>
      </snapshots>
    </repository>
  </repositories>
```


###功能
1. mail包已经被移除， 参见io.loli.util.mail
2. string包中是字符串相关的工具，计算MD5值、短地址等
