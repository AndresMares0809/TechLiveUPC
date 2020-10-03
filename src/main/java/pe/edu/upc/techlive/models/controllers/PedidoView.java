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

import pe.edu.upc.techlive.models.entities.Cliente;
import pe.edu.upc.techlive.models.entities.DetallePedido;
import pe.edu.upc.techlive.models.entities.Pedido;
import pe.edu.upc.techlive.models.services.ClienteService;
import pe.edu.upc.techlive.models.services.DetallePedidoService;
import pe.edu.upc.techlive.models.services.PedidoService;
import pe.edu.upc.techlive.utils.Action;

@Named("pedidoView")		
@ViewScoped
public class PedidoView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Pedido> pedidos;
	private Pedido pedido;
	private Pedido pedidoSelected;
	private Pedido pedidoSearch;
	private Action action;
	
	private List<DetallePedido> detallePedidos;
	
	private List<Cliente> clientes;
	
	private boolean disabledNuevo;
	private boolean disabledGrabar;
	private boolean disabledCancelar;
	private boolean disabledEditar;
	private boolean disabledEliminar;	

	private String stylePanelGrid;
	private String StyleDataTable;
	
	@Inject
	private PedidoService pedidoService;
	
	@Inject
	private DetallePedidoService detallePedidoService;
	
	@Inject
	private ClienteService clienteService;

	@PostConstruct
	public void init() {
		cleanForm();
		loadPedidos();
		loadDetallePedidos();
		loadClientes();
		this.pedidoSearch = new Pedido();
		action = Action.NONE;
		disabledAllButtom();
	}

	public void loadPedidos() {
		try {
			this.pedidos = pedidoService.findAll();
		} catch( Exception e ) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}	
	
	public void loadDetallePedidos() {
		try {
			this.detallePedidos = detallePedidoService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}
	
	public void loadClientes() {
		try {
			this.clientes = clienteService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}
	
	public Optional<DetallePedido> loadDetallePedido( Integer id ) {
		try {
			return detallePedidoService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
		return Optional.empty();
	}
	
	public Optional<Cliente> loadCliente( Integer id ) {
		try {
			return clienteService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
		return Optional.empty();
	}
	
	public void newPedido() {
		action = Action.NEW;
		cleanForm();
		loadPedidos();
		addMessageInfo("Creando nuevo Pedido");
		enabledButtomGrabar();
	}
	
	public void cleanForm( ) {
		this.pedido = new Pedido();
		this.pedidoSelected = null;	
	}

	public void savePedido() {
		try {
			if (action == Action.NEW || action == Action.EDIT) {
				verifyDetallePedido();
				verifyCliente();
				if (action == Action.NEW) {
					pedidoService.save(this.pedido);
					addMessageInfo("Se grabo de forma correcta el nuevo pedido");
				} 
				else if (action == Action.EDIT) {
					
					pedidoService.update(this.pedido);
					addMessageInfo("Se actualizo de forma correcta el pedido");
				}	
				action = Action.NONE;
				cleanForm();
				loadPedidos();
				disabledAllButtom();
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}
	
	public void verifyDetallePedido() {
		if (action == Action.NEW) {
			changeDetallePedido();
		} 
		else if (action == Action.EDIT) {
			if( this.pedido.getDetallePedido() != null ) {
				if( ! this.pedido.getDetallePedidoId().equals( this.pedido.getDetallePedido().getId() ) ) {
					changeDetallePedido();
				}
			}
			else {
				changeDetallePedido();
			}				
		}
	}
	
	public void verifyCliente() {
		if (action == Action.NEW) {
			changeCliente();
		} 
		else if (action == Action.EDIT) {
			if( this.pedido.getCliente() != null ) {
				if( ! this.pedido.getClienteId().equals( this.pedido.getCliente().getId() ) ) {
					changeCliente();
				}
			}
			else {
				changeCliente();
			}				
		}
	}
	
	public void changeDetallePedido() {
		Optional<DetallePedido> optional = loadDetallePedido( this.pedido.getDetallePedidoId() );
		if(optional.isPresent()) {
			this.pedido.setDetallePedido( optional.get() );
		}
	}
	
	public void changeCliente() {
		Optional<Cliente> optional = loadCliente( this.pedido.getClienteId() );
		if(optional.isPresent()) {
			this.pedido.setCliente( optional.get() );
		}
	}
	
	public void cancelSavePedido() {
		cleanForm();
		loadPedidos();
		disabledAllButtom();
	}

	public void selectPedido(SelectEvent<Pedido> e) {
		cleanForm();
		this.pedidoSelected = e.getObject();
		if(this.getPedidoSelected().getDetallePedido() != null || this.getPedidoSelected().getCliente() != null) {
			this.pedidoSelected.setDetallePedidoId(this.getPedidoSelected().getDetallePedido().getId());
			this.pedidoSelected.setClienteId(getPedidoSelected().getCliente().getId());
		}		
		enabledButtomEditarEliminar();
	}
	
	public void unselectPedido(UnselectEvent<Pedido> e) {
		cleanForm();
		this.pedidoSelected = null;
		disabledAllButtom();
	}
	
	public void editPedido() {
		if(pedidoSelected != null) {
			action = Action.EDIT;
			pedido = pedidoSelected;
			pedidoSelected = null;
			addMessageInfo("Ya puedes modificar el pedido");
			enabledButtomGrabar();
		} 
		else {
			addMessageError("No hay ningun detalle de pedido seleccionado");
		}
	}
	
	public void deletePedido() {
		if(pedidoSelected != null) {
			try {
				detallePedidoService.deleteById(pedidoSelected.getId());
				action = Action.NONE;				
				addMessageInfo("Se elimino de forma correcta el pedido: " + pedidoSelected.getId() );
				cleanForm();
				loadPedidos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println( e.getMessage() );
			}
			disabledAllButtom();
		}
		else {
			addMessageError("No hay ningun pedido seleccionado");
		}
	}
	
	public void searchIdPedido() {
		try {
			this.pedidos = pedidoService.findByid(pedidoSearch.getId());
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

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Pedido getPedidoSelected() {
		return pedidoSelected;
	}

	public void setPedidoSelected(Pedido pedidoSelected) {
		this.pedidoSelected = pedidoSelected;
	}

	public Pedido getPedidoSearch() {
		return pedidoSearch;
	}

	public void setPedidoSearch(Pedido pedidoSearch) {
		this.pedidoSearch = pedidoSearch;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public List<DetallePedido> getDetallePedidos() {
		return detallePedidos;
	}

	public void setDetallePedidos(List<DetallePedido> detallePedidos) {
		this.detallePedidos = detallePedidos;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
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

	public PedidoService getPedidoService() {
		return pedidoService;
	}

	public void setPedidoService(PedidoService pedidoService) {
		this.pedidoService = pedidoService;
	}

	public DetallePedidoService getDetallePedidoService() {
		return detallePedidoService;
	}

	public void setDetallePedidoService(DetallePedidoService detallePedidoService) {
		this.detallePedidoService = detallePedidoService;
	}

	public ClienteService getClienteService() {
		return clienteService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	
	
	
}
