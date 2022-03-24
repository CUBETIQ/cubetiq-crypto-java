package com.cubetiqs.crypto;

import com.cubetiqs.crypto.provider.DefaultCryptoProvider;

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

    public static Crypto createInstance(CryptoProvider provider) {
        return new Crypto(provider);
    }

    public static Crypto newInstance(CryptoProvider provider) {
        if (instance == null) {
            instance = createInstance(provider);
        }
        return instance;
    }

    public static Crypto defaultInstance(String key, String iv) {
        if (instance == null) {
            instance = createInstance(new DefaultCryptoProvider(key, iv));
        }
        return instance;
    }
}
