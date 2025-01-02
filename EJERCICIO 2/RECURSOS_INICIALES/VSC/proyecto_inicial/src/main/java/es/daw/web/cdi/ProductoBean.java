package es.daw.web.cdi;

import java.math.BigDecimal;

import jakarta.enterprise.inject.Model;

@Model
public class ProductoBean{

    private String nombre;
    private BigDecimal precio;
    private String sku;


    // PENDIENTE COMPLETAR!!!!


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

}
