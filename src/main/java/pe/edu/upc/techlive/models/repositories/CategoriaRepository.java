package pe.edu.upc.techlive.models.repositories;

import java.util.List;

import pe.edu.upc.techlive.models.entities.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
	List<Categoria> findByDenominacion(String denominacion) throws Exception;
}
