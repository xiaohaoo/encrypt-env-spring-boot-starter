/*
 * Copyright (c) 2022 xiaohao & xiaohaoo
 */

package com.xiaohaoo.server.encryptenv.encrypt;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

/**
 * @author xiaohao
 * @version 1.0
 * @date 2022/1/18 8:46 PM
 */
public class AesSecretHandler implements SecretHandler {

    private final Cipher cipher;
    private final SecretKeySpec secretKeySpec;

    public AesSecretHandler(String key) {
        if (Objects.isNull(key) || "".equals(key)) {
            key = "xiaohaoo";
        }
        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256, secureRandom);
            this.secretKeySpec = new SecretKeySpec(keyGenerator.generateKey().getEncoded(), "AES");
            this.cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String encrypt(String encrypt) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, this.secretKeySpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(encrypt.getBytes()));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decrypt(String decrypt) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, this.secretKeySpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(decrypt)));
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
