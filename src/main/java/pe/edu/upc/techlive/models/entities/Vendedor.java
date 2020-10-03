package pe.edu.upc.techlive.models.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="vendedores")
public class Vendedor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;

	@Column(name = "nombre", length = 30, nullable = false)
	private String nombre;
	
	@Column(name = "ruc", length = 30, nullable = false)
	private String ruc;
	
	@Column(name = "direccion", length = 30, nullable = false)
	private String direccion;
	
	@OneToMany(mappedBy = "vendedor")
	private List<Producto> productos;
	
	@OneToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}


	
	
}
