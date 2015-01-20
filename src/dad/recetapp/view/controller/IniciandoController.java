package dad.recetapp.view.controller;

import dad.recetapp.view.MainApp;
import javafx.fxml.FXML;
import javafx.util.Duration;

public class IniciandoController {

	@FXML
	public void cerrar(){
		Duration du = new Duration(4000);
		MainApp.timeline.jumpTo(du);
	}
}
