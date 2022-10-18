package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {

    private static final String URL = "jdbc:mysql://localhost:3306/mydbuser";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    private static final String DIALECT = "org.hibernate.dialect.MySQLDialect";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {

        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties prop = new Properties();
                prop.put(Environment.URL, URL);
                prop.put(Environment.USER, USERNAME);
                prop.put(Environment.PASS, PASSWORD);
                prop.put(Environment.DIALECT, DIALECT);
                prop.put(Environment.DRIVER, DRIVER);
                prop.put(Environment.SHOW_SQL, "true");
                configuration.setProperties(prop);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception ignored) {

            }
        }
        return sessionFactory;
    }

    public static Connection getConnect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ignored) {
        }
        return conn;
    }
}
