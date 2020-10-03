package pe.edu.upc.techlive.models.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="pagos")
public class Pago {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	private Integer id;
	
	@Column(name = "codigo", nullable = false)
	private Integer codigo;
	
	@Column(name = "cvv", nullable = false)
	private Integer cvv;
	
	@Column(name = "fecha_vencimiento")
	@Temporal(TemporalType.DATE)
	private Date fecha_vencimiento;
	
	@Column(name = "nombre_tarjeta", length = 30, nullable = false)
	private String nombreTarjeta;
	
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@Transient
	private Integer usuarioId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCvv() {
		return cvv;
	}

	public void setCvv(Integer cvv) {
		this.cvv = cvv;
	}

	public Date getFecha_vencimiento() {
		return fecha_vencimiento;
	}

	public void setFecha_vencimiento(Date fecha_vencimiento) {
		this.fecha_vencimiento = fecha_vencimiento;
	}

	public String getNombreTarjeta() {
		return nombreTarjeta;
	}

	public void setNombreTarjeta(String nombreTarjeta) {
		this.nombreTarjeta = nombreTarjeta;
	}


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	
	
}
