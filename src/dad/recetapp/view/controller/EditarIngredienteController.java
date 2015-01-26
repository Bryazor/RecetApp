package dad.recetapp.view.controller;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.IngredienteItem;
import dad.recetapp.services.items.MedidaItem;
import dad.recetapp.services.items.TipoIngredienteItem;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

public class EditarIngredienteController {
	@FXML
	private BorderPane borderpane;
	@FXML
	private TextField cantidadText;
	@FXML
	private ComboBox<String> medidaCombo;
	@FXML
	private ComboBox<String> tipoCombo;

	private IngredienteItem ingrediente=null;

	private boolean guardar=false;



	public boolean isGuardar() {
		return guardar;
	}
	public void setGuardar(boolean guardar) {
		this.guardar = guardar;
	}
	public IngredienteItem getIngrediente() {
		return ingrediente;
	}
	public void setIngrediente(IngredienteItem ingrediente2) {
		this.ingrediente = ingrediente2;
		cantidadText.setText(ingrediente.getCantidad().toString());
		medidaCombo.setValue(ingrediente.getMedida().getNombre());
		tipoCombo.setValue(ingrediente.getTipo().getNombre());
	}

	@FXML
	public void initialize(){
		try {
			medidaCombo.getItems().add("<Seleccione la medida>");
			for (MedidaItem medidaitem : ServiceLocator.getMedidasService().listarMedidas()) {
				medidaCombo.getItems().add(medidaitem.getNombre());
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		medidaCombo.setValue("<Seleccione una la medida>");

		try {
			tipoCombo.getItems().add("<Seleccione el tipo de ingrediente>");
			for (TipoIngredienteItem tipoingredienteitem : ServiceLocator.getTiposIngredientesService().listarTipoIngredientes()) {
				tipoCombo.getItems().add(tipoingredienteitem.getNombre());
			}
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tipoCombo.setValue("<Seleccione el tipo de ingrediente>");



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
	public void guardar(ActionEvent event) {
		if(cantidadText.getText().equals("")){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Crear");
			alertError.setHeaderText("Introduzca la cantidad");
			alertError.setContentText("Por favor, introduzca la cantidad");

			alertError.showAndWait();
		}else if(medidaCombo.getValue().equals("")){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Crear");
			alertError.setHeaderText("Seleccione una medida");
			alertError.setContentText("Por favor, seleccione una medida");

			alertError.showAndWait();
		}else if(tipoCombo.getValue().equals("")){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Crear");
			alertError.setHeaderText("Seleccione un ingrediente");
			alertError.setContentText("Por favor, seleccione un ingrediente");

			alertError.showAndWait();

		}else if(!tryParseInt(cantidadText.getText())){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Crear");
			alertError.setHeaderText("Introduzca la cantidad");
			alertError.setContentText("Por favor, introduzca el numero de cantidad");

			alertError.showAndWait();
		}else{
			ingrediente = new IngredienteItem();
			ingrediente.setCantidad(Integer.valueOf(cantidadText.getText()));
			try {
				for(MedidaItem medida : ServiceLocator.getMedidasService().listarMedidas()){
					if(medida.getNombre().equals(medidaCombo.getValue())){
						ingrediente.setMedida(medida);
					}
				}
			} catch (ServiceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				for(TipoIngredienteItem tipo : ServiceLocator.getTiposIngredientesService().listarTipoIngredientes()){
					if(tipo.getNombre().equals(tipoCombo.getValue())){
						ingrediente.setTipo(tipo);
					}
				}
			} catch (ServiceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			guardar=true;
			borderpane.getScene().getWindow().hide();
		}
	}
	// Event Listener on Button.onAction
	@FXML
	public void cancelar(ActionEvent event) {
		borderpane.getScene().getWindow().hide();
	}
}
