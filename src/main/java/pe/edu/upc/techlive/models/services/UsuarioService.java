package pe.edu.upc.techlive.models.services;

import java.util.List;

import pe.edu.upc.techlive.models.entities.Usuario;

public interface UsuarioService extends CrudService<Usuario, Integer> {
	List<Usuario> findByApellido(String apellido) throws Exception;
}
