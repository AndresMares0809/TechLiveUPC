package pe.edu.upc.techlive.models.services;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.techlive.models.entities.Vendedor;

public interface VendedorService extends CrudService<Vendedor, Integer>{
	List<Vendedor> findByNombre(String nombre) throws Exception;
	List<Vendedor> findByDireccion(String direccion) throws Exception;
	Optional<Vendedor> findByRuc (String ruc) throws Exception;
}
