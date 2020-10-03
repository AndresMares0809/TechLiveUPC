package pe.edu.upc.techlive.models.repositories.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pe.edu.upc.techlive.models.entities.Historial;
import pe.edu.upc.techlive.models.repositories.HistorialRepository;

@Named
@ApplicationScoped
public class HistorialRepositoryImpl implements HistorialRepository, Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "TechLivePU")
	private EntityManager em;
	
	@Override
	public Historial save(Historial entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Historial update(Historial entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Historial> optional = findById(id);
		
		if(optional.isPresent())
			em.remove(optional.get());
		
	}

	@Override
	public List<Historial> findAll() throws Exception {
		List<Historial> historiales = new ArrayList<Historial>();
		String qlString = "SELECT c FROM Historial c";	// JPQL
		TypedQuery<Historial> query = em.createQuery(qlString, Historial.class);
		historiales = query.getResultList();
		return historiales;
	}

	@Override
	public Optional<Historial> findById(Integer id) throws Exception {
		Optional<Historial> Historial = Optional.empty();
		String qlString = "SELECT c FROM Historial c WHERE c.id = ?1";	// JPQL
		TypedQuery<Historial> query = em.createQuery(qlString, Historial.class);
		query.setParameter(1, id);
		List<Historial> historiales = query.getResultList();
		
		if(historiales != null && !historiales.isEmpty())
			Historial = Optional.of( historiales.get(0) );
		
		return Historial;
	}

	@Override
	public List<Historial> findByid(int id) {
		List<Historial> historiales = new ArrayList<Historial>();
		String qlString = "SELECT p FROM Historial p WHERE p.id LIKE '%?1%'";	// JPQL
		TypedQuery<Historial> query = em.createQuery(qlString, Historial.class);
		query.setParameter(1, id);
		historiales = query.getResultList();
		return historiales;
	}
}
