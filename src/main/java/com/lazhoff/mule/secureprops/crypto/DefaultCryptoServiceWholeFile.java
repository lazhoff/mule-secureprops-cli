package com.lazhoff.mule.secureprops.crypto;

import com.lazhoff.mule.secureprops.adapter.SecurePropertiesConfig;
import com.lazhoff.mule.secureprops.adapter.SecurePropertiesToolAdapter;
import com.lazhoff.mule.secureprops.adapter.SecurePropertiesToolRunner;
import com.lazhoff.mule.secureprops.util.TempFileManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

import static com.lazhoff.mule.secureprops.crypto.CryptoExecutionResult.Status.*;

public class DefaultCryptoServiceWholeFile implements ICryptoService {

    private static final Logger logger = LogManager.getLogger(DefaultCryptoServiceWholeFile.class);

    private final CryptoConfig config;
    private final SecurePropertiesToolRunner runner;
    private final TempFileManager tempFileManager;

    public DefaultCryptoServiceWholeFile(CryptoConfig config) {
        this.config = config;
        this.runner = new SecurePropertiesToolAdapter();
        this.tempFileManager = new TempFileManager(config.getTempFolder());
    }

    @Override
    public CryptoExecutionResult.Status encrypt() {
        Path file = Path.of(config.getFilePath());
        try {
            if (isAlreadyEncrypted(file)) {
                logger.warn("File {} appears to be already encrypted. Skipping encryption.", file);
                return UNCHANGED;
            }
        } catch (IOException e) {
            logger.error("Failed to read file for encryption check: {}", file, e);
            return FAILED;
        }
        return process(SecurePropertiesConfig.Action.ENCRYPT);
    }

    @Override
    public CryptoExecutionResult.Status decrypt() {
        Path file = Path.of(config.getFilePath());
        try {
            if (!isAlreadyEncrypted(file)) {
                logger.warn("File {} does not appear to be encrypted. Skipping decryption.", file);
                return UNCHANGED;
            }
        } catch (IOException e) {
            logger.error("Failed to read file for decryption check: {}", file, e);
            return FAILED;
        }
        return process(SecurePropertiesConfig.Action.DECRYPT);
    }

    private boolean isAlreadyEncrypted(Path file) throws IOException {
        String content = Files.readString(file).trim();
        return content.startsWith("![") && content.endsWith("]");
    }


    private CryptoExecutionResult.Status process(SecurePropertiesConfig.Action action) {
        Path file = Path.of(config.getFilePath());
        Path temp = null;

        try {
            temp = tempFileManager.createTempSecureFile();

            SecurePropertiesConfig secureConfig = new SecurePropertiesConfig(
                    SecurePropertiesConfig.Type.WHOLE_FILE,
                    action,
                    config.getAlgorithm(),
                    config.getMode(),
                    config.getKey(),
                    null,
                    file.toString(),
                    temp.toString(),
                    config.isUseRandomIV()
            );

            SecurePropertiesToolAdapter.ExecutionResult result = runner.run(secureConfig);
            if (result.exitCode != 0) {
                logger.error("{} failed: {}", action, result.error);
                return FAILED;
            }

            if (config.isDryRun()) {
                byte[] original = Files.readAllBytes(file);
                byte[] processed = Files.readAllBytes(temp);
                return Arrays.equals(original, processed) ? UNCHANGED : DRYRUN_CHANGED;
            }

            if (config.isBackup()) {
                Path backup = file.resolveSibling(file.getFileName() + ".bak");
                Files.copy(file, backup, StandardCopyOption.REPLACE_EXISTING);
                logger.info("Backup created at: {}", backup);
            }

            Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
            logger.info("{} succeeded. File updated: {}", action, file);
            return SUCCESS;

        } catch (Exception e) {
            logger.error("Exception during {}: {}", action, e.toString(), e);
            return FAILED;
        } finally {
            tempFileManager.cleanAllTempFiles();
        }
    }




}

