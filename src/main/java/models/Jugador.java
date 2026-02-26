package models;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "jugador")
public class Jugador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idJugador")
    private int id;

    @Column(name = "nickname", nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "fechaRegistro", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaRegistro;

    @OneToMany(mappedBy = "jugador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Compra> compras;

    // Constructores

    public Jugador(){ //Hibernate necesita un constructor vacío
    }

    public Jugador(String nickname, String email, Date fechaRegistro) {
        this.nickname = nickname;
        this.email = email;
        this.fechaRegistro = fechaRegistro;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public List<Compra> getCompras() { return compras; }
    public void setCompras(List<Compra> compras) { this.compras = compras; }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}