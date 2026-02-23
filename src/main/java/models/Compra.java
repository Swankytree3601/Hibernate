package models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCompra") // Cambiado para coincidir con la BD
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jugador_id", nullable = false) // Coincide con la BD
    private Jugador jugador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "videojuego_id", nullable = false) // Coincide con la BD
    private Videojuego videojuego;

    @Column(name = "fechaCompra", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaCompra;

    @Column(name = "precioFinal", nullable = false, precision = 6, scale = 2)
    private BigDecimal precioFinal;

    // Constructores
    public Compra() {}

    public Compra(Jugador jugador, Videojuego videojuego, Date fechaCompra, BigDecimal precioFinal) {
        this.jugador = jugador;
        this.videojuego = videojuego;
        this.fechaCompra = fechaCompra;
        this.precioFinal = precioFinal;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Jugador getJugador() { return jugador; }
    public void setJugador(Jugador jugador) { this.jugador = jugador; }

    public Videojuego getVideojuego() { return videojuego; }
    public void setVideojuego(Videojuego videojuego) { this.videojuego = videojuego; }

    public Date getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(Date fechaCompra) { this.fechaCompra = fechaCompra; }

    public BigDecimal getPrecioFinal() { return precioFinal; }
    public void setPrecioFinal(BigDecimal precioFinal) { this.precioFinal = precioFinal; }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", jugador=" + jugador.getNickname() +
                ", videojuego=" + videojuego.getTitulo() +
                ", fechaCompra=" + fechaCompra +
                ", precioFinal=" + precioFinal +
                '}';
    }
}