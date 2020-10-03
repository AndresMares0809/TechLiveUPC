package pe.edu.upc.techlive.models.controllers;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import pe.edu.upc.techlive.models.entities.Pago;
import pe.edu.upc.techlive.models.entities.Usuario;
import pe.edu.upc.techlive.models.services.PagoService;
import pe.edu.upc.techlive.models.services.UsuarioService;
import pe.edu.upc.techlive.utils.Action;

@Named("pagoView")		
@ViewScoped
public class PagoView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Pago> pagos;
	private Pago pago;
	private Pago pagoSelected;
	private Pago pagoSearch;
	private Action action;
	
	private List<Usuario> usuarios;
	
	// Disabled utilizado para activar y desactivar los botones
	private boolean disabledNuevo;
	private boolean disabledGrabar;
	private boolean disabledCancelar;
	private boolean disabledEditar;
	private boolean disabledEliminar;	
	// Style for Panelgrid y Datatable
	private String stylePanelGrid;
	private String StyleDataTable;
	
	@Inject
	private PagoService pagoService;
	
	@Inject
	private UsuarioService usuarioService;

	@PostConstruct
	public void init() {
		cleanForm();
		loadPagos();
		loadUsuarios();
		this.pagoSearch = new Pago();
		action = Action.NONE;
		disabledAllButtom();
	}

	
	public void loadPagos() {
		try {
			this.pagos = pagoService.findAll();
		} catch( Exception e ) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}	
	
	public void loadUsuarios() {
		try {
			this.usuarios = usuarioService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}
	
	public Optional<Usuario> loadUsuario( Integer id ) {
		try {
			return usuarioService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
		return Optional.empty();
	}
	
	public void newPago() {
		action = Action.NEW;
		cleanForm();
		loadPagos();
		addMessageInfo("Creando nuevo Pago");
		enabledButtomGrabar();
	}
	
	public void cleanForm( ) {
		this.pago = new Pago();
		this.pagoSelected = null;	
	}

	public void savePago() {
		try {
			if (action == Action.NEW || action == Action.EDIT) {
				verifyUsuario();
				if (action == Action.NEW) {
					pagoService.save(this.pago);
					addMessageInfo("Se grabo de forma correcta el nuevo pago");
				} 
				else if (action == Action.EDIT) {
					
					pagoService.update(this.pago);
					addMessageInfo("Se actualizo de forma correcta el pago");
				}	
				action = Action.NONE;
				cleanForm();
				loadPagos();
				disabledAllButtom();
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}
	
	public void verifyUsuario() {
		if (action == Action.NEW) {
			changeUsuario();
		} 
		else if (action == Action.EDIT) {
			if( this.pago.getUsuario() != null ) {
				if( ! this.pago.getUsuarioId().equals( this.pago.getUsuario().getId() ) ) {
					changeUsuario();
				}
			}
			else {
				changeUsuario();
			}				
		}
	}
	
	public void changeUsuario() {
		Optional<Usuario> optional = loadUsuario( this.pago.getUsuarioId() );
		if(optional.isPresent()) {
			this.pago.setUsuario( optional.get() );
		}
	}
	
	public void cancelSavePago() {
		cleanForm();
		loadPagos();
		disabledAllButtom();
	}

	public void selectPago(SelectEvent<Pago> e) {
		cleanForm();
		this.pagoSelected = e.getObject();
		if(this.getPagoSelected().getUsuario() != null ) {
			this.pagoSelected.setUsuarioId(this.getPagoSelected().getUsuario().getId());
		}		
		enabledButtomEditarEliminar();
	}
	
	
	public void unselectPago(UnselectEvent<Pago> e) {
		cleanForm();
		this.pagoSelected = null;
		disabledAllButtom();
	}
	
	public void editPago() {
		if(pagoSelected != null) {
			action = Action.EDIT;
			pago = pagoSelected;
			pagoSelected = null;
			addMessageInfo("Ya puedes modificar el pago");
			enabledButtomGrabar();
		} 
		else {
			addMessageError("No hay ningun pago seleccionado");
		}
	}
	
	public void deletePago() {
		if(pagoSelected != null) {
			try {
				pagoService.deleteById(pagoSelected.getId());
				action = Action.NONE;				
				addMessageInfo("Se elimino de forma correcta el pago: " + pagoSelected.getNombreTarjeta() );
				cleanForm();
				loadPagos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println( e.getMessage() );
			}
			disabledAllButtom();
		}
		else {
			addMessageError("No hay ningun pago seleccionado");
		}
	}
	
	public void searchNombreTarjeta() {
		try {
			this.pagos = pagoService.findByNombreTarjeta(pagoSearch.getNombreTarjeta());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}
	
	public void addMessageInfo(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public void addMessageError(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public void disabledAllButtom() {
		this.stylePanelGrid = "none";
		this.StyleDataTable = "block";
		this.disabledNuevo = false;
		this.disabledGrabar = true;
		this.disabledCancelar = true;
		this.disabledEditar = true;
		this.disabledEliminar = true;
	}
	
	public void enabledButtomGrabar() {
		this.stylePanelGrid = "block";
		this.StyleDataTable = "none";
		this.disabledNuevo = true;
		this.disabledGrabar = false;
		this.disabledCancelar = false;
		this.disabledEditar = true;
		this.disabledEliminar = true;
	}
	
	public void enabledButtomEditarEliminar() {
		this.stylePanelGrid = "none";
		this.StyleDataTable = "block";
		this.disabledNuevo = false;
		this.disabledGrabar = true;
		this.disabledCancelar = true;
		this.disabledEditar = false;
		this.disabledEliminar = false;
	}


	
	public List<Pago> getPagos() {
		return pagos;
	}


	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}


	public Pago getPago() {
		return pago;
	}


	public void setPago(Pago pago) {
		this.pago = pago;
	}


	public Pago getPagoSelected() {
		return pagoSelected;
	}


	public void setPagoSelected(Pago pagoSelected) {
		this.pagoSelected = pagoSelected;
	}


	public Pago getPagoSearch() {
		return pagoSearch;
	}


	public void setPagoSearch(Pago pagoSearch) {
		this.pagoSearch = pagoSearch;
	}


	public Action getAction() {
		return action;
	}


	public void setAction(Action action) {
		this.action = action;
	}


	public List<Usuario> getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}


	public boolean isDisabledNuevo() {
		return disabledNuevo;
	}


	public void setDisabledNuevo(boolean disabledNuevo) {
		this.disabledNuevo = disabledNuevo;
	}


	public boolean isDisabledGrabar() {
		return disabledGrabar;
	}


	public void setDisabledGrabar(boolean disabledGrabar) {
		this.disabledGrabar = disabledGrabar;
	}


	public boolean isDisabledCancelar() {
		return disabledCancelar;
	}


	public void setDisabledCancelar(boolean disabledCancelar) {
		this.disabledCancelar = disabledCancelar;
	}


	public boolean isDisabledEditar() {
		return disabledEditar;
	}


	public void setDisabledEditar(boolean disabledEditar) {
		this.disabledEditar = disabledEditar;
	}


	public boolean isDisabledEliminar() {
		return disabledEliminar;
	}


	public void setDisabledEliminar(boolean disabledEliminar) {
		this.disabledEliminar = disabledEliminar;
	}


	public String getStylePanelGrid() {
		return stylePanelGrid;
	}


	public void setStylePanelGrid(String stylePanelGrid) {
		this.stylePanelGrid = stylePanelGrid;
	}


	public String getStyleDataTable() {
		return StyleDataTable;
	}


	public void setStyleDataTable(String styleDataTable) {
		StyleDataTable = styleDataTable;
	}


	public PagoService getPagoService() {
		return pagoService;
	}


	public void setPagoService(PagoService pagoService) {
		this.pagoService = pagoService;
	}


	public UsuarioService getUsuarioService() {
		return usuarioService;
	}


	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	
	
	
	
}
