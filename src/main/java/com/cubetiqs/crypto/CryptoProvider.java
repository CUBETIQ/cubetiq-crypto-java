package com.cubetiqs.crypto;

import com.cubetiqs.crypto.util.FunctionUtil;

/**
 * Crypto Provider, provides the methods to encrypt and decrypt the data.
 *
 * @author sombochea
 * @since 1.0
 */
public interface CryptoProvider extends ByteArrayCryptoProvider {
    interface CryptoOptions {
        String ENCRYPT_ALGORITHM = "ENCRYPT_ALGORITHM";
        String ENCRYPT_PADDING = "ENCRYPT_PADDING";
        String ENCRYPT_KEY = "ENCRYPT_KEY";
        String ENCRYPT_IV = "ENCRYPT_IV";

        String getProperty(String key);

        default String getProperty(String key, String defaultValue) {
            String value = getProperty(key);
            return value == null ? defaultValue : value;
        }

        default String getAlgorithm() {
            return getProperty(ENCRYPT_ALGORITHM, Constant.CRYPTO_ALGORITHM);
        }

        default String getPadding() {
            return getProperty(ENCRYPT_PADDING, Constant.CRYPTO_PADDING);
        }

        default String getKey() {
            return getProperty(ENCRYPT_KEY);
        }

        default String getIv() {
            return getProperty(ENCRYPT_IV);
        }
    }

    CryptoOptions getOptions();

    default String encrypt(String data) {
        return FunctionUtil.encrypt(data, getOptions());
    }

    default String decrypt(String encrypted) {
        return FunctionUtil.decrypt(encrypted, getOptions());
    }
}
