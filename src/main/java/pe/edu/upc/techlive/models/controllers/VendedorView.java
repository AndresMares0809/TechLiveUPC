package pe.edu.upc.techlive.models.controllers;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import pe.edu.upc.techlive.models.entities.Vendedor;
import pe.edu.upc.techlive.models.services.VendedorService;
import pe.edu.upc.techlive.utils.Action;

@Named("vendedorView")		// Creando un Beans
@ViewScoped
public class VendedorView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Vendedor> vendedores;
	private Vendedor vendedor;
	private Vendedor vendedorSelected;
	private Vendedor vendedorSearch;
	private Action action;
		
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
	private VendedorService vendedorService;
	

	@PostConstruct
	public void init() {
		cleanForm();
		loadVendedores();
		this.vendedorSearch = new Vendedor();
		action = Action.NONE;
		disabledAllButtom();
	}
	// Métodos que utilizan un Service
	public void loadVendedores() {
		try {
			this.vendedores = vendedorService.findAll();
		} catch( Exception e ) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}	
	
	public void newVendedor() {
		action = Action.NEW;
		cleanForm();
		loadVendedores();
		addMessageInfo("Creando nuevo Cliente");
		enabledButtomGrabar();
	}
	
	public void cleanForm( ) {
		this.vendedor = new Vendedor();
		this.vendedorSelected = null;	
	}
	
	public void saveVendedor() {
		try {
			if (action == Action.NEW) {
				vendedorService.save(this.vendedor);
				addMessageInfo("Se grabo de forma correcta el nuevo vendedor");
			} 
			else if (action == Action.EDIT) {
				vendedorService.update(this.vendedor);
				addMessageInfo("Se actualizo de forma correcta el vendedor");
			}	
			action = Action.NONE;
			cleanForm();
			loadVendedores();
			disabledAllButtom();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}

	
	public void cancelSaveVendedor() {
		cleanForm();
		loadVendedores();
		disabledAllButtom();
	}

	public void selectVendedor(SelectEvent<Vendedor> e) {
		cleanForm();
		this.vendedorSelected = e.getObject();
		enabledButtomEditarEliminar();
	}

	public void unselectVendedor(UnselectEvent<Vendedor> e) {
		cleanForm();
		this.vendedorSelected = null;
		disabledAllButtom();
	}
	
	public void editVendedor() {
		if(vendedorSelected != null) {
			action = Action.EDIT;
			vendedor = vendedorSelected;
			vendedorSelected = null;
			addMessageInfo("Ya puedes modificar el vendedor");
			enabledButtomGrabar();
		} 
		else {
			addMessageError("No hay ningun Vendedor seleccionado");
		}
	}
	
	// Método que se ejecuta cuando hace click en el boton 'Eliminar'
	public void deleteVendedor() {
		if(vendedorSelected != null) {
			try {
				vendedorService.deleteById(vendedorSelected.getId());
				action = Action.NONE;				
				addMessageInfo("Se elimino de forma correcta el vendedor: " + vendedorSelected.getNombre());
				cleanForm();
				loadVendedores();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println( e.getMessage() );
			}
			disabledAllButtom();
		}
		else {
			addMessageError("No hay ningun vendedor seleccionado");
		}
	}
	
	public void searchNombreVendedor() {
		try {
			this.vendedores = vendedorService.findByNombre(vendedorSearch.getNombre());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}
	// Mensaje 
	public void addMessageInfo(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	public void addMessageError(String summary) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	// Disabled Buttom
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
	public List<Vendedor> getVendedores() {
		return vendedores;
	}
	public void setVendedores(List<Vendedor> vendedores) {
		this.vendedores = vendedores;
	}
	public Vendedor getVendedor() {
		return vendedor;
	}
	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}
	public Vendedor getVendedorSelected() {
		return vendedorSelected;
	}
	public void setVendedorSelected(Vendedor vendedorSelected) {
		this.vendedorSelected = vendedorSelected;
	}
	public Vendedor getVendedorSearch() {
		return vendedorSearch;
	}
	public void setVendedorSearch(Vendedor vendedorSearch) {
		this.vendedorSearch = vendedorSearch;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
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
	public VendedorService getVendedorService() {
		return vendedorService;
	}
	public void setVendedorService(VendedorService vendedorService) {
		this.vendedorService = vendedorService;
	}
	
	

	
}

