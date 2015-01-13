package dad.recetapp.services;

import java.util.List;

import dad.recetapp.services.items.TipoIngredienteItem;

public interface ITipoIngredienteService {
	
	public void crearTipoIngrediente(TipoIngredienteItem tipo) throws ServiceException;
	public void modificarTipoIngrediente(TipoIngredienteItem tipo) throws ServiceException;
	public void eliminarTipoIngrediente(Long id) throws ServiceException;
	public List<TipoIngredienteItem> listarTipoIngredientes() throws ServiceException;
	public TipoIngredienteItem obtenerTipoIngrediente(Long id) throws ServiceException;

}
