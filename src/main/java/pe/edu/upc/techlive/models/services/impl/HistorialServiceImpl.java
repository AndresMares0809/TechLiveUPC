package pe.edu.upc.techlive.models.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.techlive.models.entities.Historial;
import pe.edu.upc.techlive.models.repositories.HistorialRepository;
import pe.edu.upc.techlive.models.services.HistorialService;

@Named
@ApplicationScoped
public class HistorialServiceImpl implements HistorialService, Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private HistorialRepository historialRepository;
	
	@Transactional
	@Override
	public Historial save(Historial entity) throws Exception {
		return historialRepository.save(entity);
	}

	@Transactional
	@Override
	public Historial update(Historial entity) throws Exception {
		return historialRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		historialRepository.deleteById(id);
		
	}

	@Override
	public List<Historial> findAll() throws Exception {
		return historialRepository.findAll();
	}

	@Override
	public Optional<Historial> findById(Integer id) throws Exception {
		return historialRepository.findById(id);
	}

	@Override
	public List<Historial> findByid(int id) throws Exception {
		// TODO Auto-generated method stub
		return historialRepository.findByid(id);
	}
}
