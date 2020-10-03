package pe.edu.upc.techlive.models.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.techlive.models.entities.Producto;
import pe.edu.upc.techlive.models.repositories.ProductoRepository;
import pe.edu.upc.techlive.models.services.ProductoService;

@Named
@ApplicationScoped
public class ProductoServiceImpl implements ProductoService, Serializable {

	private static final long serialVersionUID = 1L;

	private ProductoRepository productoRepository;
	
	@Transactional
	@Override
	public Producto save(Producto entity) throws Exception {
		return productoRepository.save(entity);
	}

	@Transactional
	@Override
	public Producto update(Producto entity) throws Exception {
		return productoRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		productoRepository.deleteById(id);
		
	}

	@Override
	public List<Producto> findAll() throws Exception {
		return productoRepository.findAll();
	}

	@Override
	public Optional<Producto> findById(Integer id) throws Exception {
		return productoRepository.findById(id);
	}

	@Override
	public List<Producto> findByModelo(String modelo) throws Exception {
		return productoRepository.findByModelo(modelo);
	}
}
