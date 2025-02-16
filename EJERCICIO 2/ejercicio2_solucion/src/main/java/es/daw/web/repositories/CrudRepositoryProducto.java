package es.daw.web.repositories;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import es.daw.web.entities.Book;
import es.daw.web.entities.Producto;
import es.daw.web.exceptions.JPAException;
import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Model
public class CrudRepositoryProducto implements CrudRepository<Producto>{

    @Inject
    EntityManager em;
    
    @Override
    public Set<Producto> select() throws JPAException {

        try{
            List<Producto> lista = em.createQuery("SELECT p FROM Producto p order by p.nombre desc",Producto.class)
                .getResultList();

            return new LinkedHashSet<>(lista);

        }catch(Exception e){
            throw new JPAException(JpaManagerCdi.getMessageError(e));
        }
    }

    @Override
    public Optional<Producto> selectById(Long id) throws JPAException {
        try{
            Producto producto = em.find(Producto.class, id);
            return Optional.ofNullable(producto);
        }catch(Exception e){
            throw new JPAException(JpaManagerCdi.getMessageError(e));
        }
    }

    @Override
    @Transactional
    public void save(Producto t) throws JPAException {
        try{
            em.persist(t);
            em.flush();
        }catch(Exception e){
            throw new JPAException(JpaManagerCdi.getMessageError(e));
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws JPAException {
        try{
            System.out.println("* Borrando producto con id "+id);
            if (selectById(id).isPresent()){
                Producto producto = selectById(id).get();
                System.out.println("* Producto encontrado: "+producto);
                em.remove(producto);
                System.out.println("* Producto borrado");
            }
            
            em.flush();
        }catch(Exception e){
            throw new JPAException(JpaManagerCdi.getMessageError(e));
        }        
    }

  
    
}
