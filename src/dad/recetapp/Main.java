package dad.recetapp;

import javax.swing.JOptionPane;

import dad.recetapp.db.BaseDatos;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.ServiceLocator;
import dad.recetapp.services.items.CategoriaItem;

public class Main {
	public static void main(String[] args) {
		System.out.println("Prueba de conexion: " + BaseDatos.test());
		
		
		try {
			CategoriaItem categoria = new CategoriaItem();
			categoria.setDescripcion("Pescado");
			ServiceLocator.getCategoriasService().crearCategoria(categoria);
			
		} catch (ServiceException e) {
			String mensajeError = e.getMessage() + "\n\nDetalles: " + e.getCause().getMessage();
			JOptionPane.showMessageDialog(null, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
