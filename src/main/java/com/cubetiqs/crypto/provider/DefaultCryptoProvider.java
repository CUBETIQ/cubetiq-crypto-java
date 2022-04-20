package com.cubetiqs.crypto.provider;

import com.cubetiqs.crypto.CryptoProvider;
import com.cubetiqs.crypto.core.CryptoUtil;
import com.cubetiqs.crypto.core.CryptoUtil.Options;

/**
 * Default implementation of CryptoProvider.
 * Using Java Cryptography Extension (JCE) within KEY and IV.
 *
 * @author sombochea
 * @since 1.0
 */
public class DefaultCryptoProvider implements CryptoProvider {
    private final CryptoOptions options;

    /**
     * Create new instance of Default Crypto Provider.
     *
     * @param key String | Key (32) (Base64 encode) for encryption.
     * @param iv  String | IV (16) (Base64 encode) for encryption.
     */
    public DefaultCryptoProvider(String key, String iv) {
        assert key != null;
        this.options = new Options(key, iv);
    }

    /**
     * Create new instance of Default Crypto Provider.
     *
     * @param key String | Key (32) (Base64 encode) for encryption.
     */
    public DefaultCryptoProvider(String key) {
        this(key, null);
    }

    /**
     * Create new instance for CryptoProvider with Default Crypto Provider.
     *
     * @param key String | Key (32) (Base64 encode) for encryption.
     * @param iv  String | IV (16) (Base64 encode) for encryption.
     * @return CryptoProvider
     */
    public static CryptoProvider newInstance(String key, String iv) {
        return new DefaultCryptoProvider(key, iv);
    }

    /**
     * Create new instance for CryptoProvider with Default Crypto Provider.
     *
     * @param key String | Key (32) (Base64 encode) for encryption.
     * @return CryptoProvider
     */
    public static CryptoProvider newInstance(String key) {
        return newInstance(key, null);
    }

    /**
     * Create default instance of Default Crypto Provider.
     *
     * @param options CryptoOptions
     */
    public DefaultCryptoProvider(CryptoOptions options) {
        assert options != null;
        this.options = options;
    }

    @Override
    public CryptoOptions getOptions() {
        return options;
    }

    /**
     * Get Key for encryption.
     *
     * @return String | Base64 encoded key.
     */
    public String getKey() {
        return getOptions().getKey();
    }

    /**
     * Get Iv for encryption.
     *
     * @return String | Base64 encoded iv.
     */
    public String getIv() {
        return getOptions().getIv();
    }

    @Override
    public byte[] decrypt(byte[] data) {
        return CryptoUtil.decrypt(data, getOptions());
    }

    @Override
    public byte[] encrypt(byte[] encrypted) {
        return CryptoUtil.encrypt(encrypted, getOptions());
    }
}