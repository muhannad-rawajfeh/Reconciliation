package com.progressoft.jip11.servlets;

import com.progressoft.jip11.apps.ExportRequest;
import com.progressoft.jip11.apps.TransactionsExporter;
import com.progressoft.jip11.parsers.Transaction;
import com.progressoft.jip11.reconciliators.SourcedTransaction;

import javax.servlet.http.HttpSession;
import java.util.List;

public class ServletExporter implements TransactionsExporter {

    private final HttpSession session;

    public ServletExporter(HttpSession session) {
        this.session = session;
    }

    @Override
    public void exportTransactions(ExportRequest exportRequest) {
        List<Transaction> matched = exportRequest.getMatched();
        List<SourcedTransaction> mismatched = exportRequest.getMismatched();
        List<SourcedTransaction> missing = exportRequest.getMissing();

        session.setAttribute("matched", matched);
        session.setAttribute("mismatched", mismatched);
        session.setAttribute("missing", missing);
    }
}
