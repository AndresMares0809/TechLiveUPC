package pe.edu.upc.techlive.models.services;

import java.util.List;

import pe.edu.upc.techlive.models.entities.Pedido;

public interface PedidoService extends CrudService<Pedido, Integer>{
	List<Pedido> findByid(int id) throws Exception;
}
