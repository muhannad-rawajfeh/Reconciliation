package com.progressoft.jip11.servlets;

import com.progressoft.jip11.recdb.DatabaseHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
        HttpSession session = req.getSession();
        session.setAttribute("isAuthorized", "yes");
        req.getRequestDispatcher("/WEB-INF/source-upload.html").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object isAuthorized = session.getAttribute("isAuthorized");
        if (isAuthorized == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "you should login first");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/source-upload.html").forward(req, resp);
    }
}
