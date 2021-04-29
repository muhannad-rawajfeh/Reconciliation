package com.progressoft.jip11.servlets;

import java.io.File;
import java.io.IOException;

public class ZipperTest {

    public static void main(String[] args) throws IOException {
        File results = new File("/home/user/IdeaProjects/induction-reconciliation-task/reconciliation-system/reconciliation-war/reconciliation-results");
        Zipper zipper = new Zipper();
        zipper.zip(results, "/home/user/Documents/results.zip");
    }
}
