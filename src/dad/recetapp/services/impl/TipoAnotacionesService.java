package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dad.recetapp.db.BaseDatos;
import dad.recetapp.services.ITipoAnotacionesService;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.items.TipoAnotacionItem;

public class TipoAnotacionesService implements ITipoAnotacionesService {

	public TipoAnotacionesService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void crearTipoAnotacion(TipoAnotacionItem tipo)throws ServiceException {
		try {			
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("insert into tipos_anotaciones (descripcion) values (?)");
			statment.setString(1, tipo.getDescripcion());
			statment.executeUpdate();
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al crear el tipo_anotacion '" + tipo.getDescripcion() + "' ", e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al crear el tipo_anotacion: El tipo_anotacion no puede ser nulo");
		}
	}

	@Override
	public void modificarTipoAnotacion(TipoAnotacionItem tipo) throws ServiceException {
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("update tipos_anotaciones set descripcion =? where id = ?");
			statment.setString(1, tipo.getDescripcion());
			statment.setLong(2, tipo.getId());
			statment.executeUpdate();
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al modificar el tipo_anotacion '" + tipo.getDescripcion() + "' ", e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al modificar el tipo_anotacion: El tipo_anotacion no puede ser nulo");
		}	
	}

	@Override
	public void eliminarTipoAnotacion(Long id) throws ServiceException {
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("delete from tipos_anotaciones where id = ?");
			statment.setLong(1, id);
			statment.executeUpdate();
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al eliminar el tipo_anotacion con ID '" + id + "' ", e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al eliminar el tipo_anotacion: El tipo_anotacion no puede ser nulo");
		}
	}

	@Override
	public List<TipoAnotacionItem> listarTipoAnotaciones() throws ServiceException {
		List<TipoAnotacionItem> listaTipoAnotaciones = new ArrayList<TipoAnotacionItem>();
		TipoAnotacionItem tipo = null;
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("select * from tipos_anotaciones");
			ResultSet rs = statment.executeQuery();
			while (rs.next()) {
				tipo = new TipoAnotacionItem();
				tipo.setId(rs.getLong("id"));
				tipo.setDescripcion(rs.getString("descripcion"));
				listaTipoAnotaciones.add(tipo);
			}
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al recuperar los tipos_anotaciones", e);
		}
		return listaTipoAnotaciones;
	}

	@Override
	public TipoAnotacionItem obtenerTipoAnotacion(Long id) throws ServiceException {
		TipoAnotacionItem tipo = null;
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("select * from tipos_anotaciones when id = ?");
			statment.setLong(1, id);
			ResultSet rs = statment.executeQuery();
			if (rs.next()) {
				tipo = new TipoAnotacionItem();
				tipo.setId(rs.getLong("id"));
				tipo.setDescripcion(rs.getString("descripcion"));
			}			
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al recuperar el tipo_anotacion con ID: '" + id + "'", e);
		}catch (NullPointerException e) {
			throw new ServiceException("Error al recuperar el tipo_anotacion: Debe especificar el identificador");
		}
		return tipo;
	}

}
