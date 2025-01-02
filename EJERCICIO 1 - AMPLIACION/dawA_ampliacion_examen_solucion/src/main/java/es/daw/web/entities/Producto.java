package es.daw.web.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private BigDecimal precio;

    private String sku;

    @Column(name="fecha_registro")
    private LocalDateTime fechaRegistro;

    // Relación...
    //@ManyToOne(cascade = {CascadeType.MERGE,CascadeType.PERSIST})
    @ManyToOne
    //@JoinColumn(name="categoria_id", referencedColumnName = "id")
    @JoinColumn(name="categoria_id") // si no pongo referencedColumnName apunta al id de la tabla relacionada
    private Categoria categoria;

    // public Producto(){
    //     fechaRegistro = LocalDateTime.now();
    // }

    @PrePersist
    public void initFechaRegistro(){
        fechaRegistro = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


    @Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", sku=" + sku + ", fechaRegistro="
                + fechaRegistro + ", categoria=" + categoria + "]";
    }


    
}

