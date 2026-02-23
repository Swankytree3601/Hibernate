package utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Configurar Hibernate programáticamente
            Configuration configuration = new Configuration();

            // Cargar propiedades del archivo database.properties
            Properties props = new Properties();
            try (InputStream input = HibernateUtil.class.getClassLoader()
                    .getResourceAsStream("database.properties")) {
                if (input != null) {
                    props.load(input);
                    System.out.println("database.properties cargado correctamente");
                } else {
                    System.err.println("No se encontró database.properties, usando valores por defecto");
                    // Valores por defecto
                    props.setProperty("db.url", "jdbc:mysql://localhost:3306/videojuegos?useSSL=false&serverTimezone=UTC");
                    props.setProperty("db.usuario", "root");
                    props.setProperty("db.password", "1234");
                }
            } catch (Exception e) {
                System.err.println("Error al cargar database.properties: " + e.getMessage());
            }

            // Configurar conexión
            configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            configuration.setProperty("hibernate.connection.url", props.getProperty("db.url"));
            configuration.setProperty("hibernate.connection.username", props.getProperty("db.usuario"));
            configuration.setProperty("hibernate.connection.password", props.getProperty("db.password"));

            // Configurar dialecto
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

            // Configurar opciones adicionales
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.format_sql", "true");
            configuration.setProperty("hibernate.hbm2ddl.auto", "validate");

            // Configurar pool de conexiones (opcional)
            configuration.setProperty("hibernate.c3p0.min_size", "5");
            configuration.setProperty("hibernate.c3p0.max_size", "20");
            configuration.setProperty("hibernate.c3p0.timeout", "300");
            configuration.setProperty("hibernate.c3p0.max_statements", "50");
            configuration.setProperty("hibernate.c3p0.idle_test_period", "3000");

            // Añadir clases anotadas
            configuration.addAnnotatedClass(models.Desarrolladora.class);
            configuration.addAnnotatedClass(models.Videojuego.class);
            configuration.addAnnotatedClass(models.Jugador.class);
            configuration.addAnnotatedClass(models.Compra.class);

            System.out.println("Configuración de Hibernate completada");
            return configuration.buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("Error al inicializar SessionFactory: " + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}