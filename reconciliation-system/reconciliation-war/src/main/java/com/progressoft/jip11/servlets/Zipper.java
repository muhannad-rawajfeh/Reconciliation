package com.progressoft.jip11.servlets;

import java.io.*;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zipper {

    private static final int BUFFER_SIZE = 4096;

    public void zip(File folder, String zipFilePath) throws IOException {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFilePath));
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            zos.putNextEntry(new ZipEntry(folder.getName() + "/" + file.getName()));
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] bytesIn = new byte[BUFFER_SIZE];
            int read;
            while ((read = bis.read(bytesIn)) != -1)
                zos.write(bytesIn, 0, read);
            zos.closeEntry();
        }
        zos.flush();
        zos.close();
    }
}
