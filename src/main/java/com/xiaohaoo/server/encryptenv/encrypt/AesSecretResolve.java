package com.xiaohaoo.server.encryptenv.encrypt;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author xiaohao
 * @version 1.0
 * @date 2022/1/18 8:46 PM
 */
public class AesSecretResolve implements SecretResolve {

    public AesSecretResolve(final String key) throws NoSuchPaddingException, NoSuchAlgorithmException {
        assert key != null;
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(key.getBytes());
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256, secureRandom);
        this.secretKeySpec = new SecretKeySpec(keyGenerator.generateKey().getEncoded(), "AES");
        this.cipher = Cipher.getInstance("AES");
    }


    private final Cipher cipher;

    private final SecretKeySpec secretKeySpec;


    @Override
    public String encrypt(String encrypt) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.ENCRYPT_MODE, this.secretKeySpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(encrypt.getBytes()));
    }

    @Override
    public String decrypt(String decrypt) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE, this.secretKeySpec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(decrypt)));
    }

}
