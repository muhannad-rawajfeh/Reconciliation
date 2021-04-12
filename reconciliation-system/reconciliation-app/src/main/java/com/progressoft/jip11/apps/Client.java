package com.progressoft.jip11.apps;

import com.progressoft.jip11.parsers.TransactionsParser;
import com.progressoft.jip11.parsers.TransactionsParserFactory;
import com.progressoft.jip11.reconciliators.CSVTransactionsImporter;
import com.progressoft.jip11.reconciliators.TransactionsImporter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(">> Enter source file location:");
        Path sourcePath = Paths.get(scanner.next());

        System.out.println(">> Enter source file format:");
        String sourceFormat = scanner.next();

        System.out.println(">> Enter target file location:");
        Path targetPath = Paths.get(scanner.next());

        System.out.println(">> Enter target file format:");
        String targetFormat = scanner.next();

        TransactionsParserFactory parserFactory = new TransactionsParserFactory();
        TransactionsParser sourceParser = parserFactory.createParser(sourceFormat);
        TransactionsParser targetParser = parserFactory.createParser(targetFormat);

        TransactionsImporter importer = new CSVTransactionsImporter();

        ReconciliationApp app = new ReconciliationApp(sourceParser, targetParser, importer);
        app.run(sourcePath, targetPath);
    }
}
