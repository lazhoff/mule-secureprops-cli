package com.lazhoff.mule.secureprops.crypto;

import com.lazhoff.mule.secureprops.util.TempFileManager;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CryptoExecutorSummarizeDiffTest {

    @Test
    void summarizesChangesBetweenStrings() throws Exception {
        CryptoConfig config = new CryptoConfig(
                CryptoConfig.FileOrLine.WHOLE_FILE,
                ".",
                "AES",
                "CBC",
                "key",
                false,
                TempFileManager.getSystemPathDir(),
                true,
                false
        );

        CryptoExecutor executor = new CryptoExecutor("encrypt", config, ".*:key");

        Method m = CryptoExecutor.class.getDeclaredMethod("summarizeDiff", String.class, String.class);
        m.setAccessible(true);
        String diff = (String) m.invoke(executor, "a\nb\nc\n", "a\nB\nc\n");

        assertTrue(diff.contains("- b"));
        assertTrue(diff.contains("+ B"));
    }
}
