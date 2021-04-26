package com.progressoft.jip11.servlets;

import com.progressoft.jip11.parsers.TransactionsParserFactory;
import com.progressoft.jip11.recdb.DatabaseHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class LoginServlet extends HttpServlet {

    private final DatabaseHandler databaseHandler;

    public LoginServlet(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (!databaseHandler.isValidLoginRequest(username, password)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid username or password");
            return;
        }
        setAttributes(req);
        req.getRequestDispatcher("/WEB-INF/source-upload.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object isAuthorized = session.getAttribute("isAuthorized");
        if (isAuthorized == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "you should login first");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/source-upload.jsp").forward(req, resp);
    }

    private void setAttributes(HttpServletRequest req) {
        HttpSession session = req.getSession();
        List<String> types = TransactionsParserFactory.supportedTypes;
        session.setAttribute("isAuthorized", "yes");
        session.setAttribute("types", types);
    }
}
