package com.diyiliu.support.util;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * Description: PasswordHelper
 * Author: DIYILIU
 * Update: 2017-11-24 09:52
 */

public class PasswordHelper {
    private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

    private String hashAlgorithmName = "md5";
    private int hashIterations = 2;

    public String encryptPassword(String username, String password, String salt){
        return new SimpleHash(
                hashAlgorithmName,
                password,
                ByteSource.Util.bytes(salt),
                hashIterations).toHex();
    }

    public void setHashAlgorithmName(String hashAlgorithmName) {
        this.hashAlgorithmName = hashAlgorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }
}
