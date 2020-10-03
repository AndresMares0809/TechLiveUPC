package pe.edu.upc.techlive.models.services;

import java.util.List;

import pe.edu.upc.techlive.models.entities.Marca;

public interface MarcaService extends CrudService<Marca, Integer>{
	List<Marca> findByNombre (String nombre) throws Exception;
}
