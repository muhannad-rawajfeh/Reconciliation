package com.progressoft.jip11.servlets;

import com.progressoft.jip11.apps.ReconciliationSystem;
import com.progressoft.jip11.parsers.FilePath;
import com.progressoft.jip11.parsers.TransactionsParser;
import com.progressoft.jip11.parsers.TransactionsParserFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Paths;

public class ResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String sourceType = (String) session.getAttribute("sourceType");
        String targetType = (String) session.getAttribute("targetType");
        String sourceFile = (String) session.getAttribute("sourceFile");
        String targetFile = (String) session.getAttribute("targetFile");

        TransactionsParser sourceParser = TransactionsParserFactory.createParser(sourceType);
        TransactionsParser targetParser = TransactionsParserFactory.createParser(targetType);

        ReconciliationSystem reconciliationSystem = new ReconciliationSystem(sourceParser, targetParser,
                new ServletExporter(session));
        reconciliationSystem.reconcile(new FilePath(Paths.get("target", "tmp", sourceFile)),
                new FilePath(Paths.get("target", "tmp", targetFile)));

        req.getRequestDispatcher("/WEB-INF/results.jsp").forward(req, resp);
    }
}
