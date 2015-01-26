package dad.recetapp.view.controller;

import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class RecetappFrameMainController {
	@FXML
	private Label recetalabel;
	@FXML
	private BorderPane mainframe;
	@FXML
	public void initialize() {
		numeroRecetas();
	}

	public void numeroRecetas() {
		try {
			int cantidad = ServiceLocator.getRecetasService().listarRecetas().size();
			recetalabel.setText("Recetas: "+cantidad);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
