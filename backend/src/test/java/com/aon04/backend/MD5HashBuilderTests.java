package com.aon04.backend;

import com.aon04.backend.models.Md5HashBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MD5HashBuilderTests {

    @Test
    public void givenFile_generatingChecksum_thenVerifying() throws NoSuchAlgorithmException, IOException {
        URL url = getClass().getResource(".");

        File file = new File("..\\images\\1314_logo_pxl_bol.png");
        String checksum = "02890843c509a558a36cea05404bd788";

        Md5HashBuilder builder = new Md5HashBuilder();
        String MD5Output = builder.createMD5Hash(new FileInputStream(file));

        assertEquals(checksum, MD5Output);
    }


    @Test(expected = IOException.class)
    public void IOExceptionIsThrownWhenGeneratingMD5OfANonExistingFile() throws IOException, NoSuchAlgorithmException {
        Md5HashBuilder md5HashBuilder = new Md5HashBuilder();
        md5HashBuilder.createMD5Hash(new FileInputStream(""));

    }

}
