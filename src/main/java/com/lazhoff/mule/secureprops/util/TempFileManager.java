package com.lazhoff.mule.secureprops.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.*;

import java.util.HashSet;
import java.util.Set;

public class TempFileManager {

    private static final Logger logger = LogManager.getLogger(TempFileManager.class);

    private final Path tempDir;
    private final Set<Path> trackedTempFiles = new HashSet<>();

    public TempFileManager(Path tempDir) {
        logger.debug("tempDir: {}",tempDir.toAbsolutePath().toString());
        this.tempDir = tempDir;
    }

    public static Path getSystemPathDir() { return Path.of(System.getProperty("java.io.tmpdir")); }

    public Path createTempSecureYamlFile() throws IOException {
        Path file = Files.createTempFile(tempDir, "secure-yaml-", ".tmp.yaml");
        trackedTempFiles.add(file);
        return file;
    }

    public Path createTempSecureFile() throws IOException {
        Path file = Files.createTempFile(tempDir, "secure-file-", ".tmp");
        trackedTempFiles.add(file);
        return file;
    }

    public Path createTempPreviewInYamlFile() throws IOException {
        Path file = Files.createTempFile(tempDir, "preview-yaml-", ".in.yaml");
        trackedTempFiles.add(file);
        return file;
    }

    public Path createTempPreviewOutYamlFile() throws IOException {
        Path file = Files.createTempFile(tempDir, "preview-yaml-", ".out.yaml");
        trackedTempFiles.add(file);
        return file;
    }

    public void cleanAllByPattern() {
        cleanByPattern("secure-yaml-", ".tmp.yaml");
        cleanByPattern("preview-yaml-", ".in.yaml");
        cleanByPattern("preview-yaml-", ".out.yaml");
        cleanByPattern("secure-file-", ".tmp");
    }

public void cleanAllTempFiles() {
    for (Path file : trackedTempFiles) {
        try {
            Files.deleteIfExists(file);
            logger.debug("Deleted tracked temp file: {}", file);
        } catch (IOException e) {
            logger.warn("Failed to delete tracked temp file: {}", file, e);
        }
    }
    trackedTempFiles.clear();
}


    private void cleanByPattern(String prefix, String suffix) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(tempDir)) {
            for (Path file : stream) {
                String name = file.getFileName().toString();
                if (name.startsWith(prefix) && name.endsWith(suffix)) {
                    Files.deleteIfExists(file);
                    System.out.println("Deleted: " + file);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to clean files: " + e.getMessage());
        }
    }
}