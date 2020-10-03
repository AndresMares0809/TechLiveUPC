package pe.edu.upc.techlive.models.repositories.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pe.edu.upc.techlive.models.entities.Marca;
import pe.edu.upc.techlive.models.repositories.MarcaRepository;

public class MarcaRepositoryImpl implements MarcaRepository, Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "TechLivePU")
	private EntityManager em;
	
	@Override
	public Marca save(Marca entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Marca update(Marca entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Marca> optional = findById(id);
		
		if(optional.isPresent())
			em.remove(optional.get());
		
	}

	@Override
	public List<Marca> findAll() throws Exception {
		List<Marca> marcas = new ArrayList<Marca>();
		String qlString = "SELECT c FROM Marca c";	// JPQL
		TypedQuery<Marca> query = em.createQuery(qlString, Marca.class);
		marcas = query.getResultList();
		return marcas;
	}

	@Override
	public Optional<Marca> findById(Integer id) throws Exception {
		Optional<Marca> Marca = Optional.empty();
		String qlString = "SELECT c FROM Marca c WHERE c.id = ?1";	// JPQL
		TypedQuery<Marca> query = em.createQuery(qlString, Marca.class);
		query.setParameter(1, id);
		List<Marca> marcas = query.getResultList();
		
		if(marcas != null && !marcas.isEmpty())
			Marca = Optional.of( marcas.get(0) );
		
		return Marca;
	}

	@Override
	public List<Marca> findByNombre(String nombre) throws Exception {
		List<Marca> marcas = new ArrayList<Marca>();
		String qlString = "SELECT c FROM Marca c WHERE c.nombre LIKE ?1";	// JPQL
		TypedQuery<Marca> query = em.createQuery(qlString, Marca.class);
		query.setParameter(1, "%" + nombre + "%");
		marcas = query.getResultList();
		return marcas;
	}
}
