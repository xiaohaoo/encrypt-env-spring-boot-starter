# encrypt-env-spring-boot-starter

# 使用场景

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
    # or username: "{enc:twGrVRJ60ACdcY0JFP/gbQ==}"
    password: enc:KsaeJD9ahUNJtR8yR8koJw==
    # or password: "{enc:KsaeJD9ahUNJtR8yR8koJw==}"
    url: enc:fas0AsdsfafJFP/adaewff
    # or url: "abc{enc:fas0AsdsfafJFP/adaewff}cba"
```

# 使用方式

1. 引入

```groovy
implementation 'com.xiaohaoo:encrypt-env-spring-boot-starter:1.0'
```

```xml

<dependency>
    <groupId>com.xiaohaoo</groupId>
    <artifactId>encrypt-env-spring-boot-starter</artifactId>
    <version>1.0</version>
</dependency>
```

2. 配置

对配置文件中的敏感数据进行加密后添加前缀 **enc:** 或者使用 **{enc:密文}** 即可。

```yml
spring:
  datasource:
    username: enc:twGrVRJ60ACdcY0JFP/gbQ==
    # or username: "{enc:twGrVRJ60ACdcY0JFP/gbQ==}"
    password: enc:KsaeJD9ahUNJtR8yR8koJw==
    # or password: "{enc:KsaeJD9ahUNJtR8yR8koJw==}"
    url: enc:fas0AsdsfafJFP/adaewff
    # or url: "abc{enc:fas0AsdsfafJFP/adaewff}cba"
```

3. 获取加密字符串

```java
public class EncryptEnvApplicationTests {

    @Test
    void encrypt() {
        // 对称加密秘钥
        SecretHandler aesSecretResolve = new AesSecretHandler("xiaohaoo");
        // 要加密的字符串
        System.out.println(aesSecretResolve.encrypt("spring boot"));
    }
}
```

# 加密方法

计划支持：

- [x] AES
- [ ] DES
- [ ] RSA