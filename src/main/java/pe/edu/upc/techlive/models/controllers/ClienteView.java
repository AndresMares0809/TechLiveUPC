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


import pe.edu.upc.techlive.models.entities.Cliente;
import pe.edu.upc.techlive.models.services.ClienteService;
import pe.edu.upc.techlive.utils.Action;

@Named("clienteView")		// Creando un Beans
@ViewScoped
public class ClienteView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private List<Cliente> clientes;
	private Cliente cliente;
	private Cliente clienteSelected;
	private Cliente clienteSearch;
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
	private ClienteService clienteService;
	

	@PostConstruct
	public void init() {
		cleanForm();
		loadClientes();
		this.clienteSearch = new Cliente();
		action = Action.NONE;
		disabledAllButtom();
	}
	// Métodos que utilizan un Service
	public void loadClientes() {
		try {
			this.clientes = clienteService.findAll();
		} catch( Exception e ) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}	
	
	//------------------------------
	// Método que se ejecuta cuando hago click en 'Nuevo'
	public void newCliente() {
		action = Action.NEW;
		cleanForm();
		loadClientes();
		addMessageInfo("Creando nuevo Cliente");
		enabledButtomGrabar();
	}
	public void cleanForm( ) {
		this.cliente = new Cliente();
		this.clienteSelected = null;	
	}
	// Metodo para grabar el elemento ingresado en el formulario
	public void saveCliente() {
		try {
			if (action == Action.NEW) {
				clienteService.save(this.cliente);
				addMessageInfo("Se grabo de forma correcta el nuevo cliente");
			} 
			else if (action == Action.EDIT) {
				clienteService.update(this.cliente);
				addMessageInfo("Se actualizo de forma correcta el cliente");
			}	
			action = Action.NONE;
			cleanForm();
			loadClientes();
			disabledAllButtom();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}

	public void cancelSaveCliente() {
		cleanForm();
		loadClientes();
		disabledAllButtom();
	}
	// Metodo que se ejecuta cada vez que el usuario selecciona una fila del datatable
	public void selectCliente(SelectEvent<Cliente> e) {
		cleanForm();
		this.clienteSelected = e.getObject();
		enabledButtomEditarEliminar();
	}
	// Metodo que se ejecuta cada vez que el usuario des-selecciona una fila del datatable
	public void unselectCliente(UnselectEvent<Cliente> e) {
		cleanForm();
		this.clienteSelected = null;
		disabledAllButtom();
	}
	
	// Método que se ejecuta cuando hago click en el boton Editar
	public void editCliente() {
		if(clienteSelected != null) {
			action = Action.EDIT;
			cliente = clienteSelected;
			clienteSelected = null;
			addMessageInfo("Ya puedes modificar el cliente");
			enabledButtomGrabar();
		} 
		else {
			addMessageError("No hay ninguna Cliente seleccionado");
		}
	}
	
	// Método que se ejecuta cuando hace click en el boton 'Eliminar'
	public void deleteCliente() {
		if(clienteSelected != null) {
			try {
				clienteService.deleteById(clienteSelected.getId());
				action = Action.NONE;				
				addMessageInfo("Se elimino de forma correcta el cliente: " + clienteSelected.getDni());
				cleanForm();
				loadClientes();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println( e.getMessage() );
			}
			disabledAllButtom();
		}
		else {
			addMessageError("No hay ningun Cliente seleccionado");
		}
	}
	
	public void searchDNICliente() {
		try {
			this.clientes = clienteService.findByDireccion(clienteSearch.getDireccion());
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
	
	public List<Cliente> getClientes() {
		return clientes;
	}

	public ClienteService getClienteService() {
		return clienteService;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Cliente getClienteSelected() {
		return clienteSelected;
	}

	public Cliente getClienteSearch() {
		return clienteSearch;
	}

	public boolean isDisabledNuevo() {
		return disabledNuevo;
	}

	public boolean isDisabledGrabar() {
		return disabledGrabar;
	}

	public boolean isDisabledCancelar() {
		return disabledCancelar;
	}

	public boolean isDisabledEditar() {
		return disabledEditar;
	}

	public boolean isDisabledEliminar() {
		return disabledEliminar;
	}

	public String getStylePanelGrid() {
		return stylePanelGrid;
	}

	public String getStyleDataTable() {
		return StyleDataTable;
	}


	
}

