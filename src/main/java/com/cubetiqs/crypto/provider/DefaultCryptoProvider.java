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
    private final Options options;

    public DefaultCryptoProvider(String key, String iv) {
        assert key != null;
        this.options = new Options(key, iv);
    }

    public DefaultCryptoProvider(Options options) {
        assert options != null;
        this.options = options;
    }

    public Options getOptions() {
        return options;
    }

    public String getKey() {
        return getOptions().getKey();
    }

    public String getIv() {
        return getOptions().getIv();
    }

    @Override
    public String encrypt(String text) {
        return CryptoUtil.encrypt(text, getOptions());
    }

    @Override
    public String decrypt(String text) {
        return CryptoUtil.decrypt(text, getOptions());
    }
}