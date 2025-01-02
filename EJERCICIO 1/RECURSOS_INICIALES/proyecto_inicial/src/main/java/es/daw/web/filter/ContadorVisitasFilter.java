package es.daw.web.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/*")
public class ContadorVisitasFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización si es necesaria
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        int visitas = 1;

        System.out.println("*******************************************************************");
        System.out.println("********************** (FILTRO + COOKIE ) *************************");
        System.out.println("**********     NÚMERO DE VISITAS: "+visitas+"    ***************************");
        System.out.println("*******************************************************************");

        chain.doFilter(request, response);
        
    }

    @Override
    public void destroy() {
        // Limpieza si es necesaria
    }
}

