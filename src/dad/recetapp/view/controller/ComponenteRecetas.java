package dad.recetapp.view.controller;

import java.io.IOException;
import java.util.Optional;

import dad.recetapp.services.items.IngredienteItem;
import dad.recetapp.services.items.InstruccionItem;
import dad.recetapp.services.items.SeccionItem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ComponenteRecetas extends BorderPane{

	// lista que contiene los datos
//	private List<IngredienteItem> ingredientes;
//	private List<InstruccionItem> instrucciones;
	// lista "observable" que envuelve a la lista "variables" 
	private ObservableList<IngredienteItem> ingredientesList;
	private ObservableList<InstruccionItem> instrucionesList;

	@FXML
	private BorderPane borderpane;
	@FXML
	private TextField seccionText;

	@FXML
	private TableView<IngredienteItem> ingredienteTable;
	@FXML
	private TableColumn<IngredienteItem, Integer> cantidadColumn;
	@FXML
	private TableColumn<IngredienteItem, String> medidaColumn;
	@FXML
	private TableColumn<IngredienteItem, String> tipoColumn;
	@FXML
	private Button nuevoIngredienteButton;
	@FXML
	private Button editarIngredienteButton;
	@FXML
	private Button eliminarIngredienteButton;
	@FXML
	private Button nuevaInstruccionButton;
	@FXML
	private Button editarInstruccionButton;
	@FXML
	private Button eliminarInstruccionButton;
	@FXML
	private TableView<InstruccionItem> instruccionTable;
	@FXML
	private TableColumn<InstruccionItem, Integer> ordenColumn;
	@FXML
	private TableColumn<InstruccionItem, String> descripcionColumn;

	private SeccionItem seccion = new SeccionItem();
	private Stage ventana;
	private Tab tab;

	public ComponenteRecetas() {    
		super();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/dad/recetapp/view/ComponenteRecetas.fxml"));


		fxmlLoader.setRoot(this); 
		fxmlLoader.setController(this); 
		try { 
			fxmlLoader.load(); 
		} catch (IOException exception) { 
			throw new RuntimeException(exception);
		} 
	}


	public Tab getTab() {
		return tab;
	}

	public void setTab(Tab tab) {
		this.tab = tab;
	}

	public SeccionItem getSeccion() {
		seccion.setNombre(seccionText.getText());
		return seccion;
	}

	public void setSeccion(SeccionItem seccion2) {
		this.seccion = seccion2;
		seccionText.setText(seccion.getNombre());
		ingredientesList = FXCollections.observableList(seccion.getIngredientes());
		instrucionesList = FXCollections.observableList(seccion.getInstrucciones());

		instruccionTable.setItems(instrucionesList);
		ingredienteTable.setItems(ingredientesList);
	}

	@FXML
	public void initialize(){
		seccionText.setText("Sin titulo");
		
		seccionText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,String oldValue, String newValue) {
				tab.setText(seccionText.getText());
			}
		});
		
		nuevoIngredienteButton.setGraphic(new ImageView("dad/recetapp/ui/images/add-icon-20x20.png"));
		editarIngredienteButton.setGraphic(new ImageView("dad/recetapp/ui/images/edit-icon-20x20.png"));
		eliminarIngredienteButton.setGraphic(new ImageView("dad/recetapp/ui/images/delete-icon-20x20.png"));

		nuevaInstruccionButton.setGraphic(new ImageView("dad/recetapp/ui/images/add-icon-20x20.png"));
		editarInstruccionButton.setGraphic(new ImageView("dad/recetapp/ui/images/edit-icon-20x20.png"));
		eliminarInstruccionButton.setGraphic(new ImageView("dad/recetapp/ui/images/delete-icon-20x20.png"));

		
		//TABLA INSTRUCCIONES
		cantidadColumn.setCellValueFactory(new PropertyValueFactory<IngredienteItem, Integer>("cantidad"));
		
		ordenColumn.setCellValueFactory(new PropertyValueFactory<InstruccionItem, Integer>("orden"));

		descripcionColumn.setCellValueFactory(new PropertyValueFactory<InstruccionItem, String>("descripcion"));
		descripcionColumn.setCellFactory(TextFieldTableCell.<InstruccionItem>forTableColumn());
		
		
		//TABLA INGREDIENTES
		
		medidaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<IngredienteItem,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(
					CellDataFeatures<IngredienteItem, String> param) {
				ObservableValue<String> a = new ObservableValue<String>() {


					@Override
					public String getValue() {
						// TODO Auto-generated method stub
						return ((IngredienteItem) param.getValue()).getMedida().getNombre();
					}

					@Override
					public void addListener(InvalidationListener listener) {
						// TODO Auto-generated method stub

					}

					@Override
					public void removeListener(InvalidationListener listener) {
						// TODO Auto-generated method stub

					}

					@Override
					public void addListener(
							ChangeListener<? super String> listener) {
						// TODO Auto-generated method stub

					}

					@Override
					public void removeListener(
							ChangeListener<? super String> listener) {
						// TODO Auto-generated method stub

					}


				};
				return a;
			}; 

		});
		medidaColumn.setCellFactory(TextFieldTableCell.<IngredienteItem>forTableColumn());
		
		tipoColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<IngredienteItem,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(
					CellDataFeatures<IngredienteItem, String> param) {
				ObservableValue<String> a = new ObservableValue<String>() {


					@Override
					public String getValue() {
						// TODO Auto-generated method stub
						return ((IngredienteItem) param.getValue()).getTipo().getNombre();
					}

					@Override
					public void addListener(InvalidationListener listener) {
						// TODO Auto-generated method stub

					}

					@Override
					public void removeListener(InvalidationListener listener) {
						// TODO Auto-generated method stub

					}

					@Override
					public void addListener(
							ChangeListener<? super String> listener) {
						// TODO Auto-generated method stub

					}

					@Override
					public void removeListener(
							ChangeListener<? super String> listener) {
						// TODO Auto-generated method stub

					}


				};
				return a;
			}; 

		});

	}

	// Event Listener on Button[#nuevoIngredienteButton].onAction
	@FXML
	public void nuevoIngrediente(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(ComponenteRecetas.class.getResource("/dad/recetapp/view/NuevoIngredienteView.fxml"));
			BorderPane ventanaDos;
			ventanaDos = (BorderPane) loader.load();
			ventana = new Stage();
			ventana.setTitle("Nuevo Ingrediente para '"+seccion.getNombre()+"'");
			ventana.getIcons().add(new Image("/dad/recetapp/ui/images/logo.png"));
			Scene scene = new Scene(ventanaDos);
			ventana.setScene(scene);

			ventana.initOwner(borderpane.getScene().getWindow());
			ventana.initModality(Modality.WINDOW_MODAL);
			ventana.showAndWait();
			NuevoIngredienteController nuevoIngredienteController = (NuevoIngredienteController)loader.getController();
			if(!(nuevoIngredienteController.getIngrediente()==null)){
				IngredienteItem ingredienteitem = nuevoIngredienteController.getIngrediente();
				seccion.getIngredientes().add(ingredienteitem);
				ingredientesList = FXCollections.observableList(seccion.getIngredientes());
				ingredienteTable.setItems(ingredientesList);
			}


		} catch (IOException e) {
			error(e.getMessage());

		}
	}
	// Event Listener on Button[#editarIngredienteButton].onAction
	@FXML
	public void editarIngrediente(ActionEvent event) {
		if(ingredienteTable.getSelectionModel().isEmpty()){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Editar");
			alertError.setHeaderText("Seleccionar Fila");
			alertError.setContentText("Por favor, seleccione un ingrediente a editar");

			alertError.showAndWait();
		}else{

			try {
				FXMLLoader loader = new FXMLLoader(ComponenteRecetas.class.getResource("/dad/recetapp/view/EditarIngredienteView.fxml"));
				BorderPane ventanaDos;
				ventanaDos = (BorderPane) loader.load();
				ventana = new Stage();
				ventana.setTitle("Editar Ingrediente para '"+seccion.getNombre()+"'");
				ventana.getIcons().add(new Image("/dad/recetapp/ui/images/logo.png"));
				Scene scene = new Scene(ventanaDos);
				ventana.setScene(scene);

				ventana.initOwner(borderpane.getScene().getWindow());
				ventana.initModality(Modality.WINDOW_MODAL);
				IngredienteItem ingViejo = ingredienteTable.getSelectionModel().getSelectedItem();
				EditarIngredienteController editarIngredienteController = (EditarIngredienteController)loader.getController();
				editarIngredienteController.setIngrediente(ingViejo);
				ventana.showAndWait();
				if((editarIngredienteController.isGuardar())){
					IngredienteItem ingredienteitem = editarIngredienteController.getIngrediente();
					seccion.getIngredientes().remove(ingViejo);
					seccion.getIngredientes().add(ingredienteitem);
					ingredientesList = FXCollections.observableList(seccion.getIngredientes());
					ingredienteTable.setItems(ingredientesList);
				}


			} catch (IOException e) {
				error(e.getMessage());

			}
		}	}
	// Event Listener on Button[#eliminarIngredienteButton].onAction
	@FXML
	public void eliminarIngrediente(ActionEvent event) {
		if(ingredienteTable.getSelectionModel().isEmpty()){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Eliminar");
			alertError.setHeaderText("Seleccionar Fila");
			alertError.setContentText("Por favor, seleccione un elemento a eliminar");

			alertError.showAndWait();
		}else{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Eliminar");
			alert.setHeaderText("Eliminar Ingrediente");
			alert.setContentText("� Seguro que desea eliminar los elemento(s)?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				for (IngredienteItem ingredienteitem : ingredienteTable.getSelectionModel().getSelectedItems()) {
					seccion.getIngredientes().remove(ingredienteitem);
					ingredientesList = FXCollections.observableList(seccion.getIngredientes());
					ingredienteTable.setItems(ingredientesList);
				}
			}
		}
	}
	// Event Listener on Button[#nuevaInstruccionButton].onAction
	@FXML
	public void nuevaInstruccion(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(ComponenteRecetas.class.getResource("/dad/recetapp/view/NuevaInstruccionView.fxml"));
			BorderPane ventanaDos;
			ventanaDos = (BorderPane) loader.load();
			ventana = new Stage();
			ventana.setTitle("Nueva Instruccion para '"+seccion.getNombre()+"'");
			ventana.getIcons().add(new Image("/dad/recetapp/ui/images/logo.png"));
			Scene scene = new Scene(ventanaDos);
			ventana.setScene(scene);

			ventana.initOwner(borderpane.getScene().getWindow());
			ventana.initModality(Modality.WINDOW_MODAL);
			ventana.showAndWait();
			NuevaInstruccionController nuevaInstruccionController = (NuevaInstruccionController)loader.getController();
			if(!(nuevaInstruccionController.getInstruccion()==null)){
				InstruccionItem instruccionitem = nuevaInstruccionController.getInstruccion();
				seccion.getInstrucciones().add(instruccionitem);
				instrucionesList = FXCollections.observableList(seccion.getInstrucciones());
				instruccionTable.setItems(instrucionesList);
			}


		} catch (IOException e) {
			error(e.getMessage());

		}
	}
	// Event Listener on Button[#editarInstruccionButton].onAction
	@FXML
	public void editarInstruccion(ActionEvent event) {
		if(instruccionTable.getSelectionModel().isEmpty()){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Editar");
			alertError.setHeaderText("Seleccionar Fila");
			alertError.setContentText("Por favor, seleccione una instruccion a editar");

			alertError.showAndWait();
		}else{

			try {
				FXMLLoader loader = new FXMLLoader(ComponenteRecetas.class.getResource("/dad/recetapp/view/EditarInstruccionView.fxml"));
				BorderPane ventanaDos;
				ventanaDos = (BorderPane) loader.load();
				ventana = new Stage();
				ventana.setTitle("Editar Instruccion para '"+seccion.getNombre()+"'");
				ventana.getIcons().add(new Image("/dad/recetapp/ui/images/logo.png"));
				Scene scene = new Scene(ventanaDos);
				ventana.setScene(scene);

				ventana.initOwner(borderpane.getScene().getWindow());
				ventana.initModality(Modality.WINDOW_MODAL);
				InstruccionItem inViejo = instruccionTable.getSelectionModel().getSelectedItem();
				EditarInstruccionController editarInstruccionController = (EditarInstruccionController)loader.getController();
				editarInstruccionController.setInstruccion(inViejo);
				ventana.showAndWait();
				if((editarInstruccionController.isGuardar())){
					InstruccionItem instruccionitem = editarInstruccionController.getInstruccion();
					seccion.getInstrucciones().remove(inViejo);
					seccion.getInstrucciones().add(instruccionitem);
					instrucionesList = FXCollections.observableList(seccion.getInstrucciones());
					instruccionTable.setItems(instrucionesList);
					instruccionTable.getSelectionModel().clearSelection();
				}


			} catch (IOException e) {
				error(e.getMessage());
			}
		}
	}
	// Event Listener on Button[#eliminarInstruccionButton].onAction
	@FXML
	public void eliminarInstruccion(ActionEvent event) {
		if(instruccionTable.getSelectionModel().isEmpty()){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Eliminar");
			alertError.setHeaderText("Seleccionar Fila");
			alertError.setContentText("Por favor, seleccione un elemento a eliminar");

			alertError.showAndWait();
		}else{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Eliminar");
			alert.setHeaderText("Eliminar Instruccion");
			alert.setContentText("� Seguro que desea eliminar los elemento(s)?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				for (InstruccionItem instruccionIndex: instruccionTable.getSelectionModel().getSelectedItems()) {
//					seccion.getInstrucciones().remove(instruccionitem);
//					instrucionesList = FXCollections.observableList(seccion.getInstrucciones());
//					instruccionTable.setItems(instrucionesList);
					
					instrucionesList.remove(instruccionIndex);
				}
			}
		}

	}
	
	public void error(String mensaje){
		Alert alertError = new Alert(AlertType.ERROR);
		alertError.setTitle("Error");
		alertError.setHeaderText("Error ");
		alertError.setContentText("Se ha producido un error: "+ mensaje);

		alertError.showAndWait();
	}
}
