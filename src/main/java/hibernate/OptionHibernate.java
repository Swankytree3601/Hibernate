package hibernate;

import manager.CasoUsoManager;
import java.util.Scanner;

public class OptionHibernate {

    public void choseOption(){
        Scanner sc = new Scanner(System.in);
        int option;

        do {
            System.out.println("\nSistema de gestión de videojuegos - Hibernate");
            System.out.println("¿Qué quieres hacer?");
            System.out.println("1. Probar conexión de la base de datos.");
            System.out.println("2. Selecciones simples y Joins.");
            System.out.println("3. Operaciones DML (Insert, Update, Delete).");
            System.out.println("4. Procedimientos y Funciones.");
            System.out.println("5. Ejecutar todo el caso de uso completo.");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            option = sc.nextInt();

            switch (option) {
                case 1:
                    option1();
                    break;
                case 2:
                    option2();
                    break;
                case 3:
                    option3();
                    break;
                case 4:
                    option4();
                    break;
                case 5:
                    option5();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }

        } while (option != 0);

        sc.close();
    }

    protected void option1(){
        System.out.println("\nPROBANDO CONEXIÓN A BASE DE DATOS");
        CasoUsoManager manager = new CasoUsoManager();
        try {
            manager.abrirSession();
            System.out.println("Conexión exitosa a la base de datos");
        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
        } finally {
            manager.cerrarSession();
        }
    }

    protected void option2(){
        System.out.println("\nSELECCIONES SIMPLES Y JOINS");
        CasoUsoManager manager = new CasoUsoManager();
        try {
            manager.abrirSession();

            manager.seleccionSimpleVideojuegos();
            manager.seleccionSimpleJugadores();
            manager.seleccionSimpleDesarrolladoras();

            manager.joinVideojuegoDesarrolladora();
            manager.joinCompraJugadorVideojuego();
            manager.joinTriple();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            manager.cerrarSession();
        }
    }

    protected void option3(){
        System.out.println("\nOPERACIONES DML");
        CasoUsoManager manager = new CasoUsoManager();
        try {
            manager.abrirSession();

            manager.insertarDatosEjemplo();
            manager.actualizarPrecios();
            manager.eliminarComprasAntiguas();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            manager.cerrarSession();
        }
    }

    protected void option4(){
        System.out.println("\nPROCEDIMIENTOS Y FUNCIONES");
        CasoUsoManager manager = new CasoUsoManager();
        try {
            manager.abrirSession();

            Scanner sc = new Scanner(System.in);
            System.out.print("Introduce nickname del jugador para estadísticas: ");
            String nickname = sc.nextLine();
            manager.procedimientoEstadisticasJugador(nickname);

            System.out.print("\nIntroduce género para ver top juegos: ");
            String genero = sc.nextLine();
            manager.funcionTopJuegosPorGenero(genero, 5);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            manager.cerrarSession();
        }
    }

    protected void option5(){
        System.out.println("\nEJECUTANDO CASO DE USO COMPLETO");
        CasoUsoManager manager = new CasoUsoManager();
        try {
            manager.abrirSession();

            System.out.println("\n1. INSERTANDO DATOS DE EJEMPLO...");
            manager.insertarDatosEjemplo();

            System.out.println("\n2. SELECCIONES SIMPLES...");
            manager.seleccionSimpleVideojuegos();
            manager.seleccionSimpleJugadores();

            System.out.println("\n3. JOINS...");
            manager.joinVideojuegoDesarrolladora();
            manager.joinCompraJugadorVideojuego();

            System.out.println("\n4. ACTUALIZACIONES...");
            manager.actualizarPrecios();

            System.out.println("\n5. PROCEDIMIENTOS...");
            manager.procedimientoEstadisticasJugador("gamer123");
            manager.funcionTopJuegosPorGenero("Acción", 3);

            System.out.println("\nCaso de uso completado exitosamente");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            manager.cerrarSession();
        }
    }
}