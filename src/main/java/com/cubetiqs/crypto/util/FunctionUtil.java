package com.cubetiqs.crypto.util;

import com.cubetiqs.crypto.CryptoProvider;
import com.cubetiqs.crypto.core.CryptoUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class FunctionUtil {
    public static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

    public static byte[] encodeBase64(byte[] bytes) {
        return Base64.getEncoder().encode(bytes);
    }

    public static byte[] decodeBase64(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }

    public static byte[] decodeBase64FromString(String str) {
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
}
