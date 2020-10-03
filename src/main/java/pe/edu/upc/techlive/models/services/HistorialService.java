package pe.edu.upc.techlive.models.services;

import java.util.List;

import pe.edu.upc.techlive.models.entities.Historial;

public interface HistorialService extends CrudService<Historial, Integer>{
	List<Historial> findByid(int id) throws Exception;
}
