package hibernate;

import manager.CaseOfUseManager;
import java.util.Scanner;

public class OptionHibernate {

    private CaseOfUseManager manager;
    private Scanner sc;

    public void choseOption() {
        manager = new CaseOfUseManager();
        sc = new Scanner(System.in);
        int option;

        do {
            System.out.println("\nSistema de videojuegos");
            System.out.println("1. Probar conexion");
            System.out.println("2. Selecciones y Joins");
            System.out.println("3. Operaciones DML");
            System.out.println("4. Procedimientos");
            System.out.println("5. Caso completo");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");

            option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    option1TestConnection();
                    break;
                case 2:
                    option2RunQueries();
                    break;
                case 3:
                    option3RunDML();
                    break;
                case 4:
                    option4RunProcedures();
                    break;
                case 5:
                    option5RunFullCase();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        } while (option != 0);

        sc.close();
    }

    private void option1TestConnection() {
        try {
            manager.abrirSession();
            System.out.println("Conexion: OK");
            manager.cerrarSession();
        } catch (Exception e) {
            System.out.println("Conexión: error - " + e.getMessage());
        }
    }

    private void option2RunQueries() {
        try {
            manager.abrirSession();
            System.out.println("\n--- Todos los videojuegos ---");
            manager.seleccionSimpleVideojuegos();

            System.out.println("\n--- Videojuegos con desarrolladora ---");
            manager.joinVideojuegoDesarrolladora();

            System.out.println("\n--- Compras recientes ---");
            manager.joinCompraJugadorVideojuego();

            manager.cerrarSession();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void option3RunDML() {
        try {
            manager.abrirSession();
            System.out.println("\n--- Insertar datos ---");
            manager.insertarDatosEjemplo();

            System.out.println("\n--- Actualizar datos ---");
            manager.actualizarPrecios();

            System.out.println("\n--- Eliminando compras antiguas ---");
            manager.eliminarComprasAntiguas();

            manager.cerrarSession();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void option4RunProcedures() {
        try {
            manager.abrirSession();

            System.out.print("\nNickname del jugador: ");
            String nick = sc.nextLine();
            manager.procedimientoEstadisticasJugador(nick);

            System.out.print("\nGenero para top juegos: ");
            String genero = sc.nextLine();
            manager.funcionTopJuegosPorGenero(genero, 5);

            manager.cerrarSession();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void option5RunFullCase() {
        try {
            manager.abrirSession();

            System.out.println("\n--- 1. Insertando datos ---");
            manager.insertarDatosEjemplo();

            System.out.println("\n--- 2. Listando juegos ---");
            manager.seleccionSimpleVideojuegos();

            System.out.println("\n--- 3. Join con desarrolladora ---");
            manager.joinVideojuegoDesarrolladora();

            System.out.println("\n--- 4. Actualizando precios ---");
            manager.actualizarPrecios();

            System.out.println("\n--- 5. Estadísticas ---");
            manager.procedimientoEstadisticasJugador("Coscu777");

            manager.cerrarSession();
            System.out.println("\n--- CASO COMPLETADO ---");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}