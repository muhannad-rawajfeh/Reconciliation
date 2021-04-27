package com.progressoft.jip11.servlets;

import com.progressoft.jip11.parsers.FilePath;
import com.progressoft.jip11.parsers.Transaction;
import com.progressoft.jip11.parsers.TransactionsParser;
import com.progressoft.jip11.parsers.TransactionsParserFactory;
import com.progressoft.jip11.reconciliators.SourcedTransaction;
import com.progressoft.jip11.reconciliators.TransactionsReconciliator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;

public class ResultServlet extends HttpServlet {

    private final TransactionsReconciliator transactionsReconciliator;

    public ResultServlet(TransactionsReconciliator transactionsReconciliator) {
        this.transactionsReconciliator = transactionsReconciliator;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String sourceType = (String) session.getAttribute("sourceType");
        String targetType = (String) session.getAttribute("targetType");
        String sourceFile = (String) session.getAttribute("sourceFile");
        String targetFile = (String) session.getAttribute("targetFile");

        PrintWriter writer = resp.getWriter();
        writer.println(sourceType);
        writer.println(targetType);
        writer.println(sourceFile);
        writer.println(targetFile);

//        TransactionsParser sourceParser = TransactionsParserFactory.createParser(sourceType);
//        TransactionsParser targetParser = TransactionsParserFactory.createParser(targetType);
//
//        List<Transaction> sourceParsed = sourceParser.parse(new FilePath(Paths.get("target", "tmp", sourceFile)));
//        List<Transaction> targetParsed = targetParser.parse(new FilePath(Paths.get("target", "tmp", targetFile)));
//
//        List<Transaction> matched = transactionsReconciliator.findMatched(sourceParsed, targetParsed);
//        List<SourcedTransaction> mismatched = transactionsReconciliator.findMismatched(sourceParsed, targetParsed);
//        List<SourcedTransaction> missing = transactionsReconciliator.wrapMissing(sourceParsed, targetParsed);
//
//        session.setAttribute("matched", matched);
//        session.setAttribute("mismatched", mismatched);
//        session.setAttribute("missing", missing);
//
//        req.getRequestDispatcher("/WEB-INF/results.html").forward(req, resp);
    }
}
