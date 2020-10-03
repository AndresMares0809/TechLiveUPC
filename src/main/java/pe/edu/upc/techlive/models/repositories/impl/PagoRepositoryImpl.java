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

import pe.edu.upc.techlive.models.entities.Pago;
import pe.edu.upc.techlive.models.repositories.PagoRepository;

@Named
@ApplicationScoped
public class PagoRepositoryImpl implements PagoRepository, Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "TechLivePU")
	private EntityManager em;
	
	@Override
	public Pago save(Pago entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Pago update(Pago entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Pago> optional = findById(id);
		
		if(optional.isPresent())
			em.remove(optional.get());
		
	}

	@Override
	public List<Pago> findAll() throws Exception {
		List<Pago> pagos = new ArrayList<Pago>();
		String qlString = "SELECT c FROM Pago c";	// JPQL
		TypedQuery<Pago> query = em.createQuery(qlString, Pago.class);
		pagos = query.getResultList();
		return pagos;
	}

	@Override
	public Optional<Pago> findById(Integer id) throws Exception {
		Optional<Pago> Pago = Optional.empty();
		String qlString = "SELECT c FROM Pago c WHERE c.id = ?1";	// JPQL
		TypedQuery<Pago> query = em.createQuery(qlString, Pago.class);
		query.setParameter(1, id);
		List<Pago> pagos = query.getResultList();
		
		if(pagos != null && !pagos.isEmpty())
			Pago = Optional.of( pagos.get(0) );
		
		return Pago;
	}

	@Override
	public List<Pago> findByNombreTarjeta(String nombre) throws Exception {
		List<Pago> pagos = new ArrayList<Pago>();
		String qlString = "SELECT p FROM Pago p WHERE p.nombretarjeta LIKE '%?1%'";	// JPQL
		TypedQuery<Pago> query = em.createQuery(qlString, Pago.class);
		query.setParameter(1, nombre);
		pagos = query.getResultList();
		return pagos;
	}
	
}
