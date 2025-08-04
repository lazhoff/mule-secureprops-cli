package com.lazhoff.mule.secureprops.crypto;

import com.lazhoff.mule.secureprops.util.TempFileManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DefaultCryptoServiceWholeFileEncryptTest {

    private static final Logger logger = LogManager.getLogger(DefaultCryptoServiceWholeFileEncryptTest.class);

    @Test
    void encryptsWholeFile() throws Exception {
        String originalContent = "sample-content";
        Path file = Files.createTempFile("wholefile-encrypt-", ".txt");
        Files.writeString(file, originalContent);

        CryptoConfig config = new CryptoConfig(
                CryptoConfig.FileOrLine.WHOLE_FILE,
                file.toString(),
                "AES",
                "CBC",
                "0000y1230000yXYZ",
                false,
                TempFileManager.getSystemPathDir(),
                false,
                false,
                true
        );

        ICryptoService service = new DefaultCryptoServiceWholeFile(config);
        service.encrypt();

        String encrypted = Files.readString(file).trim();
        logger.info("Encrypted content: {}", encrypted);

        assertNotEquals(originalContent, encrypted);
        assertTrue(encrypted.startsWith("![") && encrypted.endsWith("]"));

        Files.deleteIfExists(file);
        Files.deleteIfExists(file.resolveSibling(file.getFileName() + ".bak"));
    }
}
