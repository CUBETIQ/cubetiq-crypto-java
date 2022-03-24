package com.cubetiqs.crypto;

/**
 * Crypto Provider, provides the methods to encrypt and decrypt the data.
 *
 * @author sombochea
 * @since 1.0
 */
public interface CryptoProvider {
    String encrypt(String text);

    String decrypt(String encrypted);
}
