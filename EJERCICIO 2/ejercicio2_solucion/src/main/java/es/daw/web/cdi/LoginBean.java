package es.daw.web.cdi;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.Serializable;
import java.util.Optional;

import es.daw.web.entities.User;
import es.daw.web.exceptions.JPAException;
import es.daw.web.repositories.CrudRepositoryUser;
import es.daw.web.repositories.JpaManagerCdi;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {
    private String username;
    private String password;

    private boolean isAdmin;

    @Inject
    CrudRepositoryUser repoUser;

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

    public boolean isAdmin() {
        return isAdmin;
    }

    /**
     * Establece si el usuario es administrador y redirige al usuario a la página
     * main.xhtml si la validación es correcta
     * 1. Verificar si el usuario existe en la base de datos (en la tabla USERS).
     * 2. Verificar que la contraseña coincida.
     * 3. Verificar si el usuario tiene el rol adecuado (admin o cliente) en la
     * tabla
     * ROLES.
     * 
     * @return
     */
    public String login() {

        System.out.println("*********** LOGIN ************");

        try {
            // 1. Verificar si el usuario existe
            User user = new User();
            user.setUsername(username);
            Optional<User> optUser = repoUser.selectByPropiedad(user);

            if (optUser.isEmpty()) {
                System.out.println("****** NO EXISTE EL USER EN EL SISTEMA ******");
                throw new JPAException("El usuario <" + username + "> no existe en el sistema.");
            }

            // 2. Verificar que la contraseña coincida.
            User usuario = optUser.get(); // no está vacío porque se ha comprobado antes
            if (!usuario.getPassword().equals(password)) {
                System.out.println("****** LA CONTRASEÑA NO ES CORRECTA ******");
                throw new JPAException("La contraseña <" + password + "> no es correcta.");
            }

            // 3. Verificar si el usuario tiene el rol adecuado (admin o cliente) en la
            // tabla
            isAdmin = repoUser.isAdmin(username);
            System.out.println("****** ES ADMIN (" + isAdmin + ") ******");

            // -------------------------------
            // PENDIENTE COMPLETAR!!!!
            // EXAMEN RECUPERACIÓN: GUARDAR EN LA SESSIÓN QUE EL USUARIO ESTÁ LOGADO
            // HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            // session.setAttribute("username", username);

            
            // -------------------------------

        } catch (JPAException e) {
            System.out.println("****** JPAEXCEPTION ******");
            System.out.println(JpaManagerCdi.getMessageError(e));

            // Capturamos la excepción y mostramos un mensaje de error en la interfaz
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de inicio de sesión", " - " + e.getMessage()));

            return null;

        }

        // Redirigimos a la página principal
        // JSF realiza una nueva solicitud HTTP hacia main.xhtml.
        // Cambia la URL en la barra del navegador a main.xhtml y hace que la página se
        // recargue completamente.
        return "main?faces-redirect=true";

    }

    
    
    public boolean canUpdate() {
        return isAdmin;
    }

    public boolean canSelect() {
        return true; // Todos los usuarios pueden seleccionar
    }

    /* ******************** EXAMEN DE RECUPERACIÓN ************************ */
    /**
     * PENDIENTE COMPLETAR!!!!
     * Este método devuelve true si el usuario está logado y false en caso contrario.
     * Para ello en la sessión debe existir el atributo "username".
     * @return
     */
    // public boolean isLoggedIn() {
      
    //     // ¿PODRÍA COMPROBARSE SI ESTÁ LOGADO O SIN USAR LA SESIÓN????

    //     HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    //     // Si no hay sessión activa devuelve null
    //     return session != null && session.getAttribute("username") != null;
    // }

    /**
     * 
     * @return
     */
    public String logout() {
        System.out.println("********************* LOGOUT **********************");
        // Limpia la sesión del usuario, eliminando cualquier atributo almacenado en la
        // sesión.
        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession(); // Invalidar la sesión
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.invalidate();

        // --------------------- ELIMINAR LA COOKIE DE PREFERENCIAS -----------------------------
        // Buscar la cookie de preferencias y eliminarla
        // MEJORA... COMPROBAR PREVIAMENTE QUE EXISTE LA COOKIE... GETCOOKIES()
        Cookie cookie = new Cookie("genero", ""); // limpio la cookie
        cookie.setMaxAge(0); // Indicamos que la cookie ha expirado
        cookie.setPath("/"); // Aseguramos que se elimina en toda la aplicación
        
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
                .getResponse();
        response.addCookie(cookie);

        System.out.println("[LOGOUT] Cookie de preferencias eliminada.");

        //return "login?faces-redirect=true"; // Redirigir a login.xhtml
        return "index?faces-redirect=true"; // Redirigir a index.html

    }    
}
