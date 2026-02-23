package basex;

import org.basex.api.client.ClientSession;
import java.util.Scanner;

public class BaseXConnection {
    public static void main(String[] args) {
        try (ClientSession session = new ClientSession("localhost", 1984, "admin", "admin")) {

            System.out.println("CONEXIÓN A BASEX");
            System.out.println("Conectado a BaseX correctamente");

            // Crear una base de datos de ejemplo si no existe
            try {
                session.execute("CREATE DB videojuegos");
                System.out.println("Base de datos 'videojuegos' creada");
            } catch (Exception e) {
                System.out.println("La base de datos ya existe o no se pudo crear");
            }

            // Insertar datos de ejemplo
            String xmlData =
                    "<videojuegos>" +
                            "  <videojuego id='1'>" +
                            "    <titulo>The Last of Us</titulo>" +
                            "    <genero>Acción</genero>" +
                            "    <precio>59.99</precio>" +
                            "    <desarrolladora>" +
                            "      <nombre>Naughty Dog</nombre>" +
                            "      <pais>USA</pais>" +
                            "    </desarrolladora>" +
                            "  </videojuego>" +
                            "  <videojuego id='2'>" +
                            "    <titulo>God of War</titulo>" +
                            "    <genero>Aventura</genero>" +
                            "    <precio>49.99</precio>" +
                            "    <desarrolladora>" +
                            "      <nombre>Santa Monica</nombre>" +
                            "      <pais>USA</pais>" +
                            "    </desarrolladora>" +
                            "  </videojuego>" +
                            "</videojuegos>";

            session.execute("OPEN videojuegos");
            session.execute("ADD /videojuegos.xml " + xmlData);

            // EJEMPLOS DE XPATH
            System.out.println("\nEJEMPLOS XPATH");

            // XPath 1: Seleccionar todos los títulos
            String xpath1 = "//titulo";
            System.out.println("Títulos de videojuegos:");
            String result1 = session.execute("xpath //titulo");
            System.out.println(result1);

            // XPath 2: Videojuegos con precio > 50
            String xpath2 = "//videojuego[precio > 50]/titulo";
            System.out.println("\nVideojuegos con precio > 50:");
            String result2 = session.execute("xpath " + xpath2);
            System.out.println(result2);

            // EJEMPLOS DE XQUERY
            System.out.println("\nEjemplos XQuery ===");

            // XQuery 1: Consulta con join implícito
            String xquery1 =
                    "for $v in //videojuego " +
                            "return <juego>{$v/titulo} - {$v/precio}</juego>";
            System.out.println("Listado de juegos con precios:");
            String result3 = session.execute("xquery " + xquery1);
            System.out.println(result3);

            // XQuery 2: Consulta con agrupación
            String xquery2 =
                    "for $v in //videojuego " +
                            "let $pais := $v/desarrolladora/pais " +
                            "group by $pais " +
                            "return <pais nombre='{$pais}'> {count($v)} juegos</pais>";
            System.out.println("\nJuegos por país:");
            String result4 = session.execute("xquery " + xquery2);
            System.out.println(result4);

            Scanner sc = new Scanner(System.in);
            System.out.println("\nPresiona ENTER para ejecutar tu propia consulta XPath/XQuery");
            sc.nextLine();

            System.out.println("Introduce tu consulta:");
            String consulta = sc.nextLine();
            String resultado = session.execute(consulta);
            System.out.println("Resultado: " + resultado);

            session.execute("CLOSE");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}