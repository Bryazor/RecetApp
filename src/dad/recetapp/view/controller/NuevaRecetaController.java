package dad.recetapp.view.controller;

import java.util.Calendar;
import java.util.List;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;
import dad.recetapp.services.items.RecetaItem;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

public class NuevaRecetaController {
	@FXML 
	private BorderPane borderpane;
	@FXML
	private TextField nombreText;
	@FXML
	private ComboBox<String> categoriaCombo;
	@FXML
	private TextField paraText;
	@FXML
	private ComboBox<String> paraCombo;
	@FXML
	private ComboBox<String> minutosTotalCombo;
	@FXML
	private ComboBox<String> segundosTotalCombo;
	@FXML
	private ComboBox<String> minutosThermoCombo;
	@FXML
	private ComboBox<String> segundosThermoCombo;
	@FXML
	private Tab seccionTab;
	@FXML
	private Tab nuevaTab;
	@FXML
	private TabPane tabPane;

	RecetaItem receta=null;

	public RecetaItem getReceta() {
		return receta;
	}

	public void setReceta(RecetaItem receta) {
		this.receta = receta;
	}

	@FXML
	public void initialize() {
		try {
			categoriaCombo.getItems().add("<Seleccione una Categoria>");
			for (CategoriaItem categoriaItem : ServiceLocator.getCategoriasService().listarCategorias()) {
				categoriaCombo.getItems().add(categoriaItem.getDescripcion());
			}
		} catch (ServiceException e) {
			error(e.getMessage());
		}

		categoriaCombo.setValue("<Seleccione una Categoria>");
		paraText.setText("0");
		paraCombo.getItems().add("Personas");
		paraCombo.getItems().add("Unidades");
		paraCombo.setValue("Personas");
		for (int j = 0; j < 61; j++) {
			segundosTotalCombo.getItems().add(String.valueOf(j));
		}
		segundosTotalCombo.setValue("0");
		for (int j = 0; j < 301; j++) {
			minutosTotalCombo.getItems().add(String.valueOf(j));
		}
		minutosTotalCombo.setValue("0");

		for (int j = 0; j < 61; j++) {
			segundosThermoCombo.getItems().add(String.valueOf(j));
		}
		segundosThermoCombo.setValue("0");
		for (int j = 0; j < 301; j++) {
			minutosThermoCombo.getItems().add(String.valueOf(j));
		}
		minutosThermoCombo.setValue("0");

		nuevaTab.setClosable(false);

		
		
		ComponenteRecetas comp = new ComponenteRecetas();

		comp.setTab(seccionTab);
		seccionTab.setContent(comp);


		nuevaTab.onSelectionChangedProperty().set(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				Tab nuevo =new Tab("Sin titulo");

				ComponenteRecetas comp = new ComponenteRecetas();

				comp.setTab(nuevo);
				nuevo.setContent(comp);

				tabPane.getTabs().add(tabPane.getTabs().size()-1,nuevo);
				tabPane.getSelectionModel().select(nuevo);

			}
		});


	}

	// Event Listener on Button.onAction
	@FXML
	public void crear(ActionEvent event) throws ServiceException {
		if(nombreText.getText().equals("")){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Crear");
			alertError.setHeaderText("Introduzca un Nombre");
			alertError.setContentText("Por favor, introduzca un Nombre");

			alertError.showAndWait();
		}else if(categoriaCombo.getSelectionModel().getSelectedItem().equals("<Seleccione una Categoria>")){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Crear");
			alertError.setHeaderText("Seleccione una Categoria");
			alertError.setContentText("Por favor, seleccione una Categoria");

			alertError.showAndWait();
		}else if((!tryParseInt(paraText.getText()))){

			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Crear");
			alertError.setHeaderText("Introduzca el numero de comensales");
			alertError.setContentText("Por favor, introduzca el numero de comensales");

			alertError.showAndWait();
		}else{
			receta = new RecetaItem();
			receta.setNombre(nombreText.getText());
			CategoriaItem categoria = new CategoriaItem();
			List<CategoriaItem> categorias =ServiceLocator.getCategoriasService().listarCategorias();
			for (CategoriaItem categoriaItem : categorias) {
				if(categoriaItem.getDescripcion().equals(categoriaCombo.getSelectionModel().getSelectedItem())){
					categoria = categoriaItem;
				}
			}
			receta.setCategoria(categoria);
			receta.setCantidad(Integer.valueOf(paraText.getText()));
			int tiempoTotal = (Integer.valueOf(minutosTotalCombo.getSelectionModel().getSelectedItem())*60)+Integer.valueOf(segundosTotalCombo.getSelectionModel().getSelectedItem());
			int tiempoThermo = (Integer.valueOf(minutosThermoCombo.getSelectionModel().getSelectedItem())*60)+Integer.valueOf(segundosThermoCombo.getSelectionModel().getSelectedItem());
			receta.setTiempoTotal(tiempoTotal);
			receta.setTiempoThermomix(tiempoThermo);
			Calendar fecha;
			fecha = Calendar.getInstance();
			receta.setFechaCreacion(fecha.getTime());
			receta.setPara(paraCombo.getSelectionModel().getSelectedItem());


			receta.getSecciones().clear();
			for (int i = 0; i < tabPane.getTabs().size()-1; i++) {
				if(!((ComponenteRecetas)tabPane.getTabs().get(i).getContent()).getSeccion().getNombre().equals("")){
					receta.getSecciones().add(((ComponenteRecetas)tabPane.getTabs().get(i).getContent()).getSeccion());
				}

			}

			borderpane.getScene().getWindow().hide();
		}
	}

	boolean tryParseInt(String value)  
	{  
		try  
		{  
			Integer.parseInt(value);  
			return true;  
		} catch(NumberFormatException nfe)  
		{  
			return false;  
		}  
	}
	// Event Listener on Button.onAction
	@FXML
	public void cancelar(ActionEvent event) {

		borderpane.getScene().getWindow().hide();
	}

	public void error(String mensaje){
		Alert alertError = new Alert(AlertType.ERROR);
		alertError.setTitle("Error");
		alertError.setHeaderText("Error ");
		alertError.setContentText("Se ha producido un error: "+ mensaje);

		alertError.showAndWait();
	}

}
