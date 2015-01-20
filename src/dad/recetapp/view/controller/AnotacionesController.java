package dad.recetapp.view.controller;

import java.util.List;
import java.util.Optional;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}



	private void cargarTabla() {
		try {
			anotaciones = ServiceLocator.getTiposAnotacionesService().listarTipoAnotaciones();
			anotacionesList = FXCollections.observableList(anotaciones);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		anotacionesTable.setItems(anotacionesList);
	}



	@FXML
	public void anadir() {
		// creamos un objeto "variable"
		TipoAnotacionItem anotacion = new TipoAnotacionItem();
		anotacion.setDescripcion(descripcionText.getText());
		// si modificamos la lista "observable", se añade el item a la lista original
		// y además la tabla se entera automáticamente (porque la tabla está observando a la lista)
		try {
			ServiceLocator.getTiposAnotacionesService().crearTipoAnotacion(anotacion);
			anotaciones.clear();
			cargarTabla();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// vaciamos los cuadros de texto
		descripcionText.clear(); // los mismo que setText("")
	}

	@FXML
	public void eliminar() {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Eliminar");
		alert.setHeaderText("Eliminar Anotacion");
		alert.setContentText("¿ Seguro que desea eliminar los elemento(s)?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			try {
				for (TipoAnotacionItem categoriaItem : anotacionesTable.getSelectionModel().getSelectedItems()) {
					ServiceLocator.getCategoriasService().eliminarCategoria(categoriaItem.getId());
					anotacionesList.remove(categoriaItem);
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