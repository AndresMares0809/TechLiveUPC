package pe.edu.upc.techlive.models.repositories;

import java.util.List;

import pe.edu.upc.techlive.models.entities.Marca;

public interface MarcaRepository extends JpaRepository<Marca, Integer>{
	List<Marca> findByNombre (String nombre) throws Exception;
}
