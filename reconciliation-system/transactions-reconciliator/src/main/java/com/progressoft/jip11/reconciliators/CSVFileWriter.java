package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.FilePath;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class CSVFileWriter {

    public <T> void write(FilePath filePath, List<T> objects, String header) {
        validatePathAndList(filePath, objects);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(filePath.getPath())) {
            bufferedWriter.write(header);
            for (Object object : objects) {
                bufferedWriter.write(object.toString() + "\n");
            }
        } catch (IOException e) {
            throw new CSVFileWriterException(e.getMessage(), e);
        }
    }

    private void validatePathAndList(FilePath filePath, List<?> objects) {
        if (filePath == null)
            throw new CSVFileWriterException("path is null");
        if (objects == null)
            throw new CSVFileWriterException("list is null");
    }
}
