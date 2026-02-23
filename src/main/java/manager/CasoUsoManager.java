package manager;

import models.*;
import utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CasoUsoManager {

    private Session session;
    private Transaction transaction;

    public void abrirSession() {
        session = HibernateUtil.getSessionFactory().openSession();
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

    // SELECCIONES SIMPLES
    public void seleccionSimpleVideojuegos() {
        System.out.println("\n--- SELECCIÓN SIMPLE: Todos los videojuegos ---");
        Query<Videojuego> query = session.createQuery("FROM Videojuego", Videojuego.class);
        List<Videojuego> videojuegos = query.list();
        for (Videojuego v : videojuegos) {
            System.out.println(v);
        }
    }

    public void seleccionSimpleJugadores() {
        System.out.println("\n--- SELECCIÓN SIMPLE: Jugadores registrados en 2024 ---");
        Query<Jugador> query = session.createQuery(
                "FROM Jugador j WHERE YEAR(j.fechaRegistro) = :year", Jugador.class);
        query.setParameter("year", 2024);
        List<Jugador> jugadores = query.list();
        for (Jugador j : jugadores) {
            System.out.println(j);
        }
    }

    public void seleccionSimpleDesarrolladoras() {
        System.out.println("\n--- SELECCIÓN SIMPLE: Desarrolladoras de USA ---");
        Query<Desarrolladora> query = session.createQuery(
                "FROM Desarrolladora d WHERE d.pais = :pais", Desarrolladora.class);
        query.setParameter("pais", "USA");
        List<Desarrolladora> desarrolladoras = query.list();
        for (Desarrolladora d : desarrolladoras) {
            System.out.println(d);
        }
    }

    // JOINS SOBRE DOS O MÁS TABLAS
    public void joinVideojuegoDesarrolladora() {
        System.out.println("\n--- JOIN: Videojuegos con su desarrolladora ---");
        String hql = "SELECT v.titulo, d.nombre, d.pais, v.precio " +
                "FROM Videojuego v " +
                "JOIN v.desarrolladora d";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        List<Object[]> resultados = query.list();
        for (Object[] fila : resultados) {
            System.out.println("Juego: " + fila[0] +
                    ", Desarrolladora: " + fila[1] +
                    ", País: " + fila[2] +
                    ", Precio: " + fila[3]);
        }
    }

    public void joinCompraJugadorVideojuego() {
        System.out.println("\n--- JOIN: Compras con jugador y videojuego ---");
        String hql = "SELECT c.fechaCompra, j.nickname, v.titulo, c.precioFinal " +
                "FROM Compra c " +
                "JOIN c.jugador j " +
                "JOIN c.videojuego v " +
                "ORDER BY c.fechaCompra DESC";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        List<Object[]> resultados = query.list();
        for (Object[] fila : resultados) {
            System.out.println("Fecha: " + fila[0] +
                    ", Jugador: " + fila[1] +
                    ", Juego: " + fila[2] +
                    ", Precio: " + fila[3]);
        }
    }

    public void joinTriple() {
        System.out.println("\n--- JOIN TRIPLE: Información completa de compras ---");
        String hql = "SELECT j.nickname, v.titulo, d.nombre, c.fechaCompra, c.precioFinal " +
                "FROM Compra c " +
                "JOIN c.jugador j " +
                "JOIN c.videojuego v " +
                "JOIN v.desarrolladora d " +
                "WHERE d.pais = :pais";
        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("pais", "USA");
        List<Object[]> resultados = query.list();
        for (Object[] fila : resultados) {
            System.out.println("Jugador: " + fila[0] +
                    ", Juego: " + fila[1] +
                    ", Desarrolladora: " + fila[2] +
                    ", Fecha: " + fila[3] +
                    ", Precio: " + fila[4]);
        }
    }

    // SENTENCIAS DML

    public void insertarDatosEjemplo() {
        System.out.println("\n--- DML INSERT: Insertando datos de ejemplo ---");

        // Verificar si ya existen datos
        Query<Long> countQuery = session.createQuery("SELECT COUNT(d) FROM Desarrolladora d", Long.class);
        Long count = countQuery.uniqueResult();

        if (count > 0) {
            System.out.println("Los datos ya existen en la base de datos. No se insertarán duplicados.");
            return;
        }

        // Insertar desarrolladoras
        Desarrolladora d1 = new Desarrolladora("Naughty Dog", "USA", 1984);
        Desarrolladora d2 = new Desarrolladora("CD Projekt Red", "Polonia", 1994);
        Desarrolladora d3 = new Desarrolladora("Nintendo", "Japón", 1889);

        session.save(d1);
        session.save(d2);
        session.save(d3);

        // Insertar videojuegos
        Videojuego v1 = new Videojuego("The Last of Us", "Acción",
                new Date(), new BigDecimal("59.99"), d1);
        Videojuego v2 = new Videojuego("The Witcher 3", "RPG",
                new Date(), new BigDecimal("39.99"), d2);
        Videojuego v3 = new Videojuego("Zelda Breath of the Wild", "Aventura",
                new Date(), new BigDecimal("69.99"), d3);

        session.save(v1);
        session.save(v2);
        session.save(v3);

        // Insertar jugadores
        Jugador j1 = new Jugador("gamer123", "gamer@email.com", new Date());
        Jugador j2 = new Jugador("proplayer", "pro@email.com", new Date());

        session.save(j1);
        session.save(j2);

        // Insertar compras
        Compra c1 = new Compra(j1, v1, new Date(), v1.getPrecio());
        Compra c2 = new Compra(j2, v2, new Date(), v2.getPrecio());
        Compra c3 = new Compra(j1, v3, new Date(), v3.getPrecio());

        session.save(c1);
        session.save(c2);
        session.save(c3);

        System.out.println("Datos insertados correctamente");
    }

    public void actualizarPrecios() {
        System.out.println("\n--- DML UPDATE: Actualizando precios ---");

        String hql = "UPDATE Videojuego v " +
                "SET v.precio = v.precio * 1.1 " +
                "WHERE v.genero = :genero";

        Query query = session.createQuery(hql);
        query.setParameter("genero", "RPG");
        int updatedCount = query.executeUpdate();

        System.out.println("Precios actualizados para " + updatedCount + " videojuegos");
    }

    public void eliminarComprasAntiguas() {
        System.out.println("\n--- DML DELETE: Eliminando compras antiguas ---");

        String hql = "DELETE FROM Compra c " +
                "WHERE c.fechaCompra < :fechaLimite";

        Query query = session.createQuery(hql);
        query.setParameter("fechaLimite", new Date(120, 0, 1)); // Año 2020
        int deletedCount = query.executeUpdate();

        System.out.println("Compras eliminadas: " + deletedCount);
    }

    // PROCEDIMIENTO / FUNCIÓN

    public void procedimientoEstadisticasJugador(String nickname) {
        System.out.println("\n--- PROCEDIMIENTO: Estadísticas del jugador " + nickname + " ---");

        // Total gastado por jugador
        String hqlTotalGastado =
                "SELECT SUM(c.precioFinal) " +
                        "FROM Compra c " +
                        "JOIN c.jugador j " +
                        "WHERE j.nickname = :nickname";

        Query<BigDecimal> queryGasto = session.createQuery(hqlTotalGastado, BigDecimal.class);
        queryGasto.setParameter("nickname", nickname);
        BigDecimal totalGastado = queryGasto.uniqueResult();

        // Número de juegos comprados
        String hqlNumJuegos =
                "SELECT COUNT(c) " +
                        "FROM Compra c " +
                        "JOIN c.jugador j " +
                        "WHERE j.nickname = :nickname";

        Query<Long> queryJuegos = session.createQuery(hqlNumJuegos, Long.class);
        queryJuegos.setParameter("nickname", nickname);
        Long numJuegos = queryJuegos.uniqueResult();

        // Juego más caro comprado
        String hqlJuegoCaro =
                "SELECT v.titulo, MAX(c.precioFinal) " +
                        "FROM Compra c " +
                        "JOIN c.videojuego v " +
                        "JOIN c.jugador j " +
                        "WHERE j.nickname = :nickname " +
                        "GROUP BY v.titulo " +
                        "ORDER BY MAX(c.precioFinal) DESC";

        Query<Object[]> queryCaro = session.createQuery(hqlJuegoCaro, Object[].class);
        queryCaro.setParameter("nickname", nickname);
        queryCaro.setMaxResults(1);
        Object[] juegoCaro = queryCaro.uniqueResult();

        // Mostrar resultados
        System.out.println("Jugador: " + nickname);
        System.out.println("Total gastado: $" + (totalGastado != null ? totalGastado : BigDecimal.ZERO));
        System.out.println("Número de juegos comprados: " + (numJuegos != null ? numJuegos : 0));
        if (juegoCaro != null) {
            System.out.println("Juego más caro: " + juegoCaro[0] + " ($" + juegoCaro[1] + ")");
        }
    }

    public void funcionTopJuegosPorGenero(String genero, int limite) {
        System.out.println("\n--- FUNCIÓN: Top " + limite + " juegos del género " + genero + " ---");

        String hql =
                "SELECT v.titulo, d.nombre, v.precio, COUNT(c) as numCompras " +
                        "FROM Videojuego v " +
                        "JOIN v.desarrolladora d " +
                        "LEFT JOIN v.compras c " +
                        "WHERE v.genero = :genero " +
                        "GROUP BY v.id, v.titulo, d.nombre, v.precio " +
                        "ORDER BY numCompras DESC";

        Query<Object[]> query = session.createQuery(hql, Object[].class);
        query.setParameter("genero", genero);
        query.setMaxResults(limite);

        List<Object[]> resultados = query.list();
        for (int i = 0; i < resultados.size(); i++) {
            Object[] fila = resultados.get(i);
            System.out.println((i+1) + ". " + fila[0] +
                    " - " + fila[1] +
                    " ($" + fila[2] + ")" +
                    " - Compras: " + fila[3]);
        }
    }
}