package com.progressoft.jip11.initializers;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.progressoft.jip11.recdb.DatabaseHandler;
import com.progressoft.jip11.recdb.DatabaseInitializer;
import com.progressoft.jip11.recdb.UsersImporter;
import com.progressoft.jip11.servlets.LoginServlet;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import java.nio.file.Paths;
import java.util.Set;

public class ReconciliationInitializer implements ServletContainerInitializer {

    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) {
        MysqlDataSource dataSource = prepareDataSource();

        DatabaseInitializer initializer = new DatabaseInitializer();
        initializer.initialize(dataSource);

        UsersImporter usersImporter = new UsersImporter(dataSource);
        usersImporter.importUsers(Paths.get("src", "main", "resources", "rec-users.csv"));

        LoginServlet loginServlet = new LoginServlet(new DatabaseHandler(dataSource));
        ServletRegistration.Dynamic loginServletRegistration = servletContext.addServlet("loginServlet", loginServlet);
        loginServletRegistration.addMapping("/login");
    }

    private MysqlDataSource prepareDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/induction");
        dataSource.setUser("admin");
        dataSource.setPassword("password");
        return dataSource;
    }
}
