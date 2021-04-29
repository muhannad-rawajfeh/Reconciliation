package com.progressoft.jip11.servlets;

import com.progressoft.jip11.apps.ExportRequest;
import com.progressoft.jip11.apps.FileTransactionsExporter;
import com.progressoft.jip11.reconciliators.TransactionsWriter;
import com.progressoft.jip11.reconciliators.TransactionsWriterFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DownloadServlet extends HttpServlet {

    public static final String folderPath =
            "reconciliation-results";
    public static final String zipFilePath =
            "src/main/webapp/results.zip";
    private static final Zipper zipper = new Zipper();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        ExportRequest exportRequest = (ExportRequest) session.getAttribute("exportRequest");
        String results_type = req.getParameter("results_type");

        TransactionsWriter transactionsWriter = TransactionsWriterFactory.createWriter(results_type);
        FileTransactionsExporter fileTransactionsExporter = new FileTransactionsExporter(transactionsWriter);
        fileTransactionsExporter.exportTransactions(exportRequest);

        File results = new File(folderPath);
        zipper.zip(results, zipFilePath);

        String extension = "." + results_type.toLowerCase();
        Files.delete(Paths.get(folderPath, "matched" + extension));
        Files.delete(Paths.get(folderPath, "mismatched" + extension));
        Files.delete(Paths.get(folderPath, "missing" + extension));
        Files.delete(Paths.get(folderPath));

        req.getRequestDispatcher("/WEB-INF/download.html").forward(req, resp);
    }
}
