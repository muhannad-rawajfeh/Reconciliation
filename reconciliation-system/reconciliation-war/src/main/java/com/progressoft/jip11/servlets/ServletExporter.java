package com.progressoft.jip11.servlets;

import com.progressoft.jip11.apps.ExportRequest;
import com.progressoft.jip11.apps.TransactionsExporter;

import javax.servlet.http.HttpSession;

public class ServletExporter implements TransactionsExporter {

    private final HttpSession session;

    public ServletExporter(HttpSession session) {
        this.session = session;
    }

    @Override
    public void exportTransactions(ExportRequest exportRequest) {
        session.setAttribute("exportRequest", exportRequest);
    }
}
