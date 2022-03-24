package com.cubetiqs.crypto;

/**
 * ByteArray Crypto Provider, provides the methods to encrypt and decrypt the data.
 *
 * @author sombochea
 * @since 1.0
 */
public interface ByteArrayCryptoProvider {
    byte[] encrypt(byte[] encrypted);

    byte[] decrypt(byte[] data);
}
