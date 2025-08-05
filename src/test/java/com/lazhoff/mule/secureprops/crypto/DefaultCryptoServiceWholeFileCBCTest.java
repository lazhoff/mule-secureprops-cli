package com.lazhoff.mule.secureprops.crypto;

import com.lazhoff.mule.secureprops.util.TempFileManager;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class DefaultCryptoServiceWholeFileCBCTest {

    @Test
    void encryptsAndDecryptsUsingCbcMode() throws Exception {
        String original = "cbc-test";
        Path file = Files.createTempFile("cbc-mode-", ".txt");
        Files.writeString(file, original);

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
        String encrypted = Files.readString(file);
        assertNotEquals(original, encrypted);

        service.decrypt();
        String decrypted = Files.readString(file);
        assertEquals(original, decrypted);

        Files.deleteIfExists(file);
    }
}
