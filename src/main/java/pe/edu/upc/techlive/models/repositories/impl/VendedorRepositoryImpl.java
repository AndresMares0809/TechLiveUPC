package pe.edu.upc.techlive.models.repositories.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pe.edu.upc.techlive.models.entities.Vendedor;
import pe.edu.upc.techlive.models.repositories.VendedorRepository;

public class VendedorRepositoryImpl implements VendedorRepository, Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "TechLivePU")
	private EntityManager em;
	
	@Override
	public Vendedor save(Vendedor entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Vendedor update(Vendedor entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Vendedor> optional = findById(id);
		
		if(optional.isPresent())
			em.remove(optional.get());
		
	}

	@Override
	public List<Vendedor> findAll() throws Exception {
		List<Vendedor> vendedores = new ArrayList<Vendedor>();
		String qlString = "SELECT c FROM Vendedor c";	// JPQL
		TypedQuery<Vendedor> query = em.createQuery(qlString, Vendedor.class);
		vendedores = query.getResultList();
		return vendedores;
	}

	@Override
	public Optional<Vendedor> findById(Integer id) throws Exception {
		Optional<Vendedor> Vendedor = Optional.empty();
		String qlString = "SELECT c FROM Vendedor c WHERE c.id = ?1";	// JPQL
		TypedQuery<Vendedor> query = em.createQuery(qlString, Vendedor.class);
		query.setParameter(1, id);
		List<Vendedor> vendedores = query.getResultList();
		
		if(vendedores != null && !vendedores.isEmpty())
			Vendedor = Optional.of( vendedores.get(0) );
		
		return Vendedor;
	}

	@Override
	public List<Vendedor> findByNombre(String nombre) throws Exception {
		List<Vendedor> vendedores = new ArrayList<Vendedor>();
		String qlString = "SELECT c FROM Vendedor c WHERE c.nombre LIKE ?1";	// JPQL
		TypedQuery<Vendedor> query = em.createQuery(qlString, Vendedor.class);
		query.setParameter(1, "%" + nombre + "%");
		vendedores = query.getResultList();
		return vendedores;
	}

	@Override
	public List<Vendedor> findByDireccion(String direccion) throws Exception {
		List<Vendedor> vendedores = new ArrayList<Vendedor>();
		String qlString = "SELECT c FROM Vendedor c WHERE c.direccion LIKE '%?1%'";	// JPQL
		TypedQuery<Vendedor> query = em.createQuery(qlString, Vendedor.class);
		query.setParameter(1, direccion);
		vendedores = query.getResultList();
		return vendedores;
	}

	@Override
	public Optional<Vendedor> findByRuc(String ruc) throws Exception {
		Optional<Vendedor> Vendedor = Optional.empty();
		String qlString = "SELECT c FROM Vendedor c WHERE c.ruc = ?1";	
		TypedQuery<Vendedor> query = em.createQuery(qlString, Vendedor.class);
		query.setParameter(1, ruc);
		List<Vendedor> vendedores = query.getResultList();
		
		if(vendedores != null && !vendedores.isEmpty())
			Vendedor = Optional.of( vendedores.get(0) );
		
		return Vendedor;
	}
}
