package pe.edu.upc.techlive.models.services;

import java.util.List;

import pe.edu.upc.techlive.models.entities.Pago;

public interface PagoService extends CrudService<Pago, Integer>{
	List<Pago> findByNombreTarjeta(String nombre) throws Exception;
}
