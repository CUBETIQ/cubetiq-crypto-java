package com.cubetiqs.crypto.util;

import com.cubetiqs.crypto.CryptoProvider;
import com.cubetiqs.crypto.core.CryptoUtil;

import javax.crypto.KeyGenerator;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public final class FunctionUtil {
    public static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

    public static String encodeToBase64(String text) {
        if (text == null) {
            return null;
        }
        byte[] bytes = encodeBase64(text.getBytes(DEFAULT_ENCODING));
        return new String(bytes, DEFAULT_ENCODING);
    }

    public static byte[] encodeBase64(byte[] bytes) {
        return Base64.getEncoder().encode(bytes);
    }

    public static byte[] decodeBase64(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }

    public static byte[] decodeBase64FromString(String str) {
        if (str == null) {
            return null;
        }
        return decodeBase64(str.getBytes(DEFAULT_ENCODING));
    }

    public static String encrypt(String data, CryptoProvider.CryptoOptions options) {
        byte[] bytes = data.getBytes(DEFAULT_ENCODING);
        byte[] encrypted = CryptoUtil.encrypt(bytes, options);

        if (encrypted == null) {
            return null;
        }

        return new String(encodeBase64(encrypted), DEFAULT_ENCODING);
    }

    public static String decrypt(String encrypted, CryptoProvider.CryptoOptions options) {
        byte[] bytes = decodeBase64FromString(encrypted);
        byte[] data = CryptoUtil.decrypt(bytes, options);

        if (data == null) {
            return null;
        }

        return new String(data, DEFAULT_ENCODING);
    }

    public static String generateRandomString(int length) {
        byte[] iv = new byte[length];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return Base64.getEncoder().encodeToString(iv).substring(0, length);
    }

    public static String generateKey(int length, String algorithm, Integer keySize) {
        try {
            if (algorithm == null) {
                algorithm = "AES";
            }
            if (keySize == null) {
                keySize = 256;
            }
            if (length == 0) {
                length = 32;
            }
            Key key;
            SecureRandom random = new SecureRandom();
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            keyGenerator.init(keySize, random);
            key = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(key.getEncoded()).substring(0, length);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
