package pe.edu.upc.techlive.models.repositories.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pe.edu.upc.techlive.models.entities.Pedido;
import pe.edu.upc.techlive.models.repositories.PedidoRepository;

public class PedidoRepositoryImpl implements PedidoRepository, Serializable{
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "TechLivePU")
	private EntityManager em;
	
	@Override
	public Pedido save(Pedido entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Pedido update(Pedido entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Pedido> optional = findById(id);
		
		if(optional.isPresent())
			em.remove(optional.get());
		
	}

	@Override
	public List<Pedido> findAll() throws Exception {
		List<Pedido> pedidos = new ArrayList<Pedido>();
		String qlString = "SELECT c FROM Pedido c";	// JPQL
		TypedQuery<Pedido> query = em.createQuery(qlString, Pedido.class);
		pedidos = query.getResultList();
		return pedidos;
	}

	@Override
	public Optional<Pedido> findById(Integer id) throws Exception {
		Optional<Pedido> Pedido = Optional.empty();
		String qlString = "SELECT c FROM Pedido c WHERE c.id = ?1";	// JPQL
		TypedQuery<Pedido> query = em.createQuery(qlString, Pedido.class);
		query.setParameter(1, id);
		List<Pedido> pedidos = query.getResultList();
		
		if(pedidos != null && !pedidos.isEmpty())
			Pedido = Optional.of( pedidos.get(0) );
		
		return Pedido;
	}

	@Override
	public List<Pedido> findByid(int id) {
		List<Pedido> Pedidos = new ArrayList<Pedido>();
		String qlString = "SELECT p FROM Pedido p WHERE p.id LIKE '%?1%'";	// JPQL
		TypedQuery<Pedido> query = em.createQuery(qlString, Pedido.class);
		query.setParameter(1, id);
		Pedidos = query.getResultList();
		return Pedidos;
	}
}
