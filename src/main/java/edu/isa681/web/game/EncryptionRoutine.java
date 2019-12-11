package edu.isa681.web.game;

import com.google.cloud.storage.StorageException;
import com.google.crypto.tink.Aead;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.JsonKeysetWriter;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.aead.AeadKeyTemplates;
import edu.isa681.DOA.util.CloudStorage;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class EncryptionRoutine {
    private static EncryptionRoutine encryptionRoutine;
    private Aead aead;
    private KeysetHandle keysetHandle;

    private EncryptionRoutine() {
        try {
            initEncryption();
            aead = keysetHandle.getPrimitive(Aead.class);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    public static EncryptionRoutine getEncryptionRoutine() {
        if (EncryptionRoutine.encryptionRoutine == null) {
            synchronized (EncryptionRoutine.class) {
                if (EncryptionRoutine.encryptionRoutine == null) {
                    encryptionRoutine = new EncryptionRoutine();
                }
            }
        }
        return encryptionRoutine;
    }

    byte[] encrypt(String data, String playerSub) throws GeneralSecurityException {
        byte[] ciphertext = aead.encrypt(data.getBytes(), playerSub.getBytes());
        return ciphertext;
    }

    String decrypt(byte[] data, String playerSub) throws GeneralSecurityException {
        byte[] decrypted = aead.decrypt(data, playerSub.getBytes());
        return new String(decrypted);
    }

    private void initEncryption() throws GeneralSecurityException {
        String keysetFilename = "keyset.json";
        try {
            AeadConfig.register();
            CloudStorage cloudStorage = new CloudStorage();
            if (cloudStorage.keyOnCloud(keysetFilename) == null) {
                File file = new File(keysetFilename);
                KeysetHandle keysetHandle = KeysetHandle.generateNew(AeadKeyTemplates.AES128_GCM);
                this.keysetHandle = keysetHandle;
                CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile(file));
                try {
                    cloudStorage.uploadFile(file);
                } catch (StorageException ex) {
                    ex.printStackTrace();
                    System.out.println("Will read from the file locally for now");
                }
            }//TODO -- > Write the else for this.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}