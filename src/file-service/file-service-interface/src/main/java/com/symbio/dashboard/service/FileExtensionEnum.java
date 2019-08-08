package com.symbio.dashboard.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum FileExtensionEnum {

    ALL(""), IMAGE("PNG,JPEG,GIF,BMP,ICO,JPG"), TXT("TXT"), ZIP("ZIP");

    private String ext;

    private FileExtensionEnum(String ext) {
        this.ext = ext;
    }

    public void verify(String path) {
        if (!StringUtils.hasText(path)) {
            throw new IllegalArgumentException("path can not be null");
        }
        if (StringUtils.hasText(ext)) {
            String extension = FilenameUtils.getExtension(path);
            if (!StringUtils.hasText(extension)) {
                throw new IllegalArgumentException("file not found: " + path);
            }
            extension = extension.trim().toUpperCase();
            List<String> strings = Arrays.asList(ext.split(","));
            if (!strings.contains(extension)) {
                throw new IllegalArgumentException("Please make sure the type is" + strings.stream().map(s -> " " + s.toLowerCase()).collect(Collectors.joining(",")) + ".");
            }
        }
    }

}
