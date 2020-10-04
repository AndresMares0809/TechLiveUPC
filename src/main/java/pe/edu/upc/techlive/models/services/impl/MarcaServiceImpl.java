package pe.edu.upc.techlive.models.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.techlive.models.entities.Marca;
import pe.edu.upc.techlive.models.repositories.MarcaRepository;
import pe.edu.upc.techlive.models.services.MarcaService;

@Named
@ApplicationScoped
public class MarcaServiceImpl implements MarcaService, Serializable {

	private static final long serialVersionUID = 1L;
	private MarcaRepository marcaRepository;
	
	@Transactional
	@Override
	public Marca save(Marca entity) throws Exception {
		return marcaRepository.save(entity);
	}

	@Transactional
	@Override
	public Marca update(Marca entity) throws Exception {
		return marcaRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		marcaRepository.deleteById(id);
		
	}

	@Override
	public List<Marca> findAll() throws Exception {
		return marcaRepository.findAll();
	}

	@Override
	public Optional<Marca> findById(Integer id) throws Exception {
		return marcaRepository.findById(id);
	}

	@Override
	public List<Marca> findByNombre(String nombre) throws Exception {
		return marcaRepository.findByNombre(nombre);
	}
}
