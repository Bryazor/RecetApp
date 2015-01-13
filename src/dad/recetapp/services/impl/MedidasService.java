package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

			PreparedStatement statment = conn
					.prepareStatement("insert into medidas (nombre, abreviatura) values (?, ?)");
			statment.setString(1, medida.getNombre());
			statment.setString(2, medida.getAbreviatura());
			statment.executeUpdate();
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al crear la medida '"
					+ medida.getNombre(), e);
		} catch (NullPointerException e) {
			throw new ServiceException(
					"Error al crear la medida: La categoria no puede ser nula");
		}
	}

	@Override
	public void modificarMedida(MedidaItem medida) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminarMedida(Long id) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<MedidaItem> listarMedidas() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MedidaItem obtenerMedida(Long id) throws ServiceException {
		MedidaItem medida = null;
		try {
			if (id == null){
				throw new ServiceException("Error al recuperar la medida: Debe especificar el identificador");
			}

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
		}
		return medida;
	}

}
