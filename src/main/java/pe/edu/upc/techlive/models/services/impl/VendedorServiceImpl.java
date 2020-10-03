package pe.edu.upc.techlive.models.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.techlive.models.entities.Vendedor;
import pe.edu.upc.techlive.models.repositories.VendedorRepository;
import pe.edu.upc.techlive.models.services.VendedorService;

@Named
@ApplicationScoped
public class VendedorServiceImpl implements VendedorService, Serializable{

	private static final long serialVersionUID = 1L;

	private VendedorRepository vendedorRepository;
	
	@Transactional
	@Override
	public Vendedor save(Vendedor entity) throws Exception {
		return vendedorRepository.save(entity);
	}

	@Transactional
	@Override
	public Vendedor update(Vendedor entity) throws Exception {
		return vendedorRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		vendedorRepository.deleteById(id);
		
	}

	@Override
	public List<Vendedor> findAll() throws Exception {
		return vendedorRepository.findAll();
	}

	@Override
	public Optional<Vendedor> findById(Integer id) throws Exception {
		return vendedorRepository.findById(id);
	}

	@Override
	public List<Vendedor> findByNombre(String nombre) throws Exception {
		return vendedorRepository.findByNombre(nombre);
	}

	@Override
	public List<Vendedor> findByDireccion(String direccion) throws Exception {
		return vendedorRepository.findByDireccion(direccion);
	}

	@Override
	public Optional<Vendedor> findByRuc(String ruc) throws Exception {
		return vendedorRepository.findByRuc(ruc);
	}

}
