package pe.edu.upc.techlive.models.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.techlive.models.entities.Pago;
import pe.edu.upc.techlive.models.repositories.PagoRepository;
import pe.edu.upc.techlive.models.services.PagoService;

@Named
@ApplicationScoped
public class PagoServiceImpl implements PagoService, Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private PagoRepository pagoRepository;
	
	@Transactional
	@Override
	public Pago save(Pago entity) throws Exception {
		return pagoRepository.save(entity);
	}

	@Transactional
	@Override
	public Pago update(Pago entity) throws Exception {
		return pagoRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		pagoRepository.deleteById(id);
		
	}

	@Override
	public List<Pago> findAll() throws Exception {
		return pagoRepository.findAll();
	}

	@Override
	public Optional<Pago> findById(Integer id) throws Exception {
		return pagoRepository.findById(id);
	}

	@Override
	public List<Pago> findByNombreTarjeta(String nombre) throws Exception {
		// TODO Auto-generated method stub
		return pagoRepository.findByNombreTarjeta(nombre);
	}

}
