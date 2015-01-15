package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dad.recetapp.db.BaseDatos;
import dad.recetapp.services.ITipoIngredienteService;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.items.TipoAnotacionItem;
import dad.recetapp.services.items.TipoIngredienteItem;

public class TipoIngredienteService implements ITipoIngredienteService {

	public TipoIngredienteService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void crearTipoIngrediente(TipoIngredienteItem tipo) throws ServiceException {
		try {			
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("insert into tipos_ingredientes (nombre) values (?)");
			statment.setString(1, tipo.getNombre());
			statment.executeUpdate();
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al crear el tipo_ingrediente '" + tipo.getNombre() + "' ", e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al crear el tipo_anotacion: El tipo_ingrediente no puede ser nulo");
		}
	}

	@Override
	public void modificarTipoIngrediente(TipoIngredienteItem tipo) throws ServiceException {
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("update tipos_ingredientes set nombre =? where id = ?");
			statment.setString(1, tipo.getNombre());
			statment.setLong(2, tipo.getId());
			statment.executeUpdate();
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al modificar el tipo_ingrediente '" + tipo.getNombre() + "' ", e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al modificar el tipo_ingrediente: El tipo_ingrediente no puede ser nulo");
		}	
	}

	@Override
	public void eliminarTipoIngrediente(Long id) throws ServiceException {
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("delete from tipos_ingredientes where id = ?");
			statment.setLong(1, id);
			statment.executeUpdate();
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al eliminar el tipo_ingrediente con ID '" + id + "' ", e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al eliminar el tipo_ingrediente: El tipo_ingrediente no puede ser nulo");
		}
	}

	@Override
	public List<TipoIngredienteItem> listarTipoIngredientes() throws ServiceException {
		List<TipoIngredienteItem> listaTipoIngredientes = new ArrayList<TipoIngredienteItem>();
		TipoIngredienteItem tipo = null;
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("select * from tipos_ingredientes");
			ResultSet rs = statment.executeQuery();
			while (rs.next()) {
				tipo = new TipoIngredienteItem();
				tipo.setId(rs.getLong("id"));
				tipo.setNombre(rs.getString("nombre"));
				listaTipoIngredientes.add(tipo);
			}
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al recuperar los tipos_ingredientes", e);
		}
		return listaTipoIngredientes;
	}

	@Override
	public TipoIngredienteItem obtenerTipoIngrediente(Long id) throws ServiceException {
		TipoIngredienteItem tipo = null;
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("select * from tipos_ingredientes when id = ?");
			statment.setLong(1, id);
			ResultSet rs = statment.executeQuery();
			if (rs.next()) {
				tipo = new TipoIngredienteItem();
				tipo.setId(rs.getLong("id"));
				tipo.setNombre(rs.getString("nombre"));
			}			
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al recuperar el tipo_ingrediente con ID: '" + id + "'", e);
		}catch (NullPointerException e) {
			throw new ServiceException("Error al recuperar el tipo_ingrediente: Debe especificar el identificador");
		}
		return tipo;
	}

}
