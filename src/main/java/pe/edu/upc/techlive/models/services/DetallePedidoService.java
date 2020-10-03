package pe.edu.upc.techlive.models.services;

import java.util.List;

import pe.edu.upc.techlive.models.entities.DetallePedido;

public interface DetallePedidoService extends CrudService<DetallePedido, Integer>{
	List<DetallePedido> findByid(int id) throws Exception;
}
