import org.basex.api.client.ClientSession;
import java.util.Scanner;

public class BaseXPlay {
    public static void main(String[] args) {
        try (ClientSession session = new ClientSession("localhost", 1984, "admin", "admin")) {

            System.out.println("Conexión a BaseX");

            // Crear base de datos
            try {
                session.execute("CREATE DB videojuegos");
                System.out.println("Base de datos 'videojuegos' creada");
            } catch (Exception e) {
                System.out.println("La base de datos ya existe");
            }

            // Insertar datos con XQUERY usando tres comillas
            String xmlData =
                    """
                    <videojuegos>
                      <videojuego id='1'>
                        <titulo>The Last of Us</titulo>
                        <genero>Acción</genero>
                        <precio>59.99</precio>
                        <desarrolladora>
                          <nombre>Naughty Dog</nombre>
                          <pais>USA</pais>
                        </desarrolladora>
                      </videojuego>
                      <videojuego id='2'>
                        <titulo>God of War</titulo>
                        <genero>Aventura</genero>
                        <precio>49.99</precio>
                        <desarrolladora>
                          <nombre>Santa Monica</nombre>
                          <pais>USA</pais>
                        </desarrolladora>
                      </videojuego>
                    </videojuegos>
                    """;

            // Insertar escapando las comillas correctamente
            session.execute("OPEN videojuegos");
            session.execute("XQUERY db:add('videojuegos', \"" + xmlData.replace("\"", "\\\"") + "\", 'videojuegos.xml')");
            System.out.println("Datos insertados correctamente");

            // Consultas
            System.out.println("\nTítulos:");
            String titulos = session.execute("XQUERY //titulo");
            System.out.println(titulos);

            System.out.println("\nVideojuegos caros:");
            String caros = session.execute("XQUERY //videojuego[precio > 50]/titulo");
            System.out.println(caros);

            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}