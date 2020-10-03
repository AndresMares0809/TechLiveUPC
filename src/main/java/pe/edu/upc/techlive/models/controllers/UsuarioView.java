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

import pe.edu.upc.techlive.models.entities.Usuario;
import pe.edu.upc.techlive.models.services.UsuarioService;
import pe.edu.upc.techlive.utils.Action;

@Named("usuarioView") // Creando un Beans
@ViewScoped
public class UsuarioView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Usuario> usuarios;
	private Usuario usuario;
	private Usuario usuarioSelected;
	private Usuario usuarioSearch;
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
	private UsuarioService usuarioService;

	@PostConstruct
	public void init() {

		cleanForm();
		loadUsuarios();
		this.usuarioSearch = new Usuario();
		action = Action.NONE;
		disabledAllButtom();
	}

	public void loadUsuarios() {
		try {
			this.usuarios = usuarioService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}

	public void newUsuario() {
		action = Action.NEW;
		cleanForm();
		loadUsuarios();
		addMessageInfo("Creando nuevo Usuario");
		enabledButtomGrabar();
	}

	public void cleanForm() {
		this.usuario = new Usuario();
		this.usuarioSelected = null;
	}

	public void saveUsuario() {
		try {
			if (action == Action.NEW) {
				usuarioService.save(this.usuario);
				addMessageInfo("Se grabo de forma correcta el nuevo usuario");
			} else if (action == Action.EDIT) {
				usuarioService.save(this.usuario);
				addMessageInfo("Se actualizo de forma correcta el usuario");
			}
			action = Action.NONE;
			cleanForm();
			loadUsuarios();
			disabledAllButtom();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}

	public void cancelSaveUsuario() {
		cleanForm();
		loadUsuarios();
		disabledAllButtom();
	}
	
	public void selectUsuario(SelectEvent<Usuario> e) {
		cleanForm();
		this.usuarioSelected = e.getObject();
		enabledButtomEditarEliminar();
	}
	
	public void unselectUsuario(UnselectEvent<Usuario> e) {
		cleanForm();
		this.usuarioSelected = null;
		disabledAllButtom();
	}
	
	public void editUsuario() {
		if(usuarioSelected != null) {
			action = Action.EDIT;
			usuario = usuarioSelected;
			usuarioSelected = null;
			addMessageInfo("Ya puedes modificar el usuario");
			enabledButtomGrabar();
		} 
		else {
			addMessageError("No hay ningun usuario seleccionado");
		}
	}
	
	public void deleteUsuario() {
		if(usuarioSelected != null) {
			try {
				usuarioService.deleteById(usuarioSelected.getId());
				action = Action.NONE;				
				addMessageInfo("Se elimino de forma correcta el usuario: " + usuarioSelected.getApellido() );
				cleanForm();
				loadUsuarios();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println( e.getMessage() );
			}
			disabledAllButtom();
		}
		else {
			addMessageError("No hay ningun usuario seleccionado");
		}
	}
	
	public void searchApellidoUsuario() {
		try {
			this.usuarios = usuarioService.findByApellido(usuarioSearch.getApellido());
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
	
	// gets y sets

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioSelected() {
		return usuarioSelected;
	}

	public void setUsuarioSelected(Usuario usuarioSelected) {
		this.usuarioSelected = usuarioSelected;
	}

	public Usuario getUsuarioSearch() {
		return usuarioSearch;
	}

	public void setUsuarioSearch(Usuario usuarioSearch) {
		this.usuarioSearch = usuarioSearch;
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

	public UsuarioService getUsuarioService() {
		return usuarioService;
	}

	public void setUsuarioService(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	
}
