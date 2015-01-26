package dad.recetapp.view.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.border.TitledBorder;

import javafx.beans.InvalidationListener;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;
import dad.recetapp.services.items.InstruccionItem;
import dad.recetapp.services.items.RecetaItem;
import dad.recetapp.services.items.RecetaListItem;
import dad.recetapp.services.items.TipoAnotacionItem;
import dad.recetapp.view.MainApp;

public class RecetasController {
	@FXML
	public Parent mainframe;
	public static Stage ventana;
	// lista que contiene los datos
	private List<RecetaListItem> recetas;
	// lista "observable" que envuelve a la lista "variables" 
	private ObservableList<RecetaListItem> recetasList;
	private ObservableList<RecetaListItem> filrecetasList = FXCollections.observableArrayList();

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
	private GridPane nortePane;
	@FXML
	private TextField nombreText;
	@FXML
	private ComboBox<String> minutosCombo;
	@FXML
	private ComboBox<String> segundosCombo;
	@FXML
	private ComboBox<String> categoriaCombo;


	@FXML
	public void initialize() {
		try {
			categoriaCombo.getItems().add("<Todas>");
			for (CategoriaItem categoriaItem : ServiceLocator.getCategoriasService().listarCategorias()) {
				categoriaCombo.getItems().add(categoriaItem.getDescripcion());
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		categoriaCombo.setValue("<Todas>");
		for (int j = 0; j < 61; j++) {
			segundosCombo.getItems().add(String.valueOf(j));
		}
		segundosCombo.setValue("0");
		for (int j = 0; j < 301; j++) {
			minutosCombo.getItems().add(String.valueOf(j));
		}
		minutosCombo.setValue("0");

		recetasTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		cargarTabla();

		// Listen for text changes in the filter text field
		nombreText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {

				updateFilteredData();
			}
		});

		categoriaCombo.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateFilteredData();
			}
		});

		minutosCombo.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateFilteredData();
			}
		});

		segundosCombo.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				updateFilteredData();
			}
		});

		nombreColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("nombre"));
		nombreColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());


		paraColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RecetaListItem,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(
					CellDataFeatures<RecetaListItem, String> param) {
				ObservableValue<String> a = new ObservableValue<String>() {


					@Override
					public String getValue() {
						// TODO Auto-generated method stub
						return ((RecetaListItem) param.getValue()).getCantidad()+" "+((RecetaListItem) param.getValue()).getPara();
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
		paraColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());

		tiempoColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RecetaListItem,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(
					CellDataFeatures<RecetaListItem, String> param) {
				ObservableValue<String> a = new ObservableValue<String>() {


					@Override
					public String getValue() {
						// TODO Auto-generated method stub
						String resul;
						int minutos =((RecetaListItem) param.getValue()).getTiempoTotal()/60;
						int segundos =((RecetaListItem) param.getValue()).getTiempoTotal()%60;
						if(segundos == 0){
							resul =  minutos+"M";
						}else{
							resul = minutos+"M "+segundos+"S";
						}
						return resul;
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
		tiempoColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());

		fechaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RecetaListItem,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(
					CellDataFeatures<RecetaListItem, String> param) {
				ObservableValue<String> a = new ObservableValue<String>() {


					@Override
					public String getValue() {
						// TODO Auto-generated method stu
						Calendar a = new GregorianCalendar();
						a = GregorianCalendar.getInstance();
						a.setTime(((RecetaListItem) param.getValue()).getFechaCreacion());
						return a.get(Calendar.DAY_OF_MONTH)+"/"+a.get(Calendar.MONTH)+"/"+a.get(Calendar.YEAR)+" "+a.get(Calendar.HOUR)+":"+a.get(Calendar.MINUTE);
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
		fechaColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());

		categoriaColumn.setCellValueFactory(new PropertyValueFactory<RecetaListItem, String>("categoria"));
		categoriaColumn.setCellFactory(TextFieldTableCell.<RecetaListItem>forTableColumn());


	}



	private void cargarTabla() {
		try {
			recetas = ServiceLocator.getRecetasService().listarRecetas();
			recetasList = FXCollections.observableList(recetas);

			filrecetasList.addAll(recetasList);

			// Listen for changes in master data.
			// Whenever the master data changes we must also update the filtered data.
			recetasList.addListener(new ListChangeListener<RecetaListItem>() {
				@Override
				public void onChanged(ListChangeListener.Change<? extends RecetaListItem> change) {
					updateFilteredData();
				}
			});

		} catch (ServiceException e) {
			e.printStackTrace();
		}

		recetasTable.setItems(filrecetasList);


	}

	/**
	 * Updates the filteredData to contain all data from the masterData that
	 * matches the current filter.
	 */
	private void updateFilteredData() {
		filrecetasList.clear();

		for (RecetaListItem p : recetasList) {
			if (matchesFilter(p)) {
				filrecetasList.add(p);
			}
		}

		// Must re-sort table after items changed
		reapplyTableSortOrder();
	}

	/**
	 * Returns true if the person matches the current filter. Lower/Upper case
	 * is ignored.
	 * 
	 * @param receta
	 * @return
	 */
	private boolean matchesFilter(RecetaListItem receta) {
		String filterString = nombreText.getText();
		if ((filterString == null || filterString.isEmpty()) &&(categoriaCombo.getValue().equals("<Todas>")) && (minutosCombo.getValue().equals("0")) &&(
				segundosCombo.getValue().equals("0"))) {
			// No filter --> Add all.
			return true;
		}

		String lowerCaseFilterString = filterString.toLowerCase();
		if(!(filterString == null || filterString.isEmpty())){
			if (receta.getNombre().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
				return true;
			} 
		}
		if(!(categoriaCombo.getValue().equals("<Todas>"))){
			if (receta.getCategoria().equals(categoriaCombo.getValue())) {
				return true;
			} 
		}
		if(!(minutosCombo.getValue().equals("0"))){
			if(!(segundosCombo.getValue().equals("0"))){
				if(receta.getTiempoTotal() < ((Integer.valueOf(minutosCombo.getValue())*60)+Integer.valueOf(segundosCombo.getValue()))){
					return true;
					
				}
			}else{
				System.out.println("dentro else");

				if(receta.getTiempoTotal()<= ((Integer.valueOf(minutosCombo.getValue())*60))){

					return true;
				}
			}
		}
		if(!(segundosCombo.getValue().equals("0"))){
			if(receta.getTiempoTotal() <= (Integer.valueOf(segundosCombo.getValue()))){
				return true;
			}
		}


		return false; // Does not match
	}

	private void reapplyTableSortOrder() {
		ArrayList<TableColumn<RecetaListItem, ?>> sortOrder = new ArrayList<>(recetasTable.getSortOrder());
		recetasTable.getSortOrder().clear();
		recetasTable.getSortOrder().addAll(sortOrder);
	}

	@FXML
	public void anadir() {

		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("NuevaRecetaView.fxml"));
			BorderPane ventanaDos;
			ventanaDos = (BorderPane) loader.load();
			ventana = new Stage();
			ventana.setTitle("Nueva Receta");
			Scene scene = new Scene(ventanaDos);
			ventana.setScene(scene);

			ventana.initOwner(MainApp.primaryStage);
			ventana.initModality(Modality.WINDOW_MODAL);
			ventana.showAndWait();
			if(!(((NuevaRecetaController)loader.getController()).getReceta()==null)){
				RecetaItem recetaitem = ((NuevaRecetaController)loader.getController()).getReceta();
				try {
					ServiceLocator.getRecetasService().crearReceta(recetaitem);
					recetas.clear();
					filrecetasList.clear();
					cargarTabla();
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}




	}

	@FXML
	public void editar() {

		if(recetasTable.getSelectionModel().isEmpty()){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Editar");
			alertError.setHeaderText("Seleccionar Fila");
			alertError.setContentText("Por favor, seleccione una receta a editar");

			alertError.showAndWait();
		}else{
			try {
				FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("EditarRecetaView.fxml"));
				BorderPane ventanaDos;
				ventanaDos = (BorderPane) loader.load();
				try {
					((EditarRecetaController) loader.getController()).setReceta(ServiceLocator.getRecetasService().obtenerReceta(
							recetasTable.getSelectionModel().getSelectedItem().getId()));
				} catch (ServiceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				ventana = new Stage();
				ventana.setTitle("Editar Receta");
				Scene scene = new Scene(ventanaDos);
				ventana.setScene(scene);
				ventana.initOwner(MainApp.primaryStage);
				ventana.initModality(Modality.WINDOW_MODAL);
				ventana.showAndWait();
				if((((EditarRecetaController)loader.getController()).isGuardar())){
					RecetaItem recetaitem = ((EditarRecetaController)loader.getController()).getReceta();
					try {
						ServiceLocator.getRecetasService().modificarReceta(recetaitem);
						recetas.clear();
						filrecetasList.clear();
						cargarTabla();
					} catch (ServiceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}





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
			alert.setContentText("� Seguro que desea eliminar los elemento(s)?");

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
