package pe.edu.upc.techlive.models.services;

import java.util.List;

import pe.edu.upc.techlive.models.entities.Producto;

public interface ProductoService extends CrudService<Producto, Integer>{
	List<Producto> findByModelo(String modelo) throws Exception;
}
