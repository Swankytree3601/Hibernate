import components.HibernateConnectionBean;
import models.Videojuego;
import org.hibernate.Session;
import java.util.List;

public class JavaBeanPlay {
    public static void main(String[] args) {
        HibernateConnectionBean conexionBean = new HibernateConnectionBean();

        //Si quisiéramos usar otro usuario para la base de datos:
            //conexionBean.setUsername("otro");
            //conexionBean.setPassword("clave");

        Session session = conexionBean.openSession();


        try {
            System.out.println("JavaBean conexión correcta  \n");

            // 1. Contar cuántos videojuegos hay
            Long totalJuegos = session.createQuery("SELECT COUNT(v) FROM Videojuego v", Long.class).uniqueResult();
            System.out.println("Total de videojuegos en la base de datos: " + totalJuegos);

            // 2. Listar todos los videojuegos
            System.out.println("\nListado de videojuegos:");
            List<Videojuego> videojuegos = session.createQuery("FROM Videojuego", Videojuego.class).list();

            for (Videojuego v : videojuegos) {
                System.out.println("  - " + v.getTitulo() + " (" + v.getPrecio() + "€)");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            session.close(); //Cerramos sesión
            conexionBean.close(); //Cerramos la conexión con Bean
        }
    }
}