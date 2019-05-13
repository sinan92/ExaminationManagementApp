package com.aon04.backend.models;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5HashBuilder {

    private MessageDigest messageDigest;
    private byte[] dataBytes;

    public Md5HashBuilder() throws NoSuchAlgorithmException {
        messageDigest = MessageDigest.getInstance("MD5");
        dataBytes = new byte[1024];
    }

    public String createMD5Hash(InputStream fileInputStream) throws IOException {
        int fileRead = 0;
        while ((fileRead = fileInputStream.read(dataBytes)) != -1) {
            messageDigest.update(dataBytes, 0, fileRead);
        }
        byte[] messageDigestBytes = messageDigest.digest();

        StringBuffer stringBufferMD5Output = new StringBuffer();
        for (int i = 0; i < messageDigestBytes.length; i++) {
            stringBufferMD5Output.append(Integer.toString((messageDigestBytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return stringBufferMD5Output.toString();
    }
}
