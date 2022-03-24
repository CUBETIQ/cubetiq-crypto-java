package com.cubetiqs.crypto.core;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public final class CryptoUtil {
    private static final String CRYPTO_ALGORITHM = "AES";
    private static final String CRYPTO_PADDING = "AES/CBC/PKCS5Padding";
    private static final int BUFFER_SIZE = 4 * 1024;
    private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

    private static byte[] encodeBase64(byte[] bytes) {
        return Base64.getEncoder().encode(bytes);
    }

    private static byte[] decodeBase64(byte[] bytes) {
        return Base64.getDecoder().decode(bytes);
    }

    private static String encodeFromBoas(ByteArrayOutputStream boas) {
        return new String(encodeBase64(boas.toByteArray()), DEFAULT_ENCODING);
    }

    private static String fromBoas(ByteArrayOutputStream boas) {
        return boas.toString(DEFAULT_ENCODING);
    }

    public static String encrypt(String text, Options options) {
        byte[] bytes = text.getBytes(DEFAULT_ENCODING);
        try (
                InputStream fis = new ByteArrayInputStream(bytes);
                ByteArrayOutputStream boas = new ByteArrayOutputStream()
        ) {
            encrypt(fis, boas, options);
            return encodeFromBoas(boas);
        } catch (Exception e) {
            System.out.println("Encrypt error: " + e.getMessage());
            return null;
        }
    }

    public static String decrypt(String encrypted, Options options) {
        byte[] bytes = decodeBase64(encrypted.getBytes(DEFAULT_ENCODING));
        try (
                InputStream fis = new ByteArrayInputStream(bytes);
                ByteArrayOutputStream boas = new ByteArrayOutputStream()
        ) {
            decrypt(fis, boas, options);
            return fromBoas(boas);
        } catch (Exception e) {
            System.out.println("Decrypt error: " + e.getMessage());
            return null;
        }
    }

    public static void encrypt(InputStream is, OutputStream os, Options options)
            throws NoSuchPaddingException, BadPaddingException,
            InvalidKeyException, NoSuchAlgorithmException,
            IOException, ShortBufferException,
            IllegalBlockSizeException, InvalidAlgorithmParameterException {
        execute(Cipher.ENCRYPT_MODE, is, os, options);
    }

    public static void decrypt(InputStream is, OutputStream os, Options options)
            throws NoSuchPaddingException, BadPaddingException,
            InvalidKeyException, NoSuchAlgorithmException,
            IOException, ShortBufferException,
            IllegalBlockSizeException, InvalidAlgorithmParameterException {
        execute(Cipher.DECRYPT_MODE, is, os, options);
    }

    private static void execute(int mode, InputStream is, OutputStream os, Options options)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException, IOException, ShortBufferException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(CRYPTO_PADDING);
        SecretKeySpec _key = new SecretKeySpec(
                decodeBase64(options.getKey().getBytes(DEFAULT_ENCODING)),
                CRYPTO_ALGORITHM
        );

        if (options.getIv() != null) {
            IvParameterSpec _iv = new IvParameterSpec(decodeBase64(options.getIv().getBytes(DEFAULT_ENCODING)));
            cipher.init(mode, _key, _iv);
        } else {
            cipher.init(mode, _key);
        }

        byte[] inBuffer = new byte[BUFFER_SIZE];
        byte[] outBuffer = new byte[BUFFER_SIZE];
        int inBytes;
        int outBytes;

        while ((inBytes = is.read(inBuffer)) != -1) {
            outBytes = cipher.update(inBuffer, 0, inBytes, outBuffer);
            os.write(outBuffer, 0, outBytes);
        }

        outBytes = cipher.doFinal(outBuffer, 0);
        os.write(outBuffer, 0, outBytes);
        os.flush();
    }

    public static class Options {
        // Base64 encode
        String key;
        // Base64 encode
        String iv;

        String algorithm;
        String padding;

        public Options(String key, String iv) {
            this.key = key;
            this.iv = iv;
            this.algorithm = CRYPTO_ALGORITHM;
            this.padding = CRYPTO_PADDING;
        }

        public Options(String key, String iv, String algorithm, String padding) {
            this.key = key;
            this.iv = iv;
            this.algorithm = algorithm;
            this.padding = padding;
        }

        public String getKey() {
            assert key != null;
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getIv() {
            return iv;
        }

        public void setIv(String iv) {
            this.iv = iv;
        }

        public String getAlgorithm() {
            return algorithm;
        }

        public void setAlgorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public String getPadding() {
            return padding;
        }

        public void setPadding(String padding) {
            this.padding = padding;
        }

        public static class Builder {
            Options options;

            public Builder() {
                options = new Options(null, null);
            }

            public Builder setKey(String key) {
                options.setKey(key);
                return this;
            }

            public Builder setIv(String iv) {
                options.setIv(iv);
                return this;
            }

            public Builder setAlgorithm(String algorithm) {
                options.setAlgorithm(algorithm);
                return this;
            }

            public Builder setPadding(String padding) {
                options.setPadding(padding);
                return this;
            }

            public Options build() {
                return options;
            }
        }

        public static Builder builder() {
            return new Builder();
        }
    }
}
