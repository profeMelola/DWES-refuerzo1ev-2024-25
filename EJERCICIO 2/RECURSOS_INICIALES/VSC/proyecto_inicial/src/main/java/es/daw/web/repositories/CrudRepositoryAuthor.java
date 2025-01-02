package es.daw.web.repositories;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import es.daw.web.entities.Author;
import es.daw.web.exceptions.JPAException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

// No hace falta que sea Serializable
@RequestScoped
public class CrudRepositoryAuthor implements CrudRepository<Author>,Serializable {

    @Inject
    private EntityManager em;

    @Override
    @Transactional
    public Set<Author> select() throws JPAException {
        return new HashSet<>(em.createQuery("SELECT a FROM Author a order by a.id",Author.class).getResultList());
    }

    @Override
    @Transactional
    public Optional<Author> selectById(Long id) throws JPAException {
        return Optional.ofNullable(em.find(Author.class,id));

    }

    @Override
    public void deleteById(Long id) throws JPAException {
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    @Transactional
    public void delete(Author t) throws JPAException {
        try{
            Author author = em.createQuery("SELECT a FROM Author a where a.name = :name",Author.class)
                .setParameter("name", t.getName())
                .getSingleResult();
            
            if (author != null)
                em.remove(author);

        }catch(Exception e){
            throw new JPAException(e.getMessage());
        }


    }

    @Override
    @Transactional
    public void save(Author t) throws JPAException {
        try{
            if (!existeAuthor(t)){
                em.persist(t);
            }else{
                throw new JPAException("Ya existe el autor "+t.getName());
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new JPAException(e.getMessage());
        }
    }

    /**
     * 
     * @param autor
     * @return
     */
    private boolean existeAuthor(Author autor){
        String jpql = "SELECT COUNT(a) FROM Author a where a.name = :nombre";
        Long cantidad = em.createQuery(jpql,Long.class)
            .setParameter("nombre",autor.getName())
            .getSingleResult();

        System.out.println("***** cantidad:"+cantidad);

        return cantidad > 0;

        
    }
    
}
