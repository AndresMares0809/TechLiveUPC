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

import pe.edu.upc.techlive.models.entities.Historial;
import pe.edu.upc.techlive.models.services.HistorialService;
import pe.edu.upc.techlive.utils.Action;

@Named("historialView")		
@ViewScoped
public class HistorialView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Historial> historiales;
	private Historial historial;
	private Historial historialSelected;
	private Historial historialSearch;
	private Action action;
	
	
		private boolean disabledNuevo;
		private boolean disabledGrabar;
		private boolean disabledCancelar;
		private boolean disabledEditar;
		private boolean disabledEliminar;	

		private String stylePanelGrid;
		private String StyleDataTable;
		
		@Inject
		private HistorialService historialService;
		
		@PostConstruct
		public void init() {
			cleanForm();
			loadHistoriales();
			this.historialSearch = new Historial();
			action = Action.NONE;
			disabledAllButtom();
		}
		
		public void loadHistoriales() {
			try {
				this.historiales = historialService.findAll();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println( e.getMessage() );
			}
		}

		public void newHistorial() {
			action = Action.NEW;
			cleanForm();
			loadHistoriales();
			addMessageInfo("Creando nuevo historial");
			enabledButtomGrabar();
		}

		public void cleanForm( ) {
			this.historial = new Historial();
			this.historialSelected = null;	
		}

		public void savehistorial() {
			try {
				if (action == Action.NEW) {
					historialService.save(this.historial);
					addMessageInfo("Se grabo de forma correcta el nuevo historial");
				} 
				else if (action == Action.EDIT) {
					historialService.update(this.historial);
					addMessageInfo("Se actualizo de forma correcta el historial");
				}	
				action = Action.NONE;
				cleanForm();
				loadHistoriales();
				disabledAllButtom();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println( e.getMessage() );
			}
		}

		public void cancelSaveHistorial() {
			cleanForm();
			loadHistoriales();
			disabledAllButtom();
		}

		public void selectHistorial(SelectEvent<Historial> e) {
			cleanForm();
			this.historialSelected = e.getObject();
			enabledButtomEditarEliminar();
		}

		public void unselectHistorial(UnselectEvent<Historial> e) {
			cleanForm();
			this.historialSelected = null;
			disabledAllButtom();
		}
		
		public void editHistorial() {
			if(historialSelected != null) {
				action = Action.EDIT;
				historial = historialSelected;
				historialSelected = null;
				addMessageInfo("Ya puedes modificar el historial");
				enabledButtomGrabar();
			} 
			else {
				addMessageError("No hay ningun historial seleccionado");
			}
		}
		

		public void deleteHistorial() {
			if(historialSelected != null) {
				try {
					historialService.deleteById(historialSelected.getId());
					action = Action.NONE;				
					addMessageInfo("Se elimino de forma correcta el historial: " + historialSelected.getId() );
					cleanForm();
					loadHistoriales();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.err.println( e.getMessage() );
				}
				disabledAllButtom();
			}
			else {
				addMessageError("No hay ningun historial seleccionado");
			}
		}
		
		public void searchIdHistorial() {
			try {
				this.historiales = historialService.findByid(historialSearch.getId());
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

		public List<Historial> getHistoriales() {
			return historiales;
		}

		public void setHistoriales(List<Historial> historiales) {
			this.historiales = historiales;
		}

		public Historial getHistorial() {
			return historial;
		}

		public void setHistorial(Historial historial) {
			this.historial = historial;
		}

		public Historial getHistorialSelected() {
			return historialSelected;
		}

		public void setHistorialSelected(Historial historialSelected) {
			this.historialSelected = historialSelected;
		}

		public Historial getHistorialSearch() {
			return historialSearch;
		}

		public void setHistorialSearch(Historial historialSearch) {
			this.historialSearch = historialSearch;
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

		public HistorialService getHistorialService() {
			return historialService;
		}

		public void setHistorialService(HistorialService historialService) {
			this.historialService = historialService;
		}
		
		
}
