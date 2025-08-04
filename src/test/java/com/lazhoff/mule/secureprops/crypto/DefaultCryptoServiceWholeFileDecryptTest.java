package com.lazhoff.mule.secureprops.crypto;

import com.lazhoff.mule.secureprops.util.TempFileManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class DefaultCryptoServiceWholeFileDecryptTest {

    private static final Logger logger = LogManager.getLogger(DefaultCryptoServiceWholeFileDecryptTest.class);

    @Test
    void decryptsWholeFile() throws Exception {
        String originalContent = "sample-content";
        Path file = Files.createTempFile("wholefile-decrypt-", ".txt");
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
                false
        );

        ICryptoService service = new DefaultCryptoServiceWholeFile(config);
        service.encrypt();
        service.decrypt();

        String decrypted = Files.readString(file);
        logger.info("Decrypted content: {}", decrypted);

        assertEquals(originalContent, decrypted);

        Files.deleteIfExists(file);
    }
}
