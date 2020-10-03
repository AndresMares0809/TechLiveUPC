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

import pe.edu.upc.techlive.models.entities.Categoria;
import pe.edu.upc.techlive.models.entities.DetallePedido;
import pe.edu.upc.techlive.models.entities.Marca;
import pe.edu.upc.techlive.models.entities.Producto;
import pe.edu.upc.techlive.models.entities.Vendedor;
import pe.edu.upc.techlive.models.services.CategoriaService;
import pe.edu.upc.techlive.models.services.DetallePedidoService;
import pe.edu.upc.techlive.models.services.MarcaService;
import pe.edu.upc.techlive.models.services.ProductoService;
import pe.edu.upc.techlive.models.services.VendedorService;
import pe.edu.upc.techlive.utils.Action;

@Named("productoView")		
@ViewScoped
public class ProductoView implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Producto> productos;
	private Producto producto;
	private Producto productoSelected;
	private Producto productoSearch;
	private Action action;
	
	private List<Vendedor> vendedores;

	private List<Categoria> categorias;

	private List<Marca> marcas;
	
	private List<DetallePedido> detallePedidos;
	
	
	private boolean disabledNuevo;
	private boolean disabledGrabar;
	private boolean disabledCancelar;
	private boolean disabledEditar;
	private boolean disabledEliminar;	

	private String stylePanelGrid;
	private String StyleDataTable;
	
	@Inject
	private ProductoService productoService;
	
	@Inject
	private VendedorService vendedorService;

	@Inject
	private CategoriaService categoriaService;
	
	@Inject
	private MarcaService marcaService;

	@Inject
	private DetallePedidoService detallePedidoService;
	
	
	@PostConstruct
	public void init() {
		cleanForm();
		loadProductos();
		loadVendedores();
		loadCategorias();
		loadMarcas();
		loadDetallePedidos();
		this.productoSearch = new Producto();
		action = Action.NONE;
		disabledAllButtom();
	}

	public void loadProductos() {
		try {
			this.productos = productoService.findAll();
		} catch( Exception e ) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}	
	
	public void loadVendedores() {
		try {
			this.vendedores = vendedorService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}

	public void loadCategorias() {
		try {
			this.categorias = categoriaService.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}
	
	public void loadMarcas() {
		try {
			this.marcas = marcaService.findAll();
		} catch (Exception e) {
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
	
	public Optional<Vendedor> loadVendedor( Integer id ) {
		try {
			return vendedorService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
		return Optional.empty();
	}
	
	public Optional<Categoria> loadCategoria( Integer id ) {
		try {
			return categoriaService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
		return Optional.empty();
	}
	
	public Optional<Marca> loadMarca( Integer id ) {
		try {
			return marcaService.findById(id);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
		return Optional.empty();
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
	
	public void newproducto() {
		action = Action.NEW;
		cleanForm();
		loadProductos();
		addMessageInfo("Creando nuevo Producto");
		enabledButtomGrabar();
	}
	
	public void cleanForm( ) {
		this.producto = new Producto();
		this.productoSelected = null;	
	}

	public void saveProducto() {
		try {
			if (action == Action.NEW || action == Action.EDIT) {
				verifyVendedor();
				verifyCategoria();
				verifyMarca();
				verifyDetallePedido();
				if (action == Action.NEW) {
					productoService.save(this.producto);
					addMessageInfo("Se grabo de forma correcta el nuevo producto");
				} 
				else if (action == Action.EDIT) {
					
					productoService.update(this.producto);
					addMessageInfo("Se actualizo de forma correcta el producto");
				}	
				action = Action.NONE;
				cleanForm();
				loadProductos();
				disabledAllButtom();
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
			System.err.println( e.getMessage() );
		}
	}
	
	public void verifyVendedor() {
		if (action == Action.NEW) {
			changeVendedor();
		} 
		else if (action == Action.EDIT) {
			if( this.producto.getVendedor() != null ) {
				if( ! this.producto.getVendedorlId().equals( this.producto.getVendedor().getId() ) ) {
					changeVendedor();
				}
			}
			else {
				changeVendedor();
			}				
		}
	}
	
	public void verifyCategoria() {
		if (action == Action.NEW) {
			changeCategoria();
		} 
		else if (action == Action.EDIT) {
			if( this.producto.getCategoria() != null ) {
				if( ! this.producto.getCategorialId().equals( this.producto.getCategoria().getId() ) ) {
					changeCategoria();
				}
			}
			else {
				changeCategoria();
			}				
		}
	}
	
	public void verifyMarca() {
		if (action == Action.NEW) {
			changeMarca();
		} 
		else if (action == Action.EDIT) {
			if( this.producto.getMarca() != null ) {
				if( ! this.producto.getMarcaId().equals( this.producto.getMarca().getId() ) ) {
					changeMarca();
				}
			}
			else {
				changeMarca();
			}				
		}
	}

	public void verifyDetallePedido() {
		if (action == Action.NEW) {
			changeDetallePedido();
		} 
		else if (action == Action.EDIT) {
			if( this.producto.getDetallePedido() != null ) {
				if( ! this.producto.getDetallePedidoId().equals( this.producto.getDetallePedido().getId() ) ) {
					changeDetallePedido();
				}
			}
			else {
				changeDetallePedido();
			}				
		}
	}
	
	public void changeVendedor() {
		Optional<Vendedor> optional = loadVendedor( this.producto.getVendedorlId() );
		if(optional.isPresent()) {
			this.producto.setVendedor( optional.get() );
		}
	}
	
	public void changeCategoria() {
		Optional<Categoria> optional = loadCategoria( this.producto.getCategorialId() );
		if(optional.isPresent()) {
			this.producto.setCategoria( optional.get() );
		}
	}
	
	public void changeMarca() {
		Optional<Marca> optional = loadMarca( this.producto.getMarcaId() );
		if(optional.isPresent()) {
			this.producto.setMarca( optional.get() );
		}
	}
	
	public void changeDetallePedido() {
		Optional<DetallePedido> optional = loadDetallePedido( this.producto.getDetallePedidoId() );
		if(optional.isPresent()) {
			this.producto.setDetallePedido( optional.get() );
		}
	}
	
	public void cancelSaveProducto() {
		cleanForm();
		loadProductos();
		disabledAllButtom();
	}

	public void selectProducto(SelectEvent<Producto> e) {
		cleanForm();
		this.productoSelected = e.getObject();
		if(this.getProductoSelected().getVendedor() != null || this.getProductoSelected().getCategoria() != null || this.getProductoSelected().getMarca() != null || this.getProductoSelected().getDetallePedido() != null) {
			this.productoSelected.setVendedorlId(getProductoSelected().getVendedor().getId());
			this.productoSelected.setCategorialId(getProductoSelected().getCategoria().getId());
			this.productoSelected.setMarcaId(getProductoSelected().getMarca().getId());
			this.productoSelected.setDetallePedidoId(this.getProductoSelected().getDetallePedido().getId());
		}		
		enabledButtomEditarEliminar();
	}
	
	public void unselectProducto(UnselectEvent<Producto> e) {
		cleanForm();
		this.productoSelected = null;
		disabledAllButtom();
	}
	
	public void editProducto() {
		if(productoSelected != null) {
			action = Action.EDIT;
			producto = productoSelected;
			productoSelected = null;
			addMessageInfo("Ya puedes modificar el producto");
			enabledButtomGrabar();
		} 
		else {
			addMessageError("No hay ningun producto seleccionado");
		}
	}
	
	public void deleteProductoo() {
		if(productoSelected != null) {
			try {
				detallePedidoService.deleteById(productoSelected.getId());
				action = Action.NONE;				
				addMessageInfo("Se elimino de forma correcta el producto: " + productoSelected.getId() );
				cleanForm();
				loadProductos();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err.println( e.getMessage() );
			}
			disabledAllButtom();
		}
		else {
			addMessageError("No hay ningun producto seleccionado");
		}
	}
	
	public void searchModeloProducto() {
		try {
			this.productos = productoService.findByModelo(productoSearch.getModelo());
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

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Producto getProductoSelected() {
		return productoSelected;
	}

	public void setProductoSelected(Producto productoSelected) {
		this.productoSelected = productoSelected;
	}

	public Producto getProductoSearch() {
		return productoSearch;
	}

	public void setProductoSearch(Producto productoSearch) {
		this.productoSearch = productoSearch;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public List<Vendedor> getVendedores() {
		return vendedores;
	}

	public void setVendedores(List<Vendedor> vendedores) {
		this.vendedores = vendedores;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Marca> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}

	public List<DetallePedido> getDetallePedidos() {
		return detallePedidos;
	}

	public void setDetallePedidos(List<DetallePedido> detallePedidos) {
		this.detallePedidos = detallePedidos;
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

	public ProductoService getProductoService() {
		return productoService;
	}

	public void setProductoService(ProductoService productoService) {
		this.productoService = productoService;
	}

	public VendedorService getVendedorService() {
		return vendedorService;
	}

	public void setVendedorService(VendedorService vendedorService) {
		this.vendedorService = vendedorService;
	}

	public CategoriaService getCategoriaService() {
		return categoriaService;
	}

	public void setCategoriaService(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}

	public MarcaService getMarcaService() {
		return marcaService;
	}

	public void setMarcaService(MarcaService marcaService) {
		this.marcaService = marcaService;
	}

	public DetallePedidoService getDetallePedidoService() {
		return detallePedidoService;
	}

	public void setDetallePedidoService(DetallePedidoService detallePedidoService) {
		this.detallePedidoService = detallePedidoService;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
