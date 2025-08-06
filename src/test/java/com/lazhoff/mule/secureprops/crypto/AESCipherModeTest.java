package com.lazhoff.mule.secureprops.crypto;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AESCipherModeTest {

    private static Stream<Arguments> modeProvider() {
        return Stream.of(
                Arguments.of("CBC", "PKCS5Padding", 16),
                Arguments.of("CFB", "NoPadding", 16),
                Arguments.of("ECB", "PKCS5Padding", 0),
                Arguments.of("OFB", "NoPadding", 16)
        );
    }

    @ParameterizedTest
    @MethodSource("modeProvider")
    void encryptsAndDecrypts(String mode, String padding, int ivLength) throws Exception {
        String algorithm = "AES";
        String transformation = algorithm + "/" + mode + "/" + padding;
        KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
        keyGen.init(128);
        SecretKey key = keyGen.generateKey();

        byte[] iv = new byte[ivLength];
        if (ivLength > 0) {
            new SecureRandom().nextBytes(iv);
        }

        Cipher encryptCipher = Cipher.getInstance(transformation);
        AlgorithmParameterSpec encSpec = createSpec(mode, iv);
        if (encSpec != null) {
            encryptCipher.init(Cipher.ENCRYPT_MODE, key, encSpec);
        } else {
            encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        }

        String input = "sample-text";
        byte[] encrypted = encryptCipher.doFinal(input.getBytes(StandardCharsets.UTF_8));

        Cipher decryptCipher = Cipher.getInstance(transformation);
        AlgorithmParameterSpec decSpec = createSpec(mode, iv);
        if (decSpec != null) {
            decryptCipher.init(Cipher.DECRYPT_MODE, key, decSpec);
        } else {
            decryptCipher.init(Cipher.DECRYPT_MODE, key);
        }
        byte[] decrypted = decryptCipher.doFinal(encrypted);

        assertEquals(input, new String(decrypted, StandardCharsets.UTF_8));
    }

    private static AlgorithmParameterSpec createSpec(String mode, byte[] iv) {
        return "ECB".equals(mode) ? null : new IvParameterSpec(iv);
    }
}
