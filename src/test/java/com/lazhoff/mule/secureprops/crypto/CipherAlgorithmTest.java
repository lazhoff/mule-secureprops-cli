package com.lazhoff.mule.secureprops.crypto;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class CipherAlgorithmTest {

    private static Stream<Arguments> algorithmProvider() {
        return Stream.of(
                Arguments.of("AES", "AES/ECB/PKCS5Padding", 128),
                Arguments.of("Blowfish", "Blowfish", 128),
                Arguments.of("DES", "DES/ECB/PKCS5Padding", 56),
                Arguments.of("DESede", "DESede/ECB/PKCS5Padding", 168),
                Arguments.of("RC2", "RC2", 128),
                Arguments.of("RC4", "RC4", 128)
        );
    }

    @ParameterizedTest
    @MethodSource("algorithmProvider")
    void encryptsAndDecrypts(String algorithm, String transformation, int keySize) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        keyGen.init(keySize);
        SecretKey key = keyGen.generateKey();
        Cipher cipher = Cipher.getInstance(transformation);

        byte[] input = "sample-text".getBytes(StandardCharsets.UTF_8);

        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encrypted = cipher.doFinal(input);

        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(encrypted);

        assertArrayEquals(input, decrypted);
    }
}
