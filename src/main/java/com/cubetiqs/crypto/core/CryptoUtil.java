package com.cubetiqs.crypto.core;

import com.cubetiqs.crypto.CryptoProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static com.cubetiqs.crypto.util.FunctionUtil.DEFAULT_ENCODING;
import static com.cubetiqs.crypto.util.FunctionUtil.decodeBase64;

public final class CryptoUtil {
    private static final int BUFFER_SIZE = 4 * 1024;

    public static byte[] encrypt(byte[] data, CryptoProvider.CryptoOptions options) {
        try (
                InputStream fis = new ByteArrayInputStream(data);
                ByteArrayOutputStream boas = new ByteArrayOutputStream()
        ) {
            encrypt(fis, boas, options);
            return boas.toByteArray();
        } catch (Exception e) {
            System.out.println("Encrypt error: " + e.getMessage());
            return null;
        }
    }

    public static byte[] decrypt(byte[] encrypted, CryptoProvider.CryptoOptions options) {
        try (
                InputStream fis = new ByteArrayInputStream(encrypted);
                ByteArrayOutputStream boas = new ByteArrayOutputStream()
        ) {
            decrypt(fis, boas, options);
            return boas.toByteArray();
        } catch (Exception e) {
            System.out.println("Decrypt error: " + e.getMessage());
            return null;
        }
    }

    public static void encrypt(InputStream is, OutputStream os, CryptoProvider.CryptoOptions options)
            throws NoSuchPaddingException, BadPaddingException,
            InvalidKeyException, NoSuchAlgorithmException,
            IOException, ShortBufferException,
            IllegalBlockSizeException, InvalidAlgorithmParameterException {
        execute(Cipher.ENCRYPT_MODE, is, os, options);
    }

    public static void decrypt(InputStream is, OutputStream os, CryptoProvider.CryptoOptions options)
            throws NoSuchPaddingException, BadPaddingException,
            InvalidKeyException, NoSuchAlgorithmException,
            IOException, ShortBufferException,
            IllegalBlockSizeException, InvalidAlgorithmParameterException {
        execute(Cipher.DECRYPT_MODE, is, os, options);
    }

    private static void execute(int mode, InputStream is, OutputStream os, CryptoProvider.CryptoOptions options)
            throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException,
            InvalidKeyException, IOException, ShortBufferException,
            BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(options.getPadding());
        SecretKeySpec _key = new SecretKeySpec(
                decodeBase64(options.getKey().getBytes(DEFAULT_ENCODING)),
                options.getAlgorithm()
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

    public static class Options implements CryptoProvider.CryptoOptions {
        @Override
        public String toString() {
            return "Options{" +
                    "options=" + options +
                    '}';
        }

        private final Map<String, String> options = new HashMap<>();

        public Options(String key, String iv) {
            setKey(key);
            setIv(iv);
        }

        public Options(String key, String iv, String algorithm, String padding) {
            setKey(key);
            setIv(iv);
            setAlgorithm(algorithm);
            setPadding(padding);
        }

        public Options(String key, String iv, String algorithm, String padding, Map<String, String> moreOptions) {
            setKey(key);
            setIv(iv);
            setAlgorithm(algorithm);
            setPadding(padding);
            options.putAll(moreOptions);
        }

        public Options(Map<String, String> options) {
            this.options.putAll(options);
        }

        public void setKey(String key) {
            setProperty(ENCRYPT_KEY, key);
        }

        public void setIv(String iv) {
            setProperty(ENCRYPT_IV, iv);
        }

        @Override
        public String getProperty(String key) {
            return options.get(key);
        }

        public void setProperty(String key, String value) {
            options.put(key, value);
        }

        public void setAlgorithm(String algorithm) {
            setProperty(ENCRYPT_ALGORITHM, algorithm);
        }

        public void setPadding(String padding) {
            setProperty(ENCRYPT_PADDING, padding);
        }

        public static class Builder {
            private final Options options;

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

            public Builder setProperty(String key, String value) {
                options.setProperty(key, value);
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
