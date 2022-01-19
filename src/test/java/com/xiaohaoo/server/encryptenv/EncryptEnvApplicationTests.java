package com.xiaohaoo.server.encryptenv;

import com.xiaohaoo.server.encryptenv.encrypt.AesSecretResolve;
import com.xiaohaoo.server.encryptenv.encrypt.SecretResolve;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
@SpringBootConfiguration
public class EncryptEnvApplicationTests {

    @Autowired
    private Environment environment;

    @Test
    void contextLoads() {
        System.out.println(environment.getProperty("hello"));
    }

    @Test
    void encrypt() throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        SecretResolve aesSecretResolve = new AesSecretResolve(environment.getProperty("enc-key"));
        System.out.println(aesSecretResolve.encrypt("springs boot"));
    }


}