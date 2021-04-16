package com.progressoft.jip11.reconciliators;

import com.progressoft.jip11.parsers.ValidPath;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class CSVFileWriter {

    public <T> void write(ValidPath validPath, List<T> objects, String header) {
        validatePathAndList(validPath, objects);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(validPath.getPath())) {
            bufferedWriter.write(header);
            for (Object object : objects) {
                bufferedWriter.write(object.toString() + "\n");
            }
        } catch (IOException e) {
            throw new TransactionsExporterException(e.getMessage(), e);
        }
    }

    private void validatePathAndList(ValidPath validPath, List<?> objects) {
        if (validPath == null)
            throw new TransactionsExporterException("path is null");
        if (objects == null)
            throw new TransactionsExporterException("list is null");
    }
}
