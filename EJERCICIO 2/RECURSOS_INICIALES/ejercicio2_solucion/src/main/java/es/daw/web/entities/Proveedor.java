package es.daw.web.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Proveedores")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String contacto;

    @ManyToMany(mappedBy = "proveedores")
    private List<Producto> productos = new ArrayList<>();

    // Getters y Setters
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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }


    // ---------------------------------------------------------------------
    // ESTO NO HARÍA FALTA SALVO QUE VÍA JAVA NECESITARA ACCEDER A UN PROVEEDOR PARA VER SU LISTA DE PRODUCTOS SIN CONSULTAR A LA BD
    public void addProducto(Producto prod) {
        this.productos.add(prod);
        prod.getProveedores().add(this);
    }

    public void removeProducto(Producto prod) {
        this.productos.remove(prod);
        prod.getProveedores().remove(this);
    }
    // ---------------------------------------------------------------------

    @Override
    public String toString() {
        return "Proveedor [nombre=" + nombre + ", contacto=" + contacto + "]";
    }

    
}

