package models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "videojuego")
public class Videojuego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVideojuego")
    private int id;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "genero", nullable = false, length = 50)
    private String genero;

    @Column(name = "fechaLanzamiento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaLanzamiento;

    @Column(name = "precio", nullable = false, precision = 6, scale = 2)
    private BigDecimal precio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "desarrolladora_id", nullable = false)
    private Desarrolladora desarrolladora;

    @OneToMany(mappedBy = "videojuego", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Compra> compras;

    // Constructores
    public Videojuego(){ //Hibernate necesita un constructor vacío
    }

    public Videojuego(String titulo, String genero, Date fechaLanzamiento,
                      BigDecimal precio, Desarrolladora desarrolladora) {
        this.titulo = titulo;
        this.genero = genero;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precio = precio;
        this.desarrolladora = desarrolladora;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public Date getFechaLanzamiento() { return fechaLanzamiento; }
    public void setFechaLanzamiento(Date fechaLanzamiento) { this.fechaLanzamiento = fechaLanzamiento; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Desarrolladora getDesarrolladora() { return desarrolladora; }
    public void setDesarrolladora(Desarrolladora desarrolladora) { this.desarrolladora = desarrolladora; }

    public List<Compra> getCompras() { return compras; }
    public void setCompras(List<Compra> compras) { this.compras = compras; }

    @Override
    public String toString() {
        return "Videojuego{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", genero='" + genero + '\'' +
                ", fechaLanzamiento=" + fechaLanzamiento +
                ", precio=" + precio +
                '}';
    }
}