package com.progressoft.jip11.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class SourceUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("source_file");
        String fileName = part.getSubmittedFileName();
        part.write(fileName);

        HttpSession session = req.getSession();
        session.setAttribute("sourceName", req.getParameter("source_name"));
        session.setAttribute("sourceType", req.getParameter("source_type"));
        session.setAttribute("sourceFile", fileName);
        req.getRequestDispatcher("/WEB-INF/target-upload.jsp").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object isAuthorized = session.getAttribute("isAuthorized");
        if (isAuthorized == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "you should login first");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/target-upload.jsp").forward(req, resp);
    }
}
