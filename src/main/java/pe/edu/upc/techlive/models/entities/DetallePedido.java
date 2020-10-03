package pe.edu.upc.techlive.models.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="detalle_pedidos")
public class DetallePedido {
	
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column(name = "precio_total", nullable = false)
	private float precioTotal;
	
	@Column(name = "confirmacion_pedido", nullable = false)
	private boolean confirmacionPedido;
	
	public boolean isConfirmacionPedido() {
		return confirmacionPedido;
	}

	public void setConfirmacionPedido(boolean confirmacionPedido) {
		this.confirmacionPedido = confirmacionPedido;
	}

	@ManyToOne
	@JoinColumn(name="historial_id")
	private Historial historial;
	
	@Transient
	private Integer historialId;
	
	@OneToMany(mappedBy = "detallePedido")
	private List<Producto> productos;
	
	@OneToMany(mappedBy = "detallePedido")
	private List<Pedido> pedidos;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(float precioTotal) {
		this.precioTotal = precioTotal;
	}

	public Historial getHistorial() {
		return historial;
	}

	public void setHistorial(Historial historial) {
		this.historial = historial;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Integer getHistorialId() {
		return historialId;
	}

	public void setHistorialId(Integer historialId) {
		this.historialId = historialId;
	}
	
	
}
