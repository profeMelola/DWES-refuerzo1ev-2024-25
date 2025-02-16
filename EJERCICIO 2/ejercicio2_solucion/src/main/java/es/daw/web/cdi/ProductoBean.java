package es.daw.web.cdi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import es.daw.web.entities.Producto;
import es.daw.web.entities.Proveedor;
import es.daw.web.exceptions.JPAException;
import es.daw.web.repositories.CrudRepository;
import es.daw.web.repositories.JpaManagerCdi;
import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;

@Model
public class ProductoBean implements Serializable {
    Set<Producto> productos;
    Set<Proveedor> proveedores;

    @Inject
    CrudRepository<Producto> repoProducto;

    @Inject
    CrudRepository<Proveedor> repoProveedor;

    // parámetros
    private String nombre;
    private BigDecimal precio;
    private String sku;

    private String mensajeError;
    private String descripcionOperacion;

    public ProductoBean() {
        productos = new LinkedHashSet<>();
    }

    // ----------------------------------------------
    // Getters and Setters
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

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getDescripcionOperacion() {
        return descripcionOperacion;
    }
    // ---------------------------------------------

    /**
     * 
     * @return
     */
    public Set<Producto> getProductos() {
        try {
            productos = repoProducto.select();
            System.out.println("************ LISTADO DE PRODUCTOS ****************");
            for (Producto producto : productos) {
                System.out.println("* " + producto);
                proveedores = producto.getProveedores();
                if (proveedores.isEmpty()) {
                    System.out.println("\t\t* [WARNING] No hay proveedores asignados");
                }else{
                    for (Proveedor proveedor : proveedores) {
                        System.out.println("\t\t* Proveedor: " + proveedor);
                    }
                }
            }
            System.out.println("**************************************************");
            
            return productos;

        } catch (JPAException e) {
            e.printStackTrace();
        }

        return new LinkedHashSet<>();
    }

    /**
     * 
     * @return
     */
    public Set<Proveedor> getProveedores() {
        try {
            return repoProveedor.select();

        } catch (JPAException e) {
            e.printStackTrace();
        }

        return new LinkedHashSet<>();
    }

    // -------------------------------------------------------------------
    // ------------------------ MÉTODOS DE ACCIÓN ------------------------
    // -------------------------------------------------------------------

    /**
     * asignaProveedor
     * @param idProducto
     * @return
     */
    public String asignaProveedor(Long idProducto) {

        System.out.println("************ asignaProveedor ****************");
        System.out.println("* idProducto:" + idProducto);
        Long id_proveedor = 1L;

        try {
            Proveedor proveedor = repoProveedor.selectById(id_proveedor).get();

            System.out.println("* proveedor:" + proveedor);
            // Asignar el proveedor al producto
            Producto producto = repoProducto.selectById(idProducto).get();

            // También podría comprobar si ya está asignado el proveedor A, en ese caso devolver otro error.....
            producto.addProveedor(proveedor);
            // Esto sobraría, salvo que quisiera vía java tener la información actualizada. 
            // En BD con la sentencia anterior ya estaría el registro dado de alta en la tabla intermedia
            // Nosotros lo hacemos para tener la información actualizada en memoria
            proveedor.addProducto(producto);

            repoProducto.save(producto);

            System.out.println("* Asignado el proveedor al producto");

        } catch (JPAException e) {
            System.out.println("************ ERROR AL AÑADIR EL PROVEEDOR *******");
            e.printStackTrace();
            descripcionOperacion = "Error al asignar el proveedor al producto";
            mensajeError = JpaManagerCdi.getMessageError(e);
            System.out.println("* mensajeError:" + mensajeError);
            return "error";
        }

        return "productos??faces-redirect=true";
    }

    /**
     * desasignarProveedores
     * @param idProducto
     * @return
     */
    public String desasignarProveedores(Long idProducto){
        try {
            Producto producto = repoProducto.selectById(idProducto).get();

            if (producto.getProveedores().isEmpty()) {
                mensajeError = "[WARNING] No hay proveedores asignados al producto "+producto.getNombre();
                descripcionOperacion = "AVISO";
                return "error";
            }

            producto.getProveedores().forEach(proveedor ->{
                producto.removeProveedor(proveedor);

                // Esto sobraría, salvo que quisiera vía java tener la información actualizada. 
                // En BD con la sentencia anterior ya estaría el registro dado de alta en la tabla intermedia
                proveedor.removeProducto(producto);

            });
            //producto.getProveedores().clear();
            repoProducto.save(producto);
        } catch (JPAException e) {
            System.out.println("************ ERROR AL DESASIGNAR PROVEEDORES *******");
            e.printStackTrace();
            mensajeError = JpaManagerCdi.getMessageError(e);
            descripcionOperacion = "Error al desasignar los proveedores del producto";
            System.out.println("* mensajeError:" + mensajeError);
            return "error";
        }
        return "productos??faces-redirect=true";
    }
}
