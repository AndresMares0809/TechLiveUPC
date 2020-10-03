package pe.edu.upc.techlive.models.services;

import java.util.List;

import pe.edu.upc.techlive.models.entities.Categoria;

public interface CategoriaService extends CrudService<Categoria, Integer> {
	List<Categoria> findByDenominacion(String denominacion) throws Exception;
}
