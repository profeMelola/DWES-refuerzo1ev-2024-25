package es.daw.web.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Categorias")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    //@OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL, orphanRemoval = true)
    /*
     * Si se borra un producto, no deben eliminarse las categorías asociadas. 
     * 
     * Lo más común es indicar las cascadas en la entidad que "posee" la relación, es decir, 
     * la entidad que representa el lado "uno" en una relación uno-a-muchos o muchos-a-muchos. 
     * En este caso, sería en la entidad Categoria, no en Producto. 
     * Esto se debe a que la entidad "poseedora" suele ser la que controla la vida de los elementos relacionados.
     */

     /*
      * Permite que las operaciones como persistir o actualizar en Categoria se propaguen a los productos.
      */
      
    //@OneToMany(mappedBy = "categoria", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL)
    private Set<Producto> productos; //orphanRemoval = true: elimina de la base de datos cualquier producto que se elimine de la lista productos del autor.

    public Categoria(){
        productos = new LinkedHashSet<>();
    }

    // Getters and Setters

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

    public void addProducto(Producto p){
        // 
        // Aseguro la consistencia en la relación bidireccional
        productos.add(p);
        p.setCategoria(this);
    }

    public void removeProducto(Producto p){
        productos.remove(p);
        p.setCategoria(null);
    }

    @Override
    public String toString() {
        //Esto da stackOverflow
        //return "Categoria [id=" + id + ", nombre=" + nombre + ", productos=" + productos + "]";
        
        return "Categoria [id=" + id + ", nombre=" + nombre + "]";
    }

    
}

