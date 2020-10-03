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

import pe.edu.upc.techlive.models.entities.DetallePedido;
import pe.edu.upc.techlive.models.entities.Historial;
import pe.edu.upc.techlive.models.services.DetallePedidoService;
import pe.edu.upc.techlive.models.services.HistorialService;
import pe.edu.upc.techlive.utils.Action;



@Named("detallePedidoView")		
@ViewScoped
public class DetallePedidoView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<DetallePedido> detallePedidos;
	private DetallePedido detallePedido;
	private DetallePedido detallePedidoSelected;
	private DetallePedido detallePedidoSearch;
	private Action action;
	
	private List<Historial> historiales;
	
	private boolean disabledNuevo;
	private boolean disabledGrabar;
	private boolean disabledCancelar;
	private boolean disabledEditar;
	private boolean disabledEliminar;	

	private String stylePanelGrid;
	private String StyleDataTable;
	
	@Inject
	private DetallePedidoService detallePedidoService;
	
	@Inject
	private HistorialService historialService;

	@PostConstruct
	public void init() {
		cleanForm();
		loadDetallePedidos();
		loadHistoriales();
		this.detallePedidoSearch = new DetallePedido();
		action = Action.NONE;
		disabledAllButtom();
	}

	
	public void loadDetallePedidos() {
		try {
			this.detallePedidos = detallePedidoService.findAll();
		} catch( Exception e ) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}	
	
	public void loadHistoriales() {
		try {
			this.historiales = historialService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}
	
	public Optional<Historial> loadHistorial( Integer id ) {
		try {
			return historialService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
		return Optional.empty();
	}
	
	public void newDetallePedido() {
		action = Action.NEW;
		cleanForm();
		loadDetallePedidos();
		addMessageInfo("Creando nuevo Detalle de Pedido");
		enabledButtomGrabar();
	}
	
	public void cleanForm( ) {
		this.detallePedido = new DetallePedido();
		this.detallePedidoSelected = null;	
	}

	public void saveDetallePedido() {
		try {
			if (action == Action.NEW || action == Action.EDIT) {
				verifyHistorial();
				if (action == Action.NEW) {
					detallePedidoService.save(this.detallePedido);
					addMessageInfo("Se grabo de forma correcta el nuevo detallo de pedido");
				} 
				else if (action == Action.EDIT) {
					
					detallePedidoService.update(this.detallePedido);
					addMessageInfo("Se actualizo de forma correcta el detalle de pedido");
				}	
				action = Action.NONE;
				cleanForm();
				loadDetallePedidos();
				disabledAllButtom();
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}
	
	public void verifyHistorial() {
		if (action == Action.NEW) {
			changeHistorial();
		} 
		else if (action == Action.EDIT) {
			if( this.detallePedido.getHistorial() != null ) {
				if( ! this.detallePedido.getHistorialId().equals( this.detallePedido.getHistorial().getId() ) ) {
					changeHistorial();
				}
			}
			else {
				changeHistorial();
			}				
		}
	}
	
	public void changeHistorial() {
		Optional<Historial> optional = loadHistorial( this.detallePedido.getHistorialId() );
		if(optional.isPresent()) {
			this.detallePedido.setHistorial( optional.get() );
		}
	}
	
	public void cancelSaveDetallePedido() {
		cleanForm();
		loadDetallePedidos();
		disabledAllButtom();
	}

	public void selectDetallePedid(SelectEvent<DetallePedido> e) {
		cleanForm();
		this.detallePedidoSelected = e.getObject();
		if(this.getDetallePedidoSelected().getHistorial() != null ) {
			this.detallePedidoSelected.setHistorialId(this.getDetallePedidoSelected().getHistorial().getId());
		}		
		enabledButtomEditarEliminar();
	}
	
	
	public void unselectDetallePedido(UnselectEvent<DetallePedido> e) {
		cleanForm();
		this.detallePedidoSelected = null;
		disabledAllButtom();
	}
	
	public void editDetallePedido() {
		if(detallePedidoSelected != null) {
			action = Action.EDIT;
			detallePedido = detallePedidoSelected;
			detallePedidoSelected = null;
			addMessageInfo("Ya puedes modificar el detalle pedido");
			enabledButtomGrabar();
		} 
		else {
			addMessageError("No hay ningun detalle de pedido seleccionado");
		}
	}
	
	public void deleteDetallePedido() {
		if(detallePedidoSelected != null) {
			try {
				detallePedidoService.deleteById(detallePedidoSelected.getId());
				action = Action.NONE;				
				addMessageInfo("Se elimino de forma correcta el detalle de pedido: " + detallePedidoSelected.getId() );
				cleanForm();
				loadDetallePedidos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println( e.getMessage() );
			}
			disabledAllButtom();
		}
		else {
			addMessageError("No hay ningun detalle de pedido seleccionado");
		}
	}
	
	public void searchIdDetallePedido() {
		try {
			this.detallePedidos = detallePedidoService.findByid(detallePedidoSearch.getId());
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


	public List<DetallePedido> getDetallePedidos() {
		return detallePedidos;
	}


	public void setDetallePedidos(List<DetallePedido> detallePedidos) {
		this.detallePedidos = detallePedidos;
	}


	public DetallePedido getDetallePedido() {
		return detallePedido;
	}


	public void setDetallePedido(DetallePedido detallePedido) {
		this.detallePedido = detallePedido;
	}


	public DetallePedido getDetallePedidoSelected() {
		return detallePedidoSelected;
	}


	public void setDetallePedidoSelected(DetallePedido detallePedidoSelected) {
		this.detallePedidoSelected = detallePedidoSelected;
	}


	public DetallePedido getDetallePedidoSearch() {
		return detallePedidoSearch;
	}


	public void setDetallePedidoSearch(DetallePedido detallePedidoSearch) {
		this.detallePedidoSearch = detallePedidoSearch;
	}


	public Action getAction() {
		return action;
	}


	public void setAction(Action action) {
		this.action = action;
	}


	public List<Historial> getHistoriales() {
		return historiales;
	}


	public void setHistoriales(List<Historial> historiales) {
		this.historiales = historiales;
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


	public DetallePedidoService getDetallePedidoService() {
		return detallePedidoService;
	}


	public void setDetallePedidoService(DetallePedidoService detallePedidoService) {
		this.detallePedidoService = detallePedidoService;
	}


	public HistorialService getHistorialService() {
		return historialService;
	}


	public void setHistorialService(HistorialService historialService) {
		this.historialService = historialService;
	}



}
