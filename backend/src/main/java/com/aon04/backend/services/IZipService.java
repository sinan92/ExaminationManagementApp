package com.aon04.backend.services;

import java.io.File;

public interface IZipService {
    void init();

    File createZipFile(String zipFileName, String[] sourceFiles);
}
