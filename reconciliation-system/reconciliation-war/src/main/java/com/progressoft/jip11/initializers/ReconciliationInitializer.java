package com.progressoft.jip11.initializers;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.progressoft.jip11.recdb.DatabaseHandler;
import com.progressoft.jip11.recdb.DatabaseInitializer;
import com.progressoft.jip11.recdb.UsersImporter;
import com.progressoft.jip11.servlets.LoginServlet;
import com.progressoft.jip11.servlets.SourceUploadServlet;
import com.progressoft.jip11.servlets.TargetUploadServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.nio.file.Paths;
import java.util.Set;

public class ReconciliationInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) {
        MysqlDataSource dataSource = prepareDataSource();

        //initializeDatabase(servletContext, dataSource);

        registerLoginServlet(servletContext, dataSource);
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement("",
                1024 * 1024 * 10, 1024 * 1024 * 100, 1024 * 1024);
        registerSourceUploadServlet(servletContext, multipartConfigElement);
        registerTargetUploadServlet(servletContext, multipartConfigElement);
    }

    private void registerTargetUploadServlet(ServletContext servletContext, MultipartConfigElement multipartConfigElement) {
        TargetUploadServlet targetUploadServlet = new TargetUploadServlet();
        ServletRegistration.Dynamic targetUploadServletRegistration = servletContext.addServlet("targetUploadServlet", targetUploadServlet);
        targetUploadServletRegistration.addMapping("/summary");
        targetUploadServletRegistration.setMultipartConfig(multipartConfigElement);
    }

    private void registerSourceUploadServlet(ServletContext servletContext, MultipartConfigElement multipartConfigElement) {
        SourceUploadServlet sourceUploadServlet = new SourceUploadServlet();
        ServletRegistration.Dynamic sourceUploadServletRegistration = servletContext.addServlet("sourceUploadServlet", sourceUploadServlet);
        sourceUploadServletRegistration.addMapping("/target-upload");
        sourceUploadServletRegistration.setMultipartConfig(multipartConfigElement);
    }

    private void registerLoginServlet(ServletContext servletContext, MysqlDataSource dataSource) {
        LoginServlet loginServlet = new LoginServlet(new DatabaseHandler(dataSource));
        ServletRegistration.Dynamic loginServletRegistration = servletContext.addServlet("loginServlet", loginServlet);
        loginServletRegistration.addMapping("/source-upload");
    }

    private void initializeDatabase(ServletContext servletContext, MysqlDataSource dataSource) {
        DatabaseInitializer initializer = new DatabaseInitializer();
        initializer.initialize(dataSource);
        UsersImporter usersImporter = new UsersImporter(dataSource);
        String usersFilePath = servletContext.getInitParameter("users-path");
        usersImporter.importUsers(Paths.get(usersFilePath));
    }

    private MysqlDataSource prepareDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/induction");
        dataSource.setUser("admin");
        dataSource.setPassword("password");
        return dataSource;
    }
}
