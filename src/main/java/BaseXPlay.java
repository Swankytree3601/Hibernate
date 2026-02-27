import org.basex.api.client.ClientSession;

import java.nio.file.Paths;

/**
 * @Autor Javier Tejera - 2 DAM
 */
public class BaseXPlay {
    public static void main(String[] args) {

        try (ClientSession session = new ClientSession("localhost", 1984, "admin", "admin")) {

            System.out.println("Conexión a BaseX correctamente.");

            String xmlPath = Paths.get("src/main/resources/videojuegos.xml").toAbsolutePath().toString();

            // Crear base de datos desde el archivo xml
            try {
                session.execute("CREATE DB videojuegos " + xmlPath); //Si no existe
                System.out.println("Base de datos 'videojuegos' creada");
            } catch (Exception e) {
                System.out.println("La base de datos 'videojuegos' ya existe");
            }

            try{
                session.execute("OPEN videojuegos");
            }catch (Exception e){
                System.out.println("Error: " + e.getMessage());
                return;
            }

            // Consultas
            System.out.println("\nTítulos:");
            String titulos = session.execute("XQUERY //titulo");
            System.out.println(titulos);

            System.out.println("\nVideojuegos caros:");
            String caros = session.execute("XQUERY //videojuego[precio > 50]/titulo");
            System.out.println(caros);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}