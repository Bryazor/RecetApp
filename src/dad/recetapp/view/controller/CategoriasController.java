package dad.recetapp.view.controller;

import java.util.List;
import java.util.Optional;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

public class CategoriasController {

	// lista que contiene los datos
	private List<CategoriaItem> categorias;
	// lista "observable" que envuelve a la lista "variables" 
	private ObservableList<CategoriaItem> categoriasList;

	@FXML
	private TableView<CategoriaItem> categoriasTable;

	@FXML
	private TableColumn<CategoriaItem, String> descripcionColumn;

	@FXML
	private TextField descripcionText;

	@FXML
	public void initialize() {
		categoriasTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		cargarTabla();

		descripcionColumn.setCellValueFactory(new PropertyValueFactory<CategoriaItem, String>("descripcion"));
		descripcionColumn.setCellFactory(TextFieldTableCell.<CategoriaItem>forTableColumn());
		descripcionColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CategoriaItem,String>>() {
			
			@Override
			public void handle(CellEditEvent<CategoriaItem, String> t) {
				CategoriaItem editado = ((CategoriaItem) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				editado.setDescripcion(t.getNewValue());	
				try {
					ServiceLocator.getCategoriasService().modificarCategoria(editado);
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}



	private void cargarTabla() {
		try {
			categorias = ServiceLocator.getCategoriasService().listarCategorias();
			categoriasList = FXCollections.observableList(categorias);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		categoriasTable.setItems(categoriasList);
	}



	@FXML
	public void anadir() {
		// creamos un objeto "variable"
		CategoriaItem categoria = new CategoriaItem();
		if(descripcionText.getText().equals("")){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Añadir");
			alertError.setHeaderText("Descripcion vacia");
			alertError.setContentText("Por favor, introduzca una descripcion");

			alertError.showAndWait();
		}else{
			categoria.setDescripcion(descripcionText.getText());
			// si modificamos la lista "observable", se añade el item a la lista original
			// y además la tabla se entera automáticamente (porque la tabla está observando a la lista)
			try {
				ServiceLocator.getCategoriasService().crearCategoria(categoria);
				categorias.clear();
				cargarTabla();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// vaciamos los cuadros de texto
			descripcionText.clear(); // los mismo que setText("")
		}

	}

	@FXML
	public void eliminar() {
		if(categoriasTable.getSelectionModel().isEmpty()){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Eliminar");
			alertError.setHeaderText("Seleccionar Fila");
			alertError.setContentText("Por favor, seleccione un elemento a eliminar");

			alertError.showAndWait();
		}else{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Eliminar");
			alert.setHeaderText("Eliminar Categoria");
			alert.setContentText("¿ Seguro que desea eliminar los elemento(s)?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){
				try {
					for (CategoriaItem categoriaItem : categoriasTable.getSelectionModel().getSelectedItems()) {
						ServiceLocator.getCategoriasService().eliminarCategoria(categoriaItem.getId());
						categoriasList.remove(categoriaItem);
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
