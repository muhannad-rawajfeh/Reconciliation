package com.progressoft.jip11.servlets;

import com.progressoft.jip11.recdb.DatabaseHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private final DatabaseHandler databaseHandler;

    public LoginServlet(DatabaseHandler databaseHandler) {
        this.databaseHandler = databaseHandler;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (!databaseHandler.isValidLoginRequest(username, password)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "invalid username or password");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/source-upload.html").forward(req, resp);
    }
}
