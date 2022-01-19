## encrypt-env-spring-boot-starter

### 使用场景

在Spring Boot中，配置参数使用明文的方式造成敏感信息泄露，例如：

```yml
spring:
  datasource:
    username: admin
    password: admin
    url: jdbc:mysql://127.0.0.1:3306
```

现在，可以使用encrypt-env-spring-boot-starter可以对配置信息进行脱敏处理，避免了因为不安全的因素将敏感数据泄露。遵循spring-boot-starter的通用starter配置方式，开箱即用。

```yml
spring:
  datasource:
    username: enc:twGrVRJ60ACdcY0JFP/gbQ==
    password: enc:KsaeJD9ahUNJtR8yR8koJw==
    url: enc:fas0AsdsfafJFP/adaewff
```

### 使用方式

1. 引入

    ```gradle
    implementation 'com.xiaohaoo:encrypt-env-spring-boot-starter:1.0'
    ```

    ```maven
    <dependency>
        <groupId>com.xiaohaoo</groupId>
        <artifactId>encrypt-env-spring-boot-starter</artifactId>
        <version>1.0</version>
    </dependency>
    ```

2. 配置

   对配置文件中的敏感数据进行加密后添加前缀enc:即可。

    ```yml
    spring:
      datasource:
        username: enc:twGrVRJ60ACdcY0JFP/gbQ==
        password: enc:KsaeJD9ahUNJtR8yR8koJw==
        url: enc:fas0AsdsfafJFP/adaewff
    ```

### 加密方法

计划支持：
- [x] AES
- [ ] DES
- [ ] RSA
- [ ] ECC



