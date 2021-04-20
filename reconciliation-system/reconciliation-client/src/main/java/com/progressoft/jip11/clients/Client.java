package com.progressoft.jip11.clients;

import com.progressoft.jip11.apps.FileTransactionsExporter;
import com.progressoft.jip11.apps.ReconciliationSystem;
import com.progressoft.jip11.parsers.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(">> Enter source file location:");
        FilePath sourcePath = createPathIfValid(Paths.get(scanner.next()));
        if (sourcePath == null) return;

        System.out.println(">> Enter source file format:");
        TransactionsParser sourceParser = createParserIfValid(scanner.next());
        if (sourceParser == null) return;

        System.out.println(">> Enter target file location:");
        FilePath targetPath = createPathIfValid(Paths.get(scanner.next()));
        if (targetPath == null) return;

        System.out.println(">> Enter target file format:");
        TransactionsParser targetParser = createParserIfValid(scanner.next());
        if (targetParser == null) return;

        ReconciliationSystem system = new ReconciliationSystem(sourceParser, targetParser, new FileTransactionsExporter());
        try {
            system.reconcile(sourcePath, targetPath);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("Reconciliation finished.");
        System.out.println("Result files are available in " + Paths.get("reconciliation-results").toAbsolutePath());
    }

    private static TransactionsParser createParserIfValid(String format) {
        try {
            return TransactionsParserFactory.createParser(format);
        } catch (TransactionsParserFactoryException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private static FilePath createPathIfValid(Path path) {
        try {
            return new FilePath(path);
        } catch (InvalidFilePathException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}
