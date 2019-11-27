package com.symbio.dashboard.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @since 2019-08
 */
public interface FileService {

    void upload(String path, File file, FileExtensionEnum ext);

    void upload(String path, InputStream inputStream, FileExtensionEnum ext);

    byte[] download(String path);

    byte[] download(String containerName, String path);

    List<String> list(String pathName);

    List<String> listPath(String destPath);

    void delete(String path);

    boolean isFile(String path);
}
