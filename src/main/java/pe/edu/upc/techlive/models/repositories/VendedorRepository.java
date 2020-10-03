package pe.edu.upc.techlive.models.repositories;

import java.util.List;
import java.util.Optional;

import pe.edu.upc.techlive.models.entities.Vendedor;

public interface VendedorRepository extends JpaRepository<Vendedor, Integer>{
	List<Vendedor> findByNombre(String nombre) throws Exception;
	List<Vendedor> findByDireccion(String direccion) throws Exception;
	Optional<Vendedor> findByRuc (String ruc) throws Exception;
}
