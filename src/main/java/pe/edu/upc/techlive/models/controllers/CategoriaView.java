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

import pe.edu.upc.techlive.models.entities.Categoria;
import pe.edu.upc.techlive.models.services.CategoriaService;
import pe.edu.upc.techlive.utils.Action;



@Named("categoriaView")		// Creando un Beans
@ViewScoped
public class CategoriaView  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Categoria> categorias;
	private Categoria categoria;
	private Categoria categoriaSelected;
	private Categoria categoriaSearch;
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
		private CategoriaService categoriaService;
		
		@PostConstruct
		public void init() {
			cleanForm();
			loadCategorias();
			this.categoriaSearch = new Categoria();
			action = Action.NONE;
			disabledAllButtom();
		}
		
		public void loadCategorias() {
			try {
				this.categorias = categoriaService.findAll();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println( e.getMessage() );
			}
		}
		// Método que se ejecuta cuando hago click en 'Nuevo'
		public void newCategoria() {
			action = Action.NEW;
			cleanForm();
			loadCategorias();
			addMessageInfo("Creando nueva Categoria");
			enabledButtomGrabar();
		}
		public void cleanForm( ) {
			this.categoria = new Categoria();
			this.categoriaSelected = null;	
		}
		// Metodo para grabar el elemento ingresado en el formulario
		public void saveCategoria() {
			try {
				if (action == Action.NEW) {
					categoriaService.save(this.categoria);
					addMessageInfo("Se grabo de forma correcta la nueva categoría");
				} 
				else if (action == Action.EDIT) {
					categoriaService.update(this.categoria);
					addMessageInfo("Se actualizo de forma correcta la categoría");
				}	
				action = Action.NONE;
				cleanForm();
				loadCategorias();
				disabledAllButtom();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println( e.getMessage() );
			}
		}
		public void cancelSaveCategoria() {
			cleanForm();
			loadCategorias();
			disabledAllButtom();
		}
		// Metodo que se ejecuta cada vez que el usuario selecciona una fila del datatable
		public void selectCategoria(SelectEvent<Categoria> e) {
			cleanForm();
			this.categoriaSelected = e.getObject();
			enabledButtomEditarEliminar();
		}
		// Metodo que se ejecuta cada vez que el usuario des-selecciona una fila del datatable
		public void unselectCategoria(UnselectEvent<Categoria> e) {
			cleanForm();
			this.categoriaSelected = null;
			disabledAllButtom();
		}
		
		// Método que se ejecuta cuando hago click en el boton Editar
		public void editCategoria() {
			if(categoriaSelected != null) {
				action = Action.EDIT;
				categoria = categoriaSelected;
				categoriaSelected = null;
				addMessageInfo("Ya puedes modificar la categoría");
				enabledButtomGrabar();
			} 
			else {
				addMessageError("No hay ninguna Categoria seleccionada");
			}
		}
		
		// Método que se ejecuta cuando hace click en el boton 'Eliminar'
		public void deleteCategoria() {
			if(categoriaSelected != null) {
				try {
					categoriaService.deleteById(categoriaSelected.getId());
					action = Action.NONE;				
					addMessageInfo("Se elimino de forma correcta la categoría: " + categoriaSelected.getDenominacion() );
					cleanForm();
					loadCategorias();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.err.println( e.getMessage() );
				}
				disabledAllButtom();
			}
			else {
				addMessageError("No hay ninguna Categoria seleccionada");
			}
		}
		
		public void searchDenominacionCategoria() {
			try {
				this.categorias = categoriaService.findByDenominacion(categoriaSearch.getDenominacion());
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
		

		
		// Getter y setter
		public List<Categoria> getCategorias() {
			return categorias;
		}

		public CategoriaService getCategoriaService() {
			return categoriaService;
		}

		public Categoria getCategoria() {
			return categoria;
		}

		public Categoria getCategoriaSelected() {
			return categoriaSelected;
		}

		public boolean isDisabledGrabar() {
			return disabledGrabar;
		}

		public boolean isDisabledEditar() {
			return disabledEditar;
		}

		public boolean isDisabledEliminar() {
			return disabledEliminar;
		}

		public boolean isDisabledCancelar() {
			return disabledCancelar;
		}

		public boolean isDisabledNuevo() {
			return disabledNuevo;
		}

		public Categoria getCategoriaSearch() {
			return categoriaSearch;
		}

		public String getStylePanelGrid() {
			return stylePanelGrid;
		}

		public String getStyleDataTable() {
			return StyleDataTable;
		}
		
		
		
}
