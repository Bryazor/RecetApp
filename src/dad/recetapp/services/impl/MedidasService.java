package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dad.recetapp.db.BaseDatos;
import dad.recetapp.services.IMedidasService;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.items.MedidaItem;

public class MedidasService implements IMedidasService {

	public MedidasService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void crearMedida(MedidaItem medida) throws ServiceException {
		try {			
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("insert into medidas (nombre, abreviatura) values (?, ?)");
			statment.setString(1, medida.getNombre());
			statment.setString(2, medida.getAbreviatura());
			statment.executeUpdate();
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al crear la medida '" + medida.getNombre() + "'", e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al crear la medida: La medida no puede ser nula");
		}
	}

	@Override
	public void modificarMedida(MedidaItem medida) throws ServiceException {
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("update medidas set nombre =?, abreviatura=? where id = ?");
			statment.setString(1, medida.getNombre());
			statment.setString(2, medida.getAbreviatura());
			statment.setLong(3, medida.getId());
			statment.executeUpdate();
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al modificar la medida '" + medida.getNombre() + "' ", e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al modificar la medida: La medida no puede ser nula");
		}
	}

	@Override
	public void eliminarMedida(Long id) throws ServiceException {
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("delete from medidas where id = ?");
			statment.setLong(1, id);
			statment.executeUpdate();
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al eliminar la medida con id '" + id + "' ", e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al eliminar la medida: Debe especificar el identificador");
		}
	}

	@Override
	public List<MedidaItem> listarMedidas() throws ServiceException {
		List<MedidaItem> listaMedidas = new ArrayList<MedidaItem>();
		MedidaItem medida = null;
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("select * from categorias");
			ResultSet rs = statment.executeQuery();
			while (rs.next()) {
				medida = new MedidaItem();
				medida.setId(rs.getLong("id"));
				medida.setNombre(rs.getString("nombre"));
				medida.setAbreviatura(rs.getString("abreviatura"));
				listaMedidas.add(medida);
			}
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al recuperar las medidas", e);
		}
		return listaMedidas;
	}

	@Override
	public MedidaItem obtenerMedida(Long id) throws ServiceException {
		MedidaItem medida = null;
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("select * from categorias when id = ?");
			statment.setLong(1, id);
			ResultSet rs = statment.executeQuery();
			if (rs.next()) {
				medida = new MedidaItem();
				medida.setId(rs.getLong("id"));
				medida.setNombre(rs.getString("nombre"));
				medida.setAbreviatura(rs.getString("abreviatura"));
			}			
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al recuperar la categoria con ID: '" + id + "'", e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al recuperar la medida: Debe especificar el identificador");
		}
		return medida;
	}

}
