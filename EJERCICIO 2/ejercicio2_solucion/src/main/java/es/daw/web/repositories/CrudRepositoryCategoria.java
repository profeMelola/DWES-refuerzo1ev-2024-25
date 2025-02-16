package es.daw.web.repositories;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import es.daw.web.entities.Categoria;
import es.daw.web.entities.Producto;
import es.daw.web.exceptions.JPAException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@RequestScoped
public class CrudRepositoryCategoria implements CrudRepository<Categoria>{

    @Inject
    EntityManager em;

    @Override
    public Set<Categoria> select() throws JPAException {
        try{
            List<Categoria> lista = em.createQuery("SELECT c FROM Categoria c",Categoria.class).getResultList();

            Set<Categoria> categorias = new LinkedHashSet<>(lista);

            System.out.println("*************** LISTADO DE CATEGORÍAS *****************");
            categorias.forEach(System.out::println);
    
            return categorias;

        }catch(Exception e){
            throw new JPAException(JpaManagerCdi.getMessageError(e));
        }
    }

    @Override
    public Optional<Categoria> selectById(Long id) throws JPAException {
        return Optional.ofNullable(em.find(Categoria.class,id));        
    }

    @Override
    public void deleteById(Long id) throws JPAException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public void save(Categoria t) throws JPAException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }
    
}
