package components;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Ejemplo de uso en un main
 *
 * HibernateConnectionBean conexionBean = new HibernateConnectionBean();
 *      Opcional: cambiar propiedades con setters
 *      conexionBean.setUsername("otro");
 *      conexionBean.setPassword("clave");
 *
 * Session session = conexionBean.openSession();
 *
 * // ... realizar operaciones con la sesión ...
 *
 * session.close(); //Cerramos sesión
 * conexionBean.close(); //Cerramos la conexión con Bean
 */

public class HibernateConnectionBean implements Serializable {
    private String driver;
    private String url;
    private String username;
    private String password;
    private transient SessionFactory sessionFactory;

    // Constructor vacío (obligatorio para JavaBean)
    public HibernateConnectionBean() {
        this.driver = "com.mysql.cj.jdbc.Driver";
        this.url = "jdbc:mysql://localhost:3306/videojuegos?useSSL=false&serverTimezone=UTC";
        this.username = "root";
        this.password = "1234";
        initSessionFactory();
    }

    // Constructor con parámetros (por si se quiere conectar a otra base de datos)
    public HibernateConnectionBean(String driver, String url, String username, String password) {
        this.driver = driver;
        this.url = url;
        this.username = username;
        this.password = password;
        initSessionFactory();
    }

    // Inicializa SessionFactory con las propiedades actuales
    private void initSessionFactory() {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        try {
            Configuration configuration = new Configuration();
            configuration.setProperty("hibernate.connection.driver_class", driver);
            configuration.setProperty("hibernate.connection.url", url);
            configuration.setProperty("hibernate.connection.username", username);
            configuration.setProperty("hibernate.connection.password", password);
            configuration.setProperty("hibernate.connection.pool_size", "1");
            configuration.setProperty("hibernate.hbm2ddl.auto", "update");
            // Clases anotadas
            configuration.addAnnotatedClass(models.Desarrolladora.class);
            configuration.addAnnotatedClass(models.Videojuego.class);
            configuration.addAnnotatedClass(models.Jugador.class);
            configuration.addAnnotatedClass(models.Compra.class);
            sessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Getters y setters obligatorios
    public String getDriver() { return driver; }
    public void setDriver(String driver) { this.driver = driver; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Método para obtener una sesión de trabajo
    public Session openSession() {
        return sessionFactory.openSession();
    }

    // Método para cerrar la fábrica de sesiones
    public void close() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}