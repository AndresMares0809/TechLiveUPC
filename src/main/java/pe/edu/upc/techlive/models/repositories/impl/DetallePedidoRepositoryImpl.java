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

import pe.edu.upc.techlive.models.entities.DetallePedido;
import pe.edu.upc.techlive.models.repositories.DetallePedidoRepository;

@Named
@ApplicationScoped
public class DetallePedidoRepositoryImpl implements DetallePedidoRepository, Serializable{

	
	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "TechLivePU")
	private EntityManager em;
	
	@Override
	public DetallePedido save(DetallePedido entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public DetallePedido update(DetallePedido entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<DetallePedido> optional = findById(id);
		
		if(optional.isPresent())
			em.remove(optional.get());
		
	}

	@Override
	public List<DetallePedido> findAll() throws Exception {
		List<DetallePedido> detallePedidos = new ArrayList<DetallePedido>();
		String qlString = "SELECT c FROM DetallePedido c";	// JPQL
		TypedQuery<DetallePedido> query = em.createQuery(qlString, DetallePedido.class);
		detallePedidos = query.getResultList();
		return detallePedidos;
	}

	@Override
	public Optional<DetallePedido> findById(Integer id) throws Exception {
		Optional<DetallePedido> detallePedido = Optional.empty();
		String qlString = "SELECT c FROM DetallePedido c WHERE c.id = ?1";	// JPQL
		TypedQuery<DetallePedido> query = em.createQuery(qlString, DetallePedido.class);
		query.setParameter(1, id);
		List<DetallePedido> DetallePedidos = query.getResultList();
		
		if(DetallePedidos != null && !DetallePedidos.isEmpty())
			detallePedido = Optional.of( DetallePedidos.get(0) );
		
		return detallePedido;
	}

	@Override
	public List <DetallePedido> findByid(int id) {
		List<DetallePedido> detallePedidos = new ArrayList<DetallePedido>();
		String qlString = "SELECT p FROM DetallePedido p WHERE p.id LIKE '%?1%'";	// JPQL
		TypedQuery<DetallePedido> query = em.createQuery(qlString, DetallePedido.class);
		query.setParameter(1, id);
		detallePedidos = query.getResultList();
		return detallePedidos;
	}

	
}
