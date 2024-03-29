package com.aon04.backend.services;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "ssetorage")
/**
 * Configuration file for storageService.
 */
public class StorageProperties {

    /**
     * Folder location for storing files
     */

    private String location = "upload-dir";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
