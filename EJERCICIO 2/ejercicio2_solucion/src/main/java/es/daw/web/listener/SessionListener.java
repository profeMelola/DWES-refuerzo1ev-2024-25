package es.daw.web.listener;

import es.daw.web.cdi.LoginBean;
import es.daw.web.cdi.PreferencesBean;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener  {

    //private static int contadorCierreSesion = 0;
    private int contadorCierreSesion = 0;

    @Inject
    PreferencesBean preferencesBean;

    @Inject
    LoginBean loginBean;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Sesión creada para el usuario: " + se.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {

        System.out.println("******************** SESIÓN CERRADA ************************");

        contadorCierreSesion++;

        if (preferencesBean.getGenero() == null) {
            System.out.printf("* El usuario %s NO seleccionó ningún género favorito.", loginBean.getUsername());
        } else {
            System.out.printf("* El usuario %s seleccionó el género favorito <%s>",loginBean.getUsername(),preferencesBean.getGenero());
        }
        System.out.println("\n********************************************");
        System.out.println("*** Sesiones cerradas hasta el momento: " + contadorCierreSesion);
        System.out.println("************************************************");

        
    }


}
