package es.daw.web.repositories;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import es.daw.web.entities.Book;
import es.daw.web.exceptions.JPAException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@RequestScoped
public class CrudRepositoryBook implements CrudRepository<Book>,Serializable {

    @Inject
    private EntityManager em;

    @Override
    @Transactional
    public Set<Book> select() throws JPAException {
        List<Book> lista = em.createQuery("SELECT b FROM Book b order by b.id desc",Book.class).getResultList();

        Set<Book> conjuntoOrdenado = new LinkedHashSet<>(lista);

        return conjuntoOrdenado;

    }

    @Override
    @Transactional // no especificamos readOnly...
    public Optional<Book> selectById(Long id) throws JPAException {
        return Optional.ofNullable(em.find(Book.class,id));

    }

    @Override
    public void deleteById(Long id) throws JPAException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public void save(Book t) throws JPAException {
    }
    
}
