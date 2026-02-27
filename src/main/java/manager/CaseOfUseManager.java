package manager;

import models.*;
import utils.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CaseOfUseManager {

    private Session session;
    private Transaction transaction;

    public void abrirSession() {
        session = HibernateUtility.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }

    public void cerrarSession() {
        if (transaction != null && transaction.isActive()) {
            transaction.commit();
        }
        if (session != null && session.isOpen()) {
            session.close();
        }
    }

    // SELECTS SIMPLES
    public void seleccionSimpleVideojuegos() {
        List<Videojuego> lista = session.createQuery("FROM Videojuego", Videojuego.class).list();
        for (Videojuego v : lista) {
            System.out.println("  " + v.getTitulo() + " - " + v.getGenero() + " - " + v.getPrecio() + "€");
        }
    }

    // JOINS
    public void joinVideojuegoDesarrolladora() {
        String hql = "SELECT v.titulo, d.nombre, d.pais, v.precio FROM Videojuego v JOIN v.desarrolladora d";
        List<Object[]> resultados = session.createQuery(hql, Object[].class).list();

        for (Object[] fila : resultados) {
            System.out.println("  " + fila[0] + " - " + fila[1] + " (" + fila[2] + ") - " + fila[3] + "€");
        }
    }

    public void joinCompraJugadorVideojuego() {
        String hql = "SELECT c.fechaCompra, j.nickname, v.titulo, c.precioFinal " +
                "FROM Compra c JOIN c.jugador j JOIN c.videojuego v " +
                "ORDER BY c.fechaCompra DESC";
        List<Object[]> resultados = session.createQuery(hql, Object[].class).list();

        for (Object[] fila : resultados) {
            System.out.println("  " + fila[0] + " - " + fila[1] + " compro " + fila[2] + " (" + fila[3] + "€)");
        }
    }

    public void joinTriple() {
        System.out.println("Joining triple");
        String hql = "SELECT j.nickname, v.titulo, d.nombre, c.fechaCompra " +
                "FROM Compra c " +
                "JOIN c.jugador j " +
                "JOIN c.videojuego v " +
                "JOIN v.desarrolladora d " +
                "WHERE d.pais = 'USA'";
        List<Object[]> resultados = session.createQuery(hql, Object[].class).list();

        for (Object[] fila : resultados) {
            System.out.println("  " + fila[0] + " compro " + fila[1] + " de " + fila[2] + " el " + fila[3]);
        }
    }

    public void subconsultaEjemplo() {
        String hql = "FROM Videojuego v WHERE v.precio > (SELECT AVG(v2.precio) FROM Videojuego v2)";
        List<Videojuego> resultados = session.createQuery(hql, Videojuego.class).list();
        System.out.println("  Videojuegos con precio superior a la media:");
        for (Videojuego v : resultados) {
            System.out.println("    " + v.getTitulo() + " - " + v.getPrecio() + "€");
        }
    }

    // DML
    public void insertarDatosEjemplo() {
        Long count = session.createQuery("SELECT COUNT(d) FROM Desarrolladora d", Long.class).uniqueResult();

        if (count > 0) {
            System.out.println("  Los datos ya existen");
            return;
        }

        Desarrolladora d1 = new Desarrolladora("Naughty Dog", "USA", 1984);
        Desarrolladora d2 = new Desarrolladora("CD Projekt Red", "Polonia", 1994);
        Desarrolladora d3 = new Desarrolladora("Nintendo", "Japon", 1889);

        session.persist(d1);
        session.persist(d2);
        session.persist(d3);

        Videojuego v1 = new Videojuego("The Last of Us", "Accion", new Date(), new BigDecimal("59.99"), d1);
        Videojuego v2 = new Videojuego("The Witcher 3", "RPG", new Date(), new BigDecimal("39.99"), d2);
        Videojuego v3 = new Videojuego("Zelda BOTW", "Aventura", new Date(), new BigDecimal("69.99"), d3);

        session.persist(v1);
        session.persist(v2);
        session.persist(v3);

        Jugador j1 = new Jugador("Coscu777", "gamer@email.com", new Date());
        Jugador j2 = new Jugador("Manolo1234", "pro@email.com", new Date());

        session.persist(j1);
        session.persist(j2);

        session.persist(new Compra(j1, v1, new Date(), v1.getPrecio()));
        session.persist(new Compra(j2, v2, new Date(), v2.getPrecio()));
        session.persist(new Compra(j1, v3, new Date(), v3.getPrecio()));

        System.out.println("  Datos insertados correctamente");
    }

    public void actualizarPrecios() {
        Query query = session.createQuery("UPDATE Videojuego SET precio = precio * 1.1 WHERE genero = 'RPG'");
        int actualizados = query.executeUpdate();
        System.out.println("  Precios actualizados: " + actualizados + " juegos RPG");
    }

    public void eliminarComprasAntiguas() {
        Query query = session.createQuery("DELETE FROM Compra WHERE fechaCompra < :fecha");
        query.setParameter("fecha", new Date(120, 0, 1));
        int eliminados = query.executeUpdate();
        System.out.println("  Compras eliminadas: " + eliminados);
    }

    // PROCEDIMIENTOS
    public void procedimientoEstadisticasJugador(String nickname) {
        // Total gastado
        String hql1 = "SELECT SUM(c.precioFinal) FROM Compra c JOIN c.jugador j WHERE j.nickname = :nick";
        BigDecimal total = session.createQuery(hql1, BigDecimal.class)
                .setParameter("nick", nickname)
                .uniqueResult();

        // Numero de compras
        String hql2 = "SELECT COUNT(c) FROM Compra c JOIN c.jugador j WHERE j.nickname = :nick";
        Long numCompras = session.createQuery(hql2, Long.class)
                .setParameter("nick", nickname)
                .uniqueResult();

        System.out.println("  Jugador: " + nickname);
        System.out.println("  Total gastado: " + (total != null ? total + "€" : "0€"));
        System.out.println("  Juegos comprados: " + (numCompras != null ? numCompras : 0));
    }

    public void funcionTopJuegosPorGenero(String genero, int limite) {
        String hql = "SELECT v.titulo, d.nombre, v.precio, COUNT(c) as ventas " +
                "FROM Videojuego v " +
                "JOIN v.desarrolladora d " +
                "LEFT JOIN v.compras c " +
                "WHERE v.genero = :genero " +
                "GROUP BY v.id, v.titulo, d.nombre, v.precio " +
                "ORDER BY ventas DESC";

        List<Object[]> resultados = session.createQuery(hql, Object[].class)
                .setParameter("genero", genero)
                .setMaxResults(limite)
                .list();

        System.out.println("  Top " + limite + " juegos de " + genero + ":");
        int pos = 1;
        for (Object[] fila : resultados) {
            System.out.println("  " + pos + ". " + fila[0] + " - " + fila[1] + " (" + fila[2] + "€) - " + fila[3] + " ventas");
            pos++;
        }
    }
}