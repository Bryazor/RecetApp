package dad.recetapp.view.controller;

import java.util.List;
import java.util.Optional;

import com.sun.xml.internal.bind.v2.runtime.output.ForkXmlOutput;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;
import dad.recetapp.services.items.IngredienteItem;
import dad.recetapp.services.items.InstruccionItem;
import dad.recetapp.services.items.RecetaListItem;
import dad.recetapp.services.items.SeccionItem;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;

public class CPNuevaRecetaController {

	// lista que contiene los datos
	private List<IngredienteItem> ingredientes;
	private List<InstruccionItem> instrucciones;
	// lista "observable" que envuelve a la lista "variables" 
	private ObservableList<IngredienteItem> ingredientesList;
	private ObservableList<InstruccionItem> instrucionesList;

	@FXML
	private TextField seccionText;
	@FXML
	private Button menosButton;
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




	public SeccionItem getSeccion() {
		return seccion;
	}

	public void setSeccion(SeccionItem seccion2) {
		this.seccion = seccion2;
		seccionText.setText(seccion.getNombre());
		ingredientes = seccion.getIngredientes();
		ingredientesList = FXCollections.observableList(ingredientes);
		instrucciones = seccion.getInstrucciones();
		instrucionesList = FXCollections.observableList(instrucciones);

		instruccionTable.setItems(instrucionesList);
		ingredienteTable.setItems(ingredientesList);
		System.out.println("meto seccion");
	}

	@FXML
	public void initialize(){
		nuevoIngredienteButton.setGraphic(new ImageView("dad/recetapp/ui/images/add-icon-20x20.png"));
		editarIngredienteButton.setGraphic(new ImageView("dad/recetapp/ui/images/edit-icon-20x20.png"));
		eliminarIngredienteButton.setGraphic(new ImageView("dad/recetapp/ui/images/delete-icon-20x20.png"));

		nuevaInstruccionButton.setGraphic(new ImageView("dad/recetapp/ui/images/add-icon-20x20.png"));
		editarInstruccionButton.setGraphic(new ImageView("dad/recetapp/ui/images/edit-icon-20x20.png"));
		eliminarInstruccionButton.setGraphic(new ImageView("dad/recetapp/ui/images/delete-icon-20x20.png"));

		cantidadColumn.setCellValueFactory(new PropertyValueFactory<IngredienteItem, Integer>("cantidad"));


		medidaColumn.setCellValueFactory(new PropertyValueFactory<IngredienteItem, String>("medida"));
		medidaColumn.setCellFactory(TextFieldTableCell.<IngredienteItem>forTableColumn());

		ordenColumn.setCellValueFactory(new PropertyValueFactory<InstruccionItem, Integer>("orden"));



		descripcionColumn.setCellValueFactory(new PropertyValueFactory<InstruccionItem, String>("descripcion"));
		descripcionColumn.setCellFactory(TextFieldTableCell.<InstruccionItem>forTableColumn());

	}

	// Event Listener on Button[#menosButton].onAction
	@FXML
	public void menos(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#nuevoIngredienteButton].onAction
	@FXML
	public void nuevoIngrediente(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#editarIngredienteButton].onAction
	@FXML
	public void editarIngrediente(ActionEvent event) {
		// TODO Autogenerated
	}
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
		// TODO Autogenerated
	}
	// Event Listener on Button[#editarInstruccionButton].onAction
	@FXML
	public void editarInstruccion(ActionEvent event) {
		// TODO Autogenerated
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
				for (InstruccionItem instruccionitem : instruccionTable.getSelectionModel().getSelectedItems()) {
					seccion.getInstrucciones().remove(instruccionitem);
					instrucionesList = FXCollections.observableList(seccion.getInstrucciones());
					instruccionTable.setItems(instrucionesList);
				}
			}
		}

	}
}
