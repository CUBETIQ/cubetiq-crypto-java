package com.cubetiqs.crypto;

import com.cubetiqs.crypto.core.CryptoUtil;
import com.cubetiqs.crypto.provider.DefaultCryptoProvider;
import com.cubetiqs.crypto.util.FunctionUtil;

import java.security.Key;

/**
 * Crypto, provides the methods to encrypt and decrypt the data with implemented for any provider
 *
 * @author sombochea
 * @since 1.0
 */
public class Crypto {
    private final CryptoProvider provider;

    public Crypto(final CryptoProvider provider) {
        this.provider = provider;
    }

    public String decrypt(String encrypted) {
        return provider.decrypt(encrypted);
    }

    public String encrypt(String text) {
        return provider.encrypt(text);
    }

    public byte[] encrypt(byte[] data) {
        return provider.encrypt(data);
    }

    public byte[] decrypt(byte[] encrypted) {
        return provider.decrypt(encrypted);
    }

    private static Crypto instance;

    public static Crypto newInstance(CryptoProvider provider) {
        return new Crypto(provider);
    }

    public static Crypto createInstance(CryptoProvider provider) {
        if (instance == null) {
            instance = newInstance(provider);
        }
        return instance;
    }

    public static Crypto newDefaultInstance(String keyText, String ivText) {
        return newInstance(DefaultCryptoProvider.newInstance(encodeToBase64(keyText), encodeToBase64(ivText)));
    }

    public static Crypto defaultInstance(String key, String iv) {
        if (instance == null) {
            return createInstance(DefaultCryptoProvider.newInstance(key, iv));
        }
        return instance;
    }

    public static void resetInstance() {
        instance = null;
    }

    public static String encodeToBase64(String text) {
        return FunctionUtil.encodeToBase64(text);
    }

    public static String createKey() {
        return generateKey(32, null, null);
    }

    public static String createIV() {
        return CryptoUtil.createRandomString(16);
    }

    public static String generateKey(int length, String algorithm, Integer keySize) {
        return CryptoUtil.createKey(length, algorithm, keySize);
    }

    public static Key secretKey(String algorithm, Integer keySize) {
        return FunctionUtil.generateKey(algorithm, keySize);
    }
}
