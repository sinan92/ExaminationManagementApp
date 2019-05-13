package com.aon04.backend.services;

import java.io.File;
import java.util.ArrayList;
import java.util.zip.ZipEntry;

public class CreateZipService {
    public Iterable<ZipEntry> createZip(Iterable<File> files)
    {
        try
        {
//            FileOutputStream fout = new FileOutputStream("examens" +
//                    LocalDateTime.now().getDayOfMonth() + "/" +
//                    LocalDateTime.now().getMonthValue() + ".zip");
//            ZipOutputStream zout = new ZipOutputStream(fout);
            Iterable<ZipEntry> entries = new ArrayList<ZipEntry>();
            for(File f: files)
            {
                ((ArrayList<ZipEntry>) entries).add(
                        new ZipEntry(f.getName())
                );
            }

            return entries;
        } catch(Exception e) {
            System.out.println("Error creating zipfiles: " + e.getMessage());
            return null;
        }

    }
}

