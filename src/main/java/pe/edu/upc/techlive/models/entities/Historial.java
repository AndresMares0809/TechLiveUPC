package pe.edu.upc.techlive.models.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="historiales")
public class Historial {

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column(name = "estado", length = 20, nullable = false)
	private String estado;
	
	@Column(name = "ultima_actualizacion")
	@Temporal(TemporalType.DATE)
	private Date ultimaActualizacion;
	
	@OneToMany(mappedBy = "historial")
	private List<DetallePedido> detallePedidos;

	public Historial() {
		detallePedidos = new ArrayList<>();
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getUltimaActualizacion() {
		return ultimaActualizacion;
	}

	public void setUltimaActualizacion(Date ultimaActualizacion) {
		this.ultimaActualizacion = ultimaActualizacion;
	}


	public List<DetallePedido> getDetallePedidos() {
		return detallePedidos;
	}

	public void setDetallePedidos(List<DetallePedido> detallePedidos) {
		this.detallePedidos = detallePedidos;
	}
}

