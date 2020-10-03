package pe.edu.upc.techlive.models.repositories;

import java.util.List;

import pe.edu.upc.techlive.models.entities.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{
	List<Producto> findByModelo(String modelo) throws Exception;
}
