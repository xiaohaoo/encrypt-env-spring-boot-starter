package com.xiaohaoo.server.encryptenv.encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author xiaohao
 * @version 1.0
 * @date 2022/1/18 8:46 PM
 */
public class AesSecretResolve implements SecretResolve {

    public AesSecretResolve(String key) throws NoSuchPaddingException, NoSuchAlgorithmException {
        assert key != null && key.length() == 16;
        this.secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
        this.cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

    }


    private final Cipher cipher;

    private final SecretKeySpec secretKeySpec;


    @Override
    public String encrypt(String encrypt) throws
        InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.ENCRYPT_MODE, this.secretKeySpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(encrypt.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public String decrypt(String decrypt) throws
        InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        cipher.init(Cipher.DECRYPT_MODE, this.secretKeySpec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(decrypt)));
    }

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException,
        IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        AesSecretResolve aesSecretResolve = new AesSecretResolve("xiaohaoxiaohaooo");
        System.out.println(aesSecretResolve.encrypt("md3jrccyza0R7gsY14ZFyw=="));
    }

}
