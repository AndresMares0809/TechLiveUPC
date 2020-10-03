package pe.edu.upc.techlive.models.services;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.techlive.models.entities.Cliente;

public interface ClienteService extends CrudService<Cliente, Integer> {
	List<Cliente> findByDireccion(String direccion) throws Exception;
	Optional<Cliente> findByDni(String dni) throws Exception;
	Optional<Cliente> findByRuc(String ruc) throws Exception;
}
