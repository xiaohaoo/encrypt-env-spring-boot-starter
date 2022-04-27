/*
 * Copyright (c) 2022 xiaohao & xiaohaoo
 */

package com.xiaohaoo.server.encryptenv;

import com.xiaohaoo.server.encryptenv.encrypt.AesSecretHandler;
import com.xiaohaoo.server.encryptenv.encrypt.SecretHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

@SpringBootTest
@SpringBootConfiguration
public class EncryptEnvApplicationTests {

    @Autowired
    private Environment environment;

    @Test
    void contextLoads() {
        System.out.println(environment.getProperty("hello"));
        System.out.println(environment.getProperty("hello-pattern"));
    }

    @Test
    void encrypt() {
        SecretHandler aesSecretResolve = new AesSecretHandler("xiaohaoo");
        System.out.println(aesSecretResolve.encrypt("spring boot"));
    }
}