package pe.edu.upc.techlive.models.repositories.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pe.edu.upc.techlive.models.entities.Producto;
import pe.edu.upc.techlive.models.repositories.ProductoRepository;

public class ProductoRepositoryImpl implements ProductoRepository, Serializable {
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "TechLivePU")
	private EntityManager em;
	
	@Override
	public Producto save(Producto entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Producto update(Producto entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Producto> optional = findById(id);
		
		if(optional.isPresent())
			em.remove(optional.get());
		
	}

	@Override
	public List<Producto> findAll() throws Exception {
		List<Producto> productos = new ArrayList<Producto>();
		String qlString = "SELECT c FROM Producto c";	// JPQL
		TypedQuery<Producto> query = em.createQuery(qlString, Producto.class);
		productos = query.getResultList();
		return productos;
	}

	@Override
	public Optional<Producto> findById(Integer id) throws Exception {
		Optional<Producto> Producto = Optional.empty();
		String qlString = "SELECT c FROM Producto c WHERE c.id = ?1";	// JPQL
		TypedQuery<Producto> query = em.createQuery(qlString, Producto.class);
		query.setParameter(1, id);
		List<Producto> productos = query.getResultList();
		
		if(productos != null && !productos.isEmpty())
			Producto = Optional.of( productos.get(0) );
		
		return Producto;
	}

	@Override
	public List<Producto> findByModelo(String modelo) throws Exception {
		List<Producto> productos = new ArrayList<Producto>();
		String qlString = "SELECT c FROM Producto c WHERE c.modelo LIKE ?1";	// JPQL
		TypedQuery<Producto> query = em.createQuery(qlString, Producto.class);
		query.setParameter(1, "%" + modelo + "%");
		productos = query.getResultList();
		return productos;
	}
}
