package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HibernateUtility {

    static {
        // Silenciar todos los logs de Hibernate
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
    }

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            // Configuración básica
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/videojuegos?useSSL=false&serverTimezone=UTC");
            configuration.setProperty("hibernate.connection.username", "root");
            configuration.setProperty("hibernate.connection.password", "1234");

            // Pool mínimo
            configuration.setProperty("hibernate.connection.pool_size", "1");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");

            // Clases
            configuration.addAnnotatedClass(models.Desarrolladora.class);
            configuration.addAnnotatedClass(models.Videojuego.class);
            configuration.addAnnotatedClass(models.Jugador.class);
            configuration.addAnnotatedClass(models.Compra.class);

            return configuration.buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Error Hibernate: " + ex.getMessage());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}