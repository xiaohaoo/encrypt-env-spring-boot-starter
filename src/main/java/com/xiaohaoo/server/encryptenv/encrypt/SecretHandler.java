/*
 * Copyright (c) 2022 xiaohao & xiaohaoo
 */

package com.xiaohaoo.server.encryptenv.encrypt;

/**
 * @author xiaohao
 * @version 1.0
 * @date 2022/1/18 8:44 PM
 */
public interface SecretHandler {


    /**
     * 加密字符串方法
     * @param text 待加密字符串
     * @return 加密后的字符串
     */
    String encrypt(String text);

    /**
     * 解密字符串方法
     * @param secret 加密的字符串
     * @return 解密后的字符串
     */
    String decrypt(String secret);
}
