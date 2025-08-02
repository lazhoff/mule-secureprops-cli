package com.lazhoff.mule.secureprops.adapter;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;

import org.mule.encryption.Encrypter;
import org.mule.encryption.exception.MuleEncryptionException;

import com.mulesoft.modules.configuration.properties.api.EncryptionAlgorithm;
import com.mulesoft.modules.configuration.properties.api.EncryptionMode;


public class SecurePropertiesToolWrapper {
    public SecurePropertiesToolWrapper() {
    }


    void applyHoleFileFixed(String action, String algorithm, String mode, String key, boolean useRandomIVs,
                            String inputFilePath, String outputFilePath) throws IOException, MuleEncryptionException {
        File inputFile = new File(inputFilePath);
        InputStream stream = new FileInputStream(inputFile);
        byte[] bytes = IOUtils.toByteArray(stream);
        stream.close(); // bugfix

        byte[] result;
        if (action.equals("encrypt")) {
            result = encrypt(bytes, algorithm, mode, key, useRandomIVs);
            String wrapped = "![" + new String(result) + "]";
            FileUtils.writeByteArrayToFile(new File(outputFilePath), wrapped.getBytes());
        } else {
            // Expecting ![Base64...], so trim
            String content = new String(bytes).trim();
            if (content.startsWith("![") && content.endsWith("]")) {
                content = content.substring(2, content.length() - 1); // remove ![ ]
            }
            byte[] decrypted = decrypt(content.getBytes(), algorithm, mode, key, useRandomIVs);
            FileUtils.writeByteArrayToFile(new File(outputFilePath), decrypted);
        }
    }

    private byte[] encrypt(byte[] value, String algorithm, String mode, String key, boolean useRandomIVs) throws MuleEncryptionException {
        Encrypter encrypter = createEncrypter(algorithm, mode, key, useRandomIVs);
        return Base64.getEncoder().encode(encrypter.encrypt(value));
    }

    private Encrypter createEncrypter(String algorithm, String mode, String key, boolean useRandomIVs) {
        return EncryptionAlgorithm.valueOf(algorithm).getBuilder().forKey(key).using(EncryptionMode.valueOf(mode)).useRandomIVs(useRandomIVs).build();
    }
    private byte[] decrypt(byte[] value, String algorithm, String mode, String key, boolean useRandomIVs) throws MuleEncryptionException {
        Encrypter encrypter = createEncrypter(algorithm, mode, key, useRandomIVs);
        return encrypter.decrypt(Base64.getDecoder().decode(value));
    }

}