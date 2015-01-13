package dad.recetapp.services;

import java.util.List;

import dad.recetapp.services.items.TipoAnotacionItem;

public interface ITipoAnotacionesService {
	
	public void crearTipoAnotacion(TipoAnotacionItem tipo) throws ServiceException;
	public void modificarTipoAnotacion(TipoAnotacionItem tipo) throws ServiceException;
	public void eliminarTipoAnotacion(Long id) throws ServiceException;
	public List<TipoAnotacionItem> listarTipoAnotaciones() throws ServiceException;
	public TipoAnotacionItem obtenerTipoAnotacion(Long id) throws ServiceException;

}
