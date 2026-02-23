package models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "desarrolladora")
public class Desarrolladora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddesarrolladora") // Cambiado para coincidir con la BD
    private int id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "pais", nullable = false, length = 50)
    private String pais;

    @Column(name = "fundacion", nullable = false)
    private int fundacion;

    @OneToMany(mappedBy = "desarrolladora", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Videojuego> videojuegos;

    // Constructores
    public Desarrolladora() {}

    public Desarrolladora(String nombre, String pais, int fundacion) {
        this.nombre = nombre;
        this.pais = pais;
        this.fundacion = fundacion;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public int getFundacion() { return fundacion; }
    public void setFundacion(int fundacion) { this.fundacion = fundacion; }

    public List<Videojuego> getVideojuegos() { return videojuegos; }
    public void setVideojuegos(List<Videojuego> videojuegos) { this.videojuegos = videojuegos; }

    @Override
    public String toString() {
        return "Desarrolladora{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", pais='" + pais + '\'' +
                ", fundacion=" + fundacion +
                '}';
    }
}