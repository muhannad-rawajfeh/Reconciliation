package com.progressoft.jip11.servlets;

import com.progressoft.jip11.recdb.DatabaseHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {

    private final DatabaseHandler databaseHandler;

    public LoginServlet(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (!databaseHandler.isValidLoginRequest(username, password)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();
        writer.println("Welcome " + username + " your password is " + password);
        writer.flush();
    }
}
