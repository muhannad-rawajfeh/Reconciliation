package com.progressoft.jip11.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class TargetUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("target_file");
        String fileName = part.getSubmittedFileName();
        for (Part p : req.getParts())
            p.write(fileName);
        HttpSession session = req.getSession();
        session.setAttribute("targetName", req.getParameter("target_name"));
        session.setAttribute("targetType", req.getParameter("target_type"));
        session.setAttribute("targetFile", fileName);
        req.getRequestDispatcher("/WEB-INF/summary.jsp").forward(req, resp);
    }
}
