package dad.recetapp.controller;

import java.util.ArrayList;
import java.util.List;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.impl.CategoriasService;
import dad.recetapp.services.items.CategoriaItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
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

	@FXML
	public void eliminar() {
		CategoriaItem item = categoriasTable.getSelectionModel().getSelectedItem();
		categoriasList.remove(item);
		try {
			ServiceLocator.getCategoriasService().eliminarCategoria(item.getId());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
