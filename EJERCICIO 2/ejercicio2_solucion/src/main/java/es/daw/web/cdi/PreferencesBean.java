package es.daw.web.cdi;

import java.io.Serializable;


import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@SessionScoped
//@Named("preferencesBean")
@Named
public class PreferencesBean implements Serializable{

    private String genero;

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    /*
   
    Utilizar cookie para almacenar el género preferido del usuario y luego borrarla automáticamente 
    cuando se cierre la sesión. 
    Esto te permite evaluar a los alumnos sobre el manejo de cookies y también acerca de cómo gestionan los eventos 
    relacionados con la sesión (como su creación y destrucción).              
    */    
    public String salvarPreferencia(){

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        // Crear una cookie para guardar las preferencias
        Cookie cookie = new Cookie("genero", genero);
        cookie.setMaxAge(60 * 60 * 24 * 7); // Duración de 1 semana
        cookie.setPath("/"); // Hacerla accesible en toda la aplicación

        // Añadir la cookie a la respuesta HTTP
        response.addCookie(cookie);

        System.out.println("Cookie creada con genero: " + genero);

        //return "main.xhtml"; // Permanecer en la misma página
        return null;

    }
    
}
