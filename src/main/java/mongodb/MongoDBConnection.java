package mongodb;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import java.util.Arrays;
import java.util.Scanner;
import static com.mongodb.client.model.Filters.*;

public class MongoDBConnection {
    public static void main(String[] args) {
        try {
            // Conexión a MongoDB
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("videojuegosDB");
            MongoCollection<Document> collection = database.getCollection("videojuegos");

            System.out.println("=== CONEXIÓN A MONGODB ===");
            System.out.println("Conectado a MongoDB correctamente");

            // Limpiar colección anterior
            collection.deleteMany(new Document());

            // Insertar documentos de ejemplo
            Document juego1 = new Document("titulo", "The Last of Us")
                    .append("genero", "Acción")
                    .append("precio", 59.99)
                    .append("desarrolladora", new Document("nombre", "Naughty Dog")
                            .append("pais", "USA")
                            .append("fundacion", 1984))
                    .append("jugadores", Arrays.asList("jugador1", "jugador2"));

            Document juego2 = new Document("titulo", "God of War")
                    .append("genero", "Aventura")
                    .append("precio", 49.99)
                    .append("desarrolladora", new Document("nombre", "Santa Monica")
                            .append("pais", "USA")
                            .append("fundacion", 1999))
                    .append("jugadores", Arrays.asList("jugador3"));

            Document juego3 = new Document("titulo", "FIFA 24")
                    .append("genero", "Deportes")
                    .append("precio", 69.99)
                    .append("desarrolladora", new Document("nombre", "EA Sports")
                            .append("pais", "Canadá")
                            .append("fundacion", 1991))
                    .append("jugadores", Arrays.asList("jugador1", "jugador4", "jugador5"));

            collection.insertMany(Arrays.asList(juego1, juego2, juego3));
            System.out.println("Documentos insertados correctamente");

            // EJEMPLOS DE FIND
            System.out.println("\n=== EJEMPLOS FIND ===");

            // Find 1: Todos los documentos
            System.out.println("1. Todos los videojuegos:");
            FindIterable<Document> todos = collection.find();
            for (Document doc : todos) {
                System.out.println("  - " + doc.getString("titulo") + " (" + doc.getString("genero") + ")");
            }

            // Find 2: Filtro por género
            System.out.println("\n2. Videojuegos de género 'Acción':");
            FindIterable<Document> accion = collection.find(eq("genero", "Acción"));
            for (Document doc : accion) {
                System.out.println("  - " + doc.getString("titulo"));
            }

            // Find 3: Filtro por precio mayor que
            System.out.println("\n3. Videojuegos con precio > 50:");
            FindIterable<Document> caros = collection.find(gt("precio", 50));
            for (Document doc : caros) {
                System.out.println("  - " + doc.getString("titulo") + " - $" + doc.getDouble("precio"));
            }

            // Find 4: Filtro por país de desarrolladora
            System.out.println("\n4. Videojuegos de desarrolladoras USA:");
            FindIterable<Document> usa = collection.find(eq("desarrolladora.pais", "USA"));
            for (Document doc : usa) {
                System.out.println("  - " + doc.getString("titulo") + " - " +
                        doc.get("desarrolladora", Document.class).getString("nombre"));
            }

            // Find 5: Proyección (solo ciertos campos)
            System.out.println("\n5. Solo títulos y precios:");
            FindIterable<Document> proyeccion = collection.find()
                    .projection(new Document("titulo", 1).append("precio", 1).append("_id", 0));
            for (Document doc : proyeccion) {
                System.out.println("  - " + doc.toJson());
            }

            // Find 6: Con AND
            System.out.println("\n6. Videojuegos de USA con precio < 60:");
            FindIterable<Document> and = collection.find(and(eq("desarrolladora.pais", "USA"), lt("precio", 60)));
            for (Document doc : and) {
                System.out.println("  - " + doc.getString("titulo"));
            }

            // Find 7: Con OR
            System.out.println("\n7. Videojuegos de Deportes o con precio < 50:");
            FindIterable<Document> or = collection.find(or(eq("genero", "Deportes"), lt("precio", 50)));
            for (Document doc : or) {
                System.out.println("  - " + doc.getString("titulo"));
            }

            Scanner sc = new Scanner(System.in);
            System.out.println("\nPresiona ENTER para ejecutar tu propio Find");
            sc.nextLine();

            System.out.println("Introduce el campo para filtrar (ej: genero, precio, desarrolladora.pais):");
            String campo = sc.nextLine();
            System.out.println("Introduce el valor:");
            String valor = sc.nextLine();

            FindIterable<Document> personalizado = collection.find(eq(campo, valor));
            System.out.println("Resultados:");
            for (Document doc : personalizado) {
                System.out.println("  - " + doc.toJson());
            }

            mongoClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}