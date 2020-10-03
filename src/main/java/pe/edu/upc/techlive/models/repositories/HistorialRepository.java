package pe.edu.upc.techlive.models.repositories;

import java.util.List;

import pe.edu.upc.techlive.models.entities.Historial;

public interface HistorialRepository extends JpaRepository<Historial, Integer>{

	List<Historial> findByid(int id);
}
