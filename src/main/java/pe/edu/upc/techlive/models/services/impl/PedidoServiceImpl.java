package pe.edu.upc.techlive.models.services.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.transaction.Transactional;

import pe.edu.upc.techlive.models.entities.Pedido;
import pe.edu.upc.techlive.models.repositories.PedidoRepository;
import pe.edu.upc.techlive.models.services.PedidoService;

@Named
@ApplicationScoped
public class PedidoServiceImpl implements PedidoService, Serializable{

	private static final long serialVersionUID = 1L;

	private PedidoRepository pedidoRepository;
	
	@Transactional
	@Override
	public Pedido save(Pedido entity) throws Exception {
		return pedidoRepository.save(entity);
	}

	@Transactional
	@Override
	public Pedido update(Pedido entity) throws Exception {
		return pedidoRepository.update(entity);
	}

	@Transactional
	@Override
	public void deleteById(Integer id) throws Exception {
		pedidoRepository.deleteById(id);
		
	}

	@Override
	public List<Pedido> findAll() throws Exception {
		return pedidoRepository.findAll();
	}

	@Override
	public Optional<Pedido> findById(Integer id) throws Exception {
		return pedidoRepository.findById(id);
	}

	@Override
	public List<Pedido> findByid(int id) throws Exception {
		// TODO Auto-generated method stub
		return pedidoRepository.findByid(id);
	}
}
