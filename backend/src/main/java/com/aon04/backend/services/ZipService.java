package com.aon04.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipService implements IZipService {
    private final ILogService log;

    private final StorageService storageService;

    @Override
    public void init() {
    }

    @Autowired
    public ZipService(ILogService logService, StorageService storageService) {
        this.log = logService;
        this.storageService = storageService;
    }

    @Override
    public File createZipFile(String zipFileName, String[] sourceFiles) {
        byte[] buffer = new byte[1024];

        try (ZipOutputStream zipOutputStream =
                     new ZipOutputStream(new FileOutputStream(zipFileName + ".zip"))) {
            for (String source : sourceFiles) {
                if (source != null) {
                    File file = storageService.load(source).toFile();

                    try (FileInputStream fileInputStream = new FileInputStream(file);) {
                        zipOutputStream.putNextEntry(new ZipEntry(file.getName()));

                        int length;
                        while ((length = fileInputStream.read(buffer)) > 0) {
                            zipOutputStream.write(buffer, 0, length);
                        }
                    } catch (Exception e) {
                        log.error("Error adding file to zip: " + e.getMessage(), this.getClass());
                        return null;
                    }
                }
            }
        } catch (IOException e) {
            log.error("Error creating zip: " + e.getMessage(), this.getClass());
            return null;
        }

    //    log.info("Zip created", this.getClass());
        return new File(zipFileName);
    }

}


