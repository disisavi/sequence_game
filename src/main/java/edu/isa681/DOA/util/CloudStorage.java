package edu.isa681.DOA.util;

import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.JsonKeysetReader;

import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CloudStorage {
    private static Storage storage = null;
    private static String bucketName = "appsecret";

    static {
        storage = StorageOptions.getDefaultInstance().getService();
    }

    public String uploadFile(File filePart) throws IOException {

        final String fileName = filePart.getName();

        // The InputStream is closed by default, so we don't need to close it here
        // Read InputStream into a ByteArrayOutputStream.
        InputStream is = new FileInputStream(fileName);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] readBuf = new byte[4096];
        while (is.available() > 0) {
            int bytesRead = is.read(readBuf);
            os.write(readBuf, 0, bytesRead);
        }

        // Convert ByteArrayOutputStream into byte[]
        BlobInfo blobInfo =
                storage.create(
                        BlobInfo
                                .newBuilder(bucketName, fileName)
                                // Modify access list to allow all users with link to read file
                                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                                .build(),
                        os.toByteArray());
        // return the public download link
        return blobInfo.getMediaLink();
    }

    public String keyOnCloud(String fileName) {
        BlobId blobId = BlobId.of("appsecret", fileName);
        Blob blob = storage.get(blobId);
        String fileContent = null;
        if (blob != null) {
            fileContent = new String(blob.getContent());
        }
        return fileContent;
    }

}
