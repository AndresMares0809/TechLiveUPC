package pe.edu.upc.techlive.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="productos")
public class Producto {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column(name = "modelo", length = 30, nullable = false)
	private String modelo;
	
	@Column(name = "descripcion", length = 30, nullable = false)
	private String descripcion;
	
	@Column(name = "precio", nullable = false)
	private float precio;
	
	@Column(name = "stock", nullable = false)
	private Integer stock;
	
	@ManyToOne
	@JoinColumn(name = "vendedor_id")
	private Vendedor vendedor;
	
	@Transient
	private Integer vendedorlId;
	
	@ManyToOne
	@JoinColumn(name="categoria_id")
	private Categoria categoria;
	
	@Transient
	private Integer categorialId;
	
	@ManyToOne
	@JoinColumn(name = "marca_id")
	private Marca marca;
	
	@Transient
	private Integer marcaId;
	
	@ManyToOne
	@JoinColumn(name = "detalle_pedido_id")
	private DetallePedido detallePedido;
	
	@Transient
	private Integer detallePedidoId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public DetallePedido getDetallePedido() {
		return detallePedido;
	}

	public void setDetallePedido(DetallePedido detallePedido) {
		this.detallePedido = detallePedido;
	}

	public Integer getVendedorlId() {
		return vendedorlId;
	}

	public void setVendedorlId(Integer vendedorlId) {
		this.vendedorlId = vendedorlId;
	}

	public Integer getCategorialId() {
		return categorialId;
	}

	public void setCategorialId(Integer categorialId) {
		this.categorialId = categorialId;
	}

	public Integer getMarcaId() {
		return marcaId;
	}

	public void setMarcaId(Integer marcaId) {
		this.marcaId = marcaId;
	}

	public Integer getDetallePedidoId() {
		return detallePedidoId;
	}

	public void setDetallePedidoId(Integer detallePedidoId) {
		this.detallePedidoId = detallePedidoId;
	}


}
