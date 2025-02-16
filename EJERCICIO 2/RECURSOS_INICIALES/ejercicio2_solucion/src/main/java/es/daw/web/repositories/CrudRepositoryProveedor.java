package es.daw.web.repositories;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import es.daw.web.entities.Producto;
import es.daw.web.entities.Proveedor;
import es.daw.web.exceptions.JPAException;
import jakarta.enterprise.inject.Model;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Model
public class CrudRepositoryProveedor implements CrudRepository<Proveedor>{

    @Inject
    EntityManager em;

    @Override
    public Set<Proveedor> select() throws JPAException {
        try{
            List<Proveedor> lista = em.createQuery("SELECT p FROM Proveedor p",Proveedor.class)
                .getResultList();

            return new LinkedHashSet<>(lista);

        }catch(Exception e){
            throw new JPAException(JpaManagerCdi.getMessageError(e));
        }
    }

    @Override
    public Optional<Proveedor> selectById(Long id) throws JPAException {
        try{
            Proveedor p = em.find(Proveedor.class, id);
            return Optional.ofNullable(p);
        }catch(Exception e){
            throw new JPAException(JpaManagerCdi.getMessageError(e));
        }
    }

    @Override
    public void deleteById(Long id) throws JPAException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    @Transactional
    public void save(Proveedor t) throws JPAException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

}
