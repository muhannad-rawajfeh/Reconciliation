package com.progressoft.jip11.apps;

import com.progressoft.jip11.parsers.*;
import com.progressoft.jip11.reconciliators.CSVTransactionsImporter;
import com.progressoft.jip11.reconciliators.Channel;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(">> Enter source file location:");
        ValidPath sourcePath = createPathIfValid(Paths.get(scanner.next()));
        if (sourcePath == null) return;

        System.out.println(">> Enter source file format:");
        TransactionsParser sourceParser = createParserIfValid(scanner.next());
        if (sourceParser == null) return;

        System.out.println(">> Enter target file location:");
        ValidPath targetPath = createPathIfValid(Paths.get(scanner.next()));
        if (targetPath == null) return;

        System.out.println(">> Enter target file format:");
        TransactionsParser targetParser = createParserIfValid(scanner.next());
        if (targetParser == null) return;

        ReconciliationApp app = new ReconciliationApp(sourceParser, targetParser, new CSVTransactionsImporter());
        Channel result = app.reconcile(sourcePath, targetPath);
        System.out.println("Reconciliation finished.");
        System.out.println("Result files are available in " + result);
    }

    private static TransactionsParser createParserIfValid(String format) {
        try {
            return TransactionsParserFactory.createParser(format);
        } catch (TransactionsParserFactoryException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private static ValidPath createPathIfValid(Path path) {
        try {
            return new ValidPath(path);
        } catch (MyInvalidPathException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
