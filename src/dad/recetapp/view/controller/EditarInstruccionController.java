package dad.recetapp.view.controller;

import dad.recetapp.services.items.InstruccionItem;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

public class EditarInstruccionController {
	@FXML
	private BorderPane borderpane;
	@FXML
	private TextField ordenText;
	@FXML
	private TextArea descripcionText;
	
	private InstruccionItem instruccion = null;
	
	

	public InstruccionItem getInstruccion() {
		return instruccion;
	}
	public void setInstruccion(InstruccionItem instruccion2) {
		this.instruccion = instruccion2;
		ordenText.setText(instruccion.getOrden().toString());
		descripcionText.setText(instruccion.getDescripcion());
		
	}
	// Event Listener on Button.onAction
	@FXML
	public void guardar(ActionEvent event) {
		if(ordenText.getText().equals("")){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Guardar");
			alertError.setHeaderText("Introduzca el numero de orden");
			alertError.setContentText("Por favor, introduzca el numero de orden");

			alertError.showAndWait();
		}else if(descripcionText.getText().equals("")){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Guardar");
			alertError.setHeaderText("Introduzca una descripcion");
			alertError.setContentText("Por favor, introduzca una descripcion");

			alertError.showAndWait();
		}else if(!tryParseInt(ordenText.getText())){
			Alert alertError = new Alert(AlertType.ERROR);
			alertError.setTitle("Error Guardar");
			alertError.setHeaderText("Introduzca el numero de orden");
			alertError.setContentText("Por favor, introduzca el numero de orden correcto");

			alertError.showAndWait();
		}else{
			instruccion = new InstruccionItem();
			instruccion.setOrden(Integer.valueOf(ordenText.getText()));
			instruccion.setDescripcion(descripcionText.getText());
			
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
}
