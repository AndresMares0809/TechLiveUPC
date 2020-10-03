package pe.edu.upc.techlive.models.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.techlive.models.entities.Usuario;

import pe.edu.upc.techlive.models.repositories.UsuarioRepository;
import pe.edu.upc.techlive.models.services.UsuarioService;

@Named
@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService, Serializable {

	private static final long serialVersionUID = 1L;

	private UsuarioRepository usuarioRepository;
	
	@Transactional
	@Override
	public Usuario save(Usuario entity) throws Exception {
		return usuarioRepository.save(entity);
	}

	@Transactional
	@Override
	public Usuario update(Usuario entity) throws Exception {
		return usuarioRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		usuarioRepository.deleteById(id);
		
	}

	@Override
	public List<Usuario> findAll() throws Exception {
		return usuarioRepository.findAll();
	}

	@Override
	public Optional<Usuario> findById(Integer id) throws Exception {
		return usuarioRepository.findById(id);
	}

	@Override
	public List<Usuario> findByApellido(String apellido) throws Exception {
		return usuarioRepository.findByApellido(apellido);
	}
}
