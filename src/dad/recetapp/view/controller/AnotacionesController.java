package dad.recetapp.view.controller;

import java.util.List;
import java.util.Optional;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.TipoAnotacionItem;
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

public class AnotacionesController {

	// lista que contiene los datos
	private List<TipoAnotacionItem> anotaciones;
	// lista "observable" que envuelve a la lista "variables" 
	private ObservableList<TipoAnotacionItem> anotacionesList;

	@FXML
	private TableView<TipoAnotacionItem> anotacionesTable;

	@FXML
	private TableColumn<TipoAnotacionItem, String> descripcionColumn;

	@FXML
	private TextField descripcionText;

	@FXML
	public void initialize() {
		anotacionesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		cargarTabla();

		descripcionColumn.setCellValueFactory(new PropertyValueFactory<TipoAnotacionItem, String>("descripcion"));
		descripcionColumn.setCellFactory(TextFieldTableCell.<TipoAnotacionItem>forTableColumn());
		descripcionColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<TipoAnotacionItem,String>>() {

			@Override
			public void handle(CellEditEvent<TipoAnotacionItem, String> t) {
				TipoAnotacionItem editado = ((TipoAnotacionItem) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				editado.setDescripcion(t.getNewValue());	
				try {
					ServiceLocator.getTiposAnotacionesService().modificarTipoAnotacion(editado);
				} catch (ServiceException e) {
					error(e.getMessage());
				}
			}
		});

	}



	private void cargarTabla() {
		try {
			anotaciones = ServiceLocator.getTiposAnotacionesService().listarTipoAnotaciones();
			anotacionesList = FXCollections.observableList(anotaciones);
		} catch (ServiceException e) {
			error(e.getMessage());
		}

		anotacionesTable.setItems(anotacionesList);
	}



	@FXML
	public void anadir() {
		TipoAnotacionItem anotacion = new TipoAnotacionItem();
		if(descripcionText.getText().equals("")){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Añadir");
			alertError.setHeaderText("Descripcion vacia");
			alertError.setContentText("Por favor, introduzca una descripcion");

			alertError.showAndWait();
		}else{
			anotacion.setDescripcion(descripcionText.getText());
			try {
				ServiceLocator.getTiposAnotacionesService().crearTipoAnotacion(anotacion);
				anotaciones.clear();
				cargarTabla();
			} catch (ServiceException e) {
				error(e.getMessage());
			}
			
			descripcionText.clear();
		}

	}

	@FXML
	public void eliminar() {
		if(anotacionesTable.getSelectionModel().isEmpty()){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Eliminar");
			alertError.setHeaderText("Seleccionar Fila");
			alertError.setContentText("Por favor, seleccione un elemento a eliminar");

			alertError.showAndWait();
		}else{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Eliminar");
			alert.setHeaderText("Eliminar Anotacion");
			alert.setContentText("¿ Seguro que desea eliminar los elemento(s)?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				try {
					for (TipoAnotacionItem tipoanotacionitem : anotacionesTable.getSelectionModel().getSelectedItems()) {
						ServiceLocator.getTiposAnotacionesService().eliminarTipoAnotacion(tipoanotacionitem.getId());
						anotacionesList.remove(tipoanotacionitem);
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

	public void error(String mensaje){
		Alert alertError = new Alert(AlertType.ERROR);
		alertError.setTitle("Error");
		alertError.setHeaderText("Error ");
		alertError.setContentText("Se ha producido un error: "+ mensaje);

		alertError.showAndWait();
	}
}