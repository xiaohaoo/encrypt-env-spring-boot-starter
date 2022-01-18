package com.xiaohaoo.server.encryptenv.encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author xiaohao
 * @version 1.0
 * @date 2022/1/18 8:44 PM
 */
public interface SecretResolve {

    /**
     * 加密方法
     *
     * @param content
     * @return
     */
    String encrypt(String content) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
        IllegalBlockSizeException, BadPaddingException;

    /**
     * 解密方法
     *
     * @param content
     * @return
     */
    String decrypt(String content) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException,
        IllegalBlockSizeException, BadPaddingException;
}
