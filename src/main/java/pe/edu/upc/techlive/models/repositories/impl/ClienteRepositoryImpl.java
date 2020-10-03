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

import pe.edu.upc.techlive.models.entities.Cliente;
import pe.edu.upc.techlive.models.repositories.ClienteRepository;

@Named
@ApplicationScoped
public class ClienteRepositoryImpl implements ClienteRepository, Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "TechLivePU")
	private EntityManager em;

	@Override
	public Cliente save(Cliente entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Cliente update(Cliente entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Cliente> optional = findById(id);
		if( optional.isPresent() )
			em.remove( optional.get() );
	}

	@Override
	public List<Cliente> findAll() throws Exception {
		List<Cliente> clientes = new ArrayList<Cliente>();
		String qlString = "SELECT c FROM Cliente c";	// JPQL
		TypedQuery<Cliente> query = em.createQuery(qlString, Cliente.class);
		clientes = query.getResultList();
		return clientes;
	}

	@Override
	public Optional<Cliente> findById(Integer id) throws Exception {
		Optional<Cliente> cliente = Optional.empty();
		String qlString = "SELECT c FROM Cliente c WHERE c.id = ?1";	// JPQL
		TypedQuery<Cliente> query = em.createQuery(qlString, Cliente.class);
		query.setParameter(1, id);
		List<Cliente> clientes = query.getResultList();
		
		if(clientes != null && !clientes.isEmpty())
			cliente = Optional.of( clientes.get(0) );
		
		return cliente;
	}


	@Override
	public List<Cliente> findByDireccion(String direccion) throws Exception {
		List<Cliente> clientes = new ArrayList<Cliente>();
		String qlString = "SELECT c FROM Cliente c WHERE c.direccion LIKE '%?1%'";	// JPQL
		TypedQuery<Cliente> query = em.createQuery(qlString, Cliente.class);
		query.setParameter(1, direccion);
		clientes = query.getResultList();
		return clientes;
	}

	@Override
	public Optional<Cliente> findByDni(String dni) throws Exception {
		Optional<Cliente> cliente = Optional.empty();
		String qlString = "SELECT c FROM Cliente c WHERE c.dni = ?1";	
		TypedQuery<Cliente> query = em.createQuery(qlString, Cliente.class);
		query.setParameter(1, dni);
		List<Cliente> clientes = query.getResultList();
		
		if(clientes != null && !clientes.isEmpty())
			cliente = Optional.of( clientes.get(0) );
		
		return cliente;
	}
	
	@Override
	public Optional<Cliente> findByRuc(String ruc) throws Exception {
		Optional<Cliente> cliente = Optional.empty();
		String qlString = "SELECT c FROM Cliente c WHERE c.ruc = ?1";	
		TypedQuery<Cliente> query = em.createQuery(qlString, Cliente.class);
		query.setParameter(1, ruc);
		List<Cliente> clientes = query.getResultList();
		
		if(clientes != null && !clientes.isEmpty())
			cliente = Optional.of( clientes.get(0) );
		
		return cliente;
	}

	
}
