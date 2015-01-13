package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import dad.recetapp.db.BaseDatos;
import dad.recetapp.services.ICategoriaServices;
import dad.recetapp.services.ServiceException;
import dad.recetapp.services.items.CategoriaItem;

public class CategoriasService implements ICategoriaServices {

	public CategoriasService() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void crearCategoria(CategoriaItem categoria) throws ServiceException {
		try {
			
			Connection conn = BaseDatos.getConnection();

			PreparedStatement statment = conn
					.prepareStatement("insert into categorias (descripcion) values (?)");
			statment.setString(1, categoria.getDescripcion());
			statment.executeUpdate();
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al crear la categoria '"
					+ categoria.getDescripcion(), e);
		} catch (NullPointerException e) {
			throw new ServiceException(
					"Error al crear la categoria: La categoria no puede ser nula");
		}
	}

	@Override
	public void modificarCategoria(CategoriaItem categoria)
			throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminarCategoria(Long id) throws ServiceException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<CategoriaItem> listarCategorias() throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CategoriaItem obtenerCategoria(Long id) throws ServiceException {
		CategoriaItem categoria = null;
		try {
			if (id == null){
				throw new ServiceException("Error al recuperar la categoria: Debe especificar el identificador");
			}

			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("select * from categorias when id = ?");
			statment.setLong(1, id);
			ResultSet rs = statment.executeQuery();
			if (rs.next()) {
				categoria = new CategoriaItem();
				categoria.setId(rs.getLong("id"));
				categoria.setDescripcion(rs.getString("descripcion"));
			}			
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al recuperar la categoria con ID: '" + id + "'", e);
		}
		return categoria;
	}

}
