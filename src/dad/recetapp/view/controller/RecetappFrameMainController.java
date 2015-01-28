package dad.recetapp.view.controller;

import java.io.IOException;

import dad.recetapp.MainApp;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;

public class RecetappFrameMainController {

	@FXML
	private  Label recetalabel;
	@FXML
	private static BorderPane mainframe;
	@FXML
	private Tab recetasTab;

	public  Label getRecetalabel() {
		return recetalabel;
	}

	public void setRecetalabel(Label recetalabel) {
		this.recetalabel = recetalabel;
	}

	public static BorderPane getMainframe(){
		return mainframe;
	}
	@FXML
	public void initialize() {
		numeroRecetas();
		
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/dad/recetapp/view/RecetasView.fxml"));
			BorderPane value;
			value = (BorderPane) loader.load();
			((RecetasController)loader.getController()).setControlladorMain(this);
			recetasTab.setContent(value);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void numeroRecetas() {
		try {
			int cantidad = ServiceLocator.getRecetasService().listarRecetas().size();
			recetalabel.setText("Recetas: "+cantidad);
		} catch (ServiceException e) {
			error(e.getMessage());
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
