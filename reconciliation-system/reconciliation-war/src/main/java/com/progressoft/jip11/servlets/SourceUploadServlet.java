package com.progressoft.jip11.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/target-upload"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,     // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class SourceUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Part part = req.getPart("file");
        String fileName = part.getSubmittedFileName();
        for (Part p : req.getParts()) {
            p.write(fileName);
        }
        PrintWriter writer = resp.getWriter();
        writer.println("The file uploaded successfully.");
        writer.println(fileName);

//        String sourceName = req.getParameter("source_name");
//        String fileType = req.getParameter("file_type");
//        String file = req.getParameter("file");
//
//        PrintWriter writer = resp.getWriter();
//        writer.println(sourceName);
//        writer.println(fileType);
//        writer.println(file);
    }
}
