package dad.recetapp.view.controller;

import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.TipoIngredienteItem;

public class IngredientesController {
	// lista que contiene los datos
	private List<TipoIngredienteItem> ingredientes;
	// lista "observable" que envuelve a la lista "variables" 
	private ObservableList<TipoIngredienteItem> ingredientesList;

	@FXML
	private TableView<TipoIngredienteItem> ingredientesTable;

	@FXML
	private TableColumn<TipoIngredienteItem, String> nombreColumn;

	@FXML
	private TextField nombreText;

	@FXML
	public void initialize() {

		ingredientesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		cargarTabla();

		nombreColumn.setCellValueFactory(new PropertyValueFactory<TipoIngredienteItem, String>("nombre"));
		nombreColumn.setCellFactory(TextFieldTableCell.<TipoIngredienteItem>forTableColumn());
		nombreColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TipoIngredienteItem,String>>() {

			@Override
			public void handle(CellEditEvent<TipoIngredienteItem, String> t) {
				TipoIngredienteItem editado = ((TipoIngredienteItem) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				editado.setNombre(t.getNewValue());	
				try {
					ServiceLocator.getTiposIngredientesService().modificarTipoIngrediente(editado);
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}



	private void cargarTabla() {
		try {
			ingredientes = ServiceLocator.getTiposIngredientesService().listarTipoIngredientes();
			ingredientesList = FXCollections.observableList(ingredientes);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		ingredientesTable.setItems(ingredientesList);
	}



	@FXML
	public void anadir() {
		// creamos un objeto "variable"
		TipoIngredienteItem ingrediente = new TipoIngredienteItem();
		if(nombreText.getText().equals("")){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Añadir");
			alertError.setHeaderText("Nombre vacio");
			alertError.setContentText("Por favor, introduzca un nombre");

			alertError.showAndWait();
		}else{
			ingrediente.setNombre(nombreText.getText());
			// si modificamos la lista "observable", se añade el item a la lista original
			// y además la tabla se entera automáticamente (porque la tabla está observando a la lista)
			try {
				ServiceLocator.getTiposIngredientesService().crearTipoIngrediente(ingrediente);
				ingredientes.clear();
				cargarTabla();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// vaciamos los cuadros de texto
			nombreText.clear(); // los mismo que setText("")
		}

	}

	@FXML
	public void eliminar() {
		if(ingredientesTable.getSelectionModel().isEmpty()){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Eliminar");
			alertError.setHeaderText("Seleccionar Fila");
			alertError.setContentText("Por favor, seleccione un elemento a eliminar");

			alertError.showAndWait();
		}else{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Eliminar");
			alert.setHeaderText("Eliminar Ingrediente");
			alert.setContentText("¿ Seguro que desea eliminar los elemento(s)?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				try {
					for (TipoIngredienteItem tipoingredienteitem : ingredientesTable.getSelectionModel().getSelectedItems()) {
						ServiceLocator.getTiposIngredientesService().eliminarTipoIngrediente(tipoingredienteitem.getId());
						ingredientesList.remove(tipoingredienteitem);
					}

				} catch (ServiceException e) {
					Alert alertError = new Alert(AlertType.ERROR);
					alertError.setTitle("Error Eliminar");
					alertError.setHeaderText("Error Eliminar");
					alertError.setContentText("Se ha producido un error al eliminar : "+ e.getMessage());

					alertError.showAndWait();
				}
			}
		}

	}
}
