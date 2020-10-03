package pe.edu.upc.techlive.models.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.techlive.models.entities.DetallePedido;
import pe.edu.upc.techlive.models.repositories.DetallePedidoRepository;
import pe.edu.upc.techlive.models.services.DetallePedidoService;

@Named
@ApplicationScoped
public class DetallePedidoServiceImpl implements DetallePedidoService, Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private DetallePedidoRepository detallePedidoRepository;
	
	@Transactional
	@Override
	public DetallePedido save(DetallePedido entity) throws Exception {
		return detallePedidoRepository.save(entity);
	}

	@Transactional
	@Override
	public DetallePedido update(DetallePedido entity) throws Exception {
		return detallePedidoRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		detallePedidoRepository.deleteById(id);
		
	}

	@Override
	public List<DetallePedido> findAll() throws Exception {
		return detallePedidoRepository.findAll();
	}

	@Override
	public Optional<DetallePedido> findById(Integer id) throws Exception {
		return detallePedidoRepository.findById(id);
	}

	@Override
	public List<DetallePedido> findByid(int id) throws Exception {
		// TODO Auto-generated method stub
		return detallePedidoRepository.findByid(id);
	}
}
