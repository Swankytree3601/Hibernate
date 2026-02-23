package consultas;

import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class EjemplosHQL {

    // EJEMPLO 1: JOIN entre Videojuego y Desarrolladora
    public void ejemploJoin(Session session) {
        String hql = "SELECT v.titulo, d.nombre, v.precio " +
                "FROM Videojuego v " +
                "JOIN v.desarrolladora d " +
                "WHERE d.pais = :pais";

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("pais", "USA");

        List<Object[]> resultados = query.list();
        System.out.println("JOIN: Videojuegos de USA");
        for (Object[] fila : resultados) {
            System.out.println("Juego: " + fila[0] +
                    ", Desarrolladora: " + fila[1] +
                    ", Precio: " + fila[2]);
        }
    }

    // EJEMPLO 2: SUBCONSULTA
    public void ejemploSubconsulta(Session session) {
        String hql = "SELECT v.titulo, v.precio " +
                "FROM Videojuego v " +
                "WHERE v.precio > (SELECT AVG(v2.precio) FROM Videojuego v2)";

        Query<Object[]> query = session.createQuery(hql, Object[].class);

        List<Object[]> resultados = query.list();
        System.out.println("\nSUBCONSULTA: Juegos con precio > media");
        for (Object[] fila : resultados) {
            System.out.println("Juego: " + fila[0] + ", Precio: " + fila[1]);
        }
    }

    // EJEMPLO 3: JOIN con condiciones y agrupación
    public void ejemploJoinComplejo(Session session) {
        String hql = "SELECT d.nombre, COUNT(v), AVG(v.precio) " +
                "FROM Desarrolladora d " +
                "LEFT JOIN d.videojuegos v " +
                "WHERE d.fundacion > :year " +
                "GROUP BY d.nombre " +
                "HAVING COUNT(v) > :minJuegos";

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("year", 1990);
        query.setParameter("minJuegos", 0L);

        List<Object[]> resultados = query.list();
        System.out.println("\nJOIN COMPLEJO: Estadísticas por desarrolladora");
        for (Object[] fila : resultados) {
            System.out.println("Desarrolladora: " + fila[0] +
                    ", Juegos: " + fila[1] +
                    ", Precio medio: " + fila[2]);
        }
    }
}