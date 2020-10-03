package pe.edu.upc.techlive.models.repositories;

import java.util.List;

import pe.edu.upc.techlive.models.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	List<Usuario> findByApellido(String apellido) throws Exception;
}
