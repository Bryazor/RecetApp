package dad.recetapp.view.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.RecetaListItem;

public class RecetasController {
	// lista que contiene los datos
		private List<RecetaListItem> recetas;
		// lista "observable" que envuelve a la lista "variables" 
		private ObservableList<RecetaListItem> recetasList;

		@FXML
		private TableView<RecetaListItem> recetasTable;

		@FXML
		private TableColumn<RecetaListItem, String> nombreColumn;
		@FXML
		private TableColumn<RecetaListItem, String> paraColumn;
		@FXML
		private TableColumn<RecetaListItem, String> tiempoColumn;
		@FXML
		private TableColumn<RecetaListItem, String> fechaColumn;
		@FXML
		private TableColumn<RecetaListItem, String> categoriaColumn;

		@FXML
		private TextField nombreText;
		@FXML
		private ComboBox<String> minutosCombo;
		@FXML
		private ComboBox<String> segundosCombo;
		@FXML
		private ComboBox<String> categoriaCombo;
		

//		@FXML
//		public void initialize() {
//			System.out.println("cargando");
//			recetasTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
//			cargarTabla();
//
//			nombreColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("nombre"));
//			nombreColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
//
//			
//			paraColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("cantidad"));
//			paraColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
//			
//			tiempoColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("tiempototal"));
//			tiempoColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
//			
//			fechaColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("fechacreacion"));
//			fechaColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
//			
//			categoriaColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("categoria"));
//			categoriaColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());
//
//
//		}
//
//
//
//		private void cargarTabla() {
//			try {
//				recetas = ServiceLocator.getRecetasService().listarRecetas();
//				recetasList = FXCollections.observableList(recetas);
//			} catch (ServiceException e) {
//				e.printStackTrace();
//			}
//
//			recetasTable.setItems(recetasList);
//		}



		@FXML
		public void anadir() {

		}

		@FXML
		public void editar() {

		}
		
		@FXML
		public void eliminar() {
			if(recetasTable.getSelectionModel().isEmpty()){
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
						for (RecetaListItem recetalistitem : recetasTable.getSelectionModel().getSelectedItems()) {
							ServiceLocator.getRecetasService().eliminarReceta(recetalistitem.getId());
							recetasList.remove(recetalistitem);
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
