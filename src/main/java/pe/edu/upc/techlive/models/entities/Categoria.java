package pe.edu.upc.techlive.models.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="categorias")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column(name = "denominacion", length = 30, nullable = false)
	private String denominacion;

	@OneToMany(mappedBy = "categoria")
	private List<Producto> productos;
	
	public Categoria() {
		productos = new ArrayList<>();
	}
	
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getDenominacion() {
		return denominacion;
	}



	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}



	public List<Producto> getProductos() {
		return productos;
	}



	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

}
