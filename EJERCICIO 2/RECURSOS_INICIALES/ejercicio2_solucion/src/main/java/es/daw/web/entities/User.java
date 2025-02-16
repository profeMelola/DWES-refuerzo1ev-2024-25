package es.daw.web.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users") // Nombre de la tabla en la base de datos
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID autoincremental en H2
    @Column(name = "user_id") // Nombre de la columna en la tabla
    private Integer id;

    @Column(name = "username", length = 50, unique = true, nullable = false) 
    private String username; // Nombre de usuario único, no nulo

    @Column(name = "password", nullable = false)
    private String password; // Contraseña no nula

    /*
     * El uso de CascadeType.ALL podría ser muy arriesgado aquí porque permite también las operaciones de eliminación en cascada (REMOVE), 
     * lo cual puede provocar que al eliminar un User, también se eliminen roles que pueden estar asociados con otros usuarios. 
     * Esto normalmente no es deseable en una relación muchos-a-muchos, ya que los roles suelen ser entidades compartidas.
     * Usando únicamente CascadeType.PERSIST y CascadeType.MERGE garantizas que:
     * PERSIST: Cuando un User se guarda por primera vez, todos los roles asignados también se guardarán automáticamente si aún no existen en la base de datos.
     * MERGE: Al actualizar un User, las entidades Role relacionadas también se actualizarán si corresponde.
     */
    // Relación muchos-a-muchos con Role, con tabla intermedia user_roles

    /*
     * Estrategia de carga (fetch = FetchType.LAZY)
     * Utilizada para optimizar el rendimiento cargando las relaciones muchos-a-muchos solo cuando se necesiten. 
     * Esto es útil en relaciones complejas para evitar que se carguen automáticamente grandes conjuntos de datos.
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "user_roles", // Nombre de la tabla de unión
        joinColumns = @JoinColumn(name = "user_id"), // Columna en user_roles que hace referencia a User
        inverseJoinColumns = @JoinColumn(name = "role_id") // Columna en user_roles que hace referencia a Role
    )
    private Set<Rol> roles = new HashSet<>();

    // Constructor vacío para JPA
    public User() {}

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    // Método para agregar un rol a un usuario
    public void addRole(Rol role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    // Método para eliminar un rol de un usuario
    public void removeRole(Rol role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}

