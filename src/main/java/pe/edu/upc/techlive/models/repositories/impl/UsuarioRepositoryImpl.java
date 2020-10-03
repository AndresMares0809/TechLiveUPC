package pe.edu.upc.techlive.models.repositories.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import pe.edu.upc.techlive.models.entities.Usuario;
import pe.edu.upc.techlive.models.repositories.UsuarioRepository;

public class UsuarioRepositoryImpl implements UsuarioRepository, Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "TechLivePU")
	private EntityManager em;
	
	@Override
	public Usuario save(Usuario entity) throws Exception {
		em.persist(entity);
		return entity;
	}

	@Override
	public Usuario update(Usuario entity) throws Exception {
		em.merge(entity);
		return entity;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		Optional<Usuario> optional = findById(id);
		
		if(optional.isPresent())
			em.remove(optional.get());
		
	}

	@Override
	public List<Usuario> findAll() throws Exception {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		String qlString = "SELECT c FROM Usuario c";	// JPQL
		TypedQuery<Usuario> query = em.createQuery(qlString, Usuario.class);
		usuarios = query.getResultList();
		return usuarios;
	}

	@Override
	public Optional<Usuario> findById(Integer id) throws Exception {
		Optional<Usuario> Usuario = Optional.empty();
		String qlString = "SELECT c FROM Usuario c WHERE c.id = ?1";	// JPQL
		TypedQuery<Usuario> query = em.createQuery(qlString, Usuario.class);
		query.setParameter(1, id);
		List<Usuario> usuarios = query.getResultList();
		
		if(usuarios != null && !usuarios.isEmpty())
			Usuario = Optional.of( usuarios.get(0) );
		
		return Usuario;
	}

	@Override
	public List<Usuario> findByApellido(String apellido) throws Exception {
		List<Usuario> usuarios = new ArrayList<Usuario>();
		String qlString = "SELECT c FROM Usuario c WHERE c.apellido LIKE ?1";	// JPQL
		TypedQuery<Usuario> query = em.createQuery(qlString, Usuario.class);
		query.setParameter(1, "%" + apellido + "%");
		usuarios = query.getResultList();
		return usuarios;
	}
}
