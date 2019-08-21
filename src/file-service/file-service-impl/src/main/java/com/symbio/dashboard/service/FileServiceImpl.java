package com.symbio.dashboard.service;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Shawn
 * @since 2019-08
 */
@Service
public class FileServiceImpl implements FileService {

    @Value("${app.file.root}")
    private String rootDirectory;


    @Override
    public void upload(String path, File file, FileExtensionEnum ext) {
        try {
            upload(path, new FileInputStream(file), ext);
        } catch (IOException e) {
            throw new IllegalStateException("can not read source file " + file, e);
        }

    }

    @Override
    public void upload(String path, InputStream inputStream, FileExtensionEnum ext) {
        String destpath = rootDirectory + "/" + path;
        ext.verify(destpath);
        File dest = new File(destpath);
        dest.getParentFile().mkdirs();
        try {
            FileUtils.copyInputStreamToFile(inputStream, dest);
        } catch (IOException e) {
            throw new IllegalStateException("can not upload file " + destpath, e);
        }
    }

    @Override
    public byte[] download(String path) {
        String destpath = rootDirectory + "/" + path;
        File dest = new File(destpath);
        try (FileInputStream input = new FileInputStream(dest)) {
            return IOUtils.toByteArray(input);
        } catch (IOException e) {
            throw new IllegalArgumentException("can not read file " + destpath, e);
        }
    }

    @Override
    public byte[] download(String containerName, String path) {
        return download(containerName + "/" + path);
    }

    @Override
    public List<String> list(String pathName) {
        String destPath = rootDirectory + "/" + pathName;
        return listPath(destPath);
    }

    @Override
    public List<String> listPath(String destPath) {
        File dest = new File(destPath);
        if (dest.isFile()) {
            return Collections.emptyList();
        }
        final File[] files = dest.listFiles();
        if (files == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(files)
                .map(File::getName)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String path) {
        String destpath = rootDirectory + "/" + path;
        File dest = new File(destpath);
        if (!dest.exists()) {
            throw new IllegalStateException("file not found: " + destpath);
        }
        if (dest.isDirectory()) {
            final File[] children = dest.listFiles();
            if (children != null) {
                for (File child : children) {
                    delete(path + "/" + child.getName());
                }
            }
        }
        try {
            Files.delete(dest.toPath());
        } catch (IOException e) {
            throw new IllegalStateException("could not delete: " + destpath, e);
        }
    }

    @Override
    public boolean isFile(String pathName) {
        String destPath = rootDirectory + "/" + pathName;
        File dest = new File(destPath);
        if (!dest.exists()) {
            throw new IllegalStateException("file not found: " + destPath);
        }
        return dest.isFile();
    }

}
