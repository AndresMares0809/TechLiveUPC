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

import pe.edu.upc.techlive.models.entities.Marca;
import pe.edu.upc.techlive.models.services.MarcaService;
import pe.edu.upc.techlive.utils.Action;

@Named("marcaView")	
@ViewScoped
public class MarcaView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Marca> marcas;
	private Marca marca;
	private Marca marcaSelected;
	private Marca marcaSearch;
	private Action action;
		
	private boolean disabledNuevo;
	private boolean disabledGrabar;
	private boolean disabledCancelar;
	private boolean disabledEditar;
	private boolean disabledEliminar;	

	private String stylePanelGrid;
	private String StyleDataTable;
	
	@Inject
	private MarcaService marcaService;
	

	@PostConstruct
	public void init() {
		cleanForm();
		loadMarcas();
		this.marcaSearch = new Marca();
		action = Action.NONE;
		disabledAllButtom();
	}
	// Métodos que utilizan un Service
	public void loadMarcas() {
		try {
			this.marcas = marcaService.findAll();
		} catch( Exception e ) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}	
	
	public void newMarca() {
		action = Action.NEW;
		cleanForm();
		loadMarcas();
		addMessageInfo("Creando nueva marca");
		enabledButtomGrabar();
	}
	
	public void cleanForm( ) {
		this.marca = new Marca();
		this.marcaSelected = null;	
	}
	
	public void saveMarca() {
		try {
			if (action == Action.NEW) {
				marcaService.save(this.marca);
				addMessageInfo("Se grabo de forma correcta la nueva marca");
			} 
			else if (action == Action.EDIT) {
				marcaService.update(this.marca);
				addMessageInfo("Se actualizo de forma correcta la marca");
			}	
			action = Action.NONE;
			cleanForm();
			loadMarcas();
			disabledAllButtom();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}

	public void cancelSaveMarca() {
		cleanForm();
		loadMarcas();
		disabledAllButtom();
	}

	public void selectMarca(SelectEvent<Marca> e) {
		cleanForm();
		this.marcaSelected = e.getObject();
		enabledButtomEditarEliminar();
	}

	public void unselectMarca(UnselectEvent<Marca> e) {
		cleanForm();
		this.marcaSelected = null;
		disabledAllButtom();
	}
	
	public void editMarca() {
		if(marcaSelected != null) {
			action = Action.EDIT;
			marca = marcaSelected;
			marcaSelected = null;
			addMessageInfo("Ya puedes modificar la marca");
			enabledButtomGrabar();
		} 
		else {
			addMessageError("No hay ninguna marca seleccionada");
		}
	}
	
	public void deleteMarca() {
		if(marcaSelected != null) {
			try {
				marcaService.deleteById(marcaSelected.getId());
				action = Action.NONE;				
				addMessageInfo("Se elimino de forma correcta la marca: " + marcaSelected.getNombre());
				cleanForm();
				loadMarcas();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println( e.getMessage() );
			}
			disabledAllButtom();
		}
		else {
			addMessageError("No hay ninguna marca seleccionada");
		}
	}
	
	public void searchNombreMarca() {
		try {
			this.marcas = marcaService.findByNombre(marcaSearch.getNombre());
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
	public List<Marca> getMarcas() {
		return marcas;
	}
	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	public Marca getMarcaSelected() {
		return marcaSelected;
	}
	public void setMarcaSelected(Marca marcaSelected) {
		this.marcaSelected = marcaSelected;
	}
	public Marca getMarcaSearch() {
		return marcaSearch;
	}
	public void setMarcaSearch(Marca marcaSearch) {
		this.marcaSearch = marcaSearch;
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
	public MarcaService getMarcaService() {
		return marcaService;
	}
	public void setMarcaService(MarcaService marcaService) {
		this.marcaService = marcaService;
	}
	
	
}
