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
import dad.recetapp.services.items.MedidaItem;
import dad.recetapp.services.items.MedidaItem;

public class MedidasController {
	// lista que contiene los datos
	private List<MedidaItem> medidas;
	// lista "observable" que envuelve a la lista "variables" 
	private ObservableList<MedidaItem> medidasList;

	@FXML
	private TableView<MedidaItem> medidasTable;

	@FXML
	private TableColumn<MedidaItem, String> nombreColumn;
	@FXML
	private TableColumn<MedidaItem, String> abreviaturaColumn;

	@FXML
	private TextField nombreText;
	@FXML
	private TextField abreviaturaText;

	@FXML
	public void initialize() {
		System.out.println("cargando");
		medidasTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		cargarTabla();

		nombreColumn.setCellValueFactory(new PropertyValueFactory<MedidaItem, String>("nombre"));
		nombreColumn.setCellFactory(TextFieldTableCell.<MedidaItem>forTableColumn());
		nombreColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MedidaItem,String>>() {

			@Override
			public void handle(CellEditEvent<MedidaItem, String> t) {
				MedidaItem editado = ((MedidaItem) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				editado.setNombre(t.getNewValue());	
				try {
					ServiceLocator.getMedidasService().modificarMedida(editado);
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		abreviaturaColumn.setCellValueFactory(new PropertyValueFactory<MedidaItem, String>("abreviatura"));
		abreviaturaColumn.setCellFactory(TextFieldTableCell.<MedidaItem>forTableColumn());
		abreviaturaColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MedidaItem,String>>() {

			@Override
			public void handle(CellEditEvent<MedidaItem, String> t) {
				MedidaItem editado = ((MedidaItem) t.getTableView().getItems().get(t.getTablePosition().getRow()));
				editado.setAbreviatura(t.getNewValue());	
				try {
					ServiceLocator.getMedidasService().modificarMedida(editado);
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}



	private void cargarTabla() {
		try {
			medidas = ServiceLocator.getMedidasService().listarMedidas();
			medidasList = FXCollections.observableList(medidas);
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		medidasTable.setItems(medidasList);
	}



	@FXML
	public void anadir() {
		// creamos un objeto "variable"
		MedidaItem medida = new MedidaItem();
		if(nombreText.getText().equals("")){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Añadir");
			alertError.setHeaderText("Datos vacio");
			alertError.setContentText("Por favor, introduzca los dos valores");

			alertError.showAndWait();
		}else{
			medida.setNombre(nombreText.getText());
			medida.setAbreviatura(abreviaturaText.getText());
			// si modificamos la lista "observable", se añade el item a la lista original
			// y además la tabla se entera automáticamente (porque la tabla está observando a la lista)
			try {
				ServiceLocator.getMedidasService().crearMedida(medida);
				medidas.clear();
				cargarTabla();
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// vaciamos los cuadros de texto
			nombreText.clear(); // los mismo que setText("")
			abreviaturaText.clear();
		}

	}

	@FXML
	public void eliminar() {
		if(medidasTable.getSelectionModel().isEmpty()){
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
					for (MedidaItem medidaitem : medidasTable.getSelectionModel().getSelectedItems()) {
						ServiceLocator.getMedidasService().eliminarMedida(medidaitem.getId());
						medidasList.remove(medidaitem);
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
