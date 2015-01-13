package dad.recetapp.services;

import dad.recetapp.services.impl.CategoriasService;

public class ServiceLocator {
	
	private static final ICategoriaServices categoriaService = new CategoriasService();

	public ServiceLocator() {}
	
	public static ICategoriaServices getCategoriasService(){
		return categoriaService;
	}

}
