package dad.recetapp.services.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

			PreparedStatement statment = conn.prepareStatement("insert into categorias (descripcion) values (?)");
			statment.setString(1, categoria.getDescripcion());
			statment.executeUpdate();
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al crear la categoria '" + categoria.getDescripcion() + "' ", e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al crear la categoria: La categoria no puede ser nula");
		}
	}

	@Override
	public void modificarCategoria(CategoriaItem categoria) throws ServiceException {
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("update categorias set descripcion =? where id = ?");
			statment.setString(1, categoria.getDescripcion());
			statment.setLong(2, categoria.getId());
			statment.executeUpdate();
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al modificar la categoria '"
					+ categoria.getDescripcion() + "' ", e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al modificar la categoria: La categoria no puede ser nula");
		}	
	}

	@Override
	public void eliminarCategoria(Long id) throws ServiceException {
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("delete from categorias where id = ?");
			statment.setLong(1, id);
			statment.executeUpdate();
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al eliminar la categoria con ID '" + id + "' ", e);
		} catch (NullPointerException e) {
			throw new ServiceException("Error al eliminar la categoria: Debe especificar el identificador");
		}
	}

	@Override
	public List<CategoriaItem> listarCategorias() throws ServiceException {
		List<CategoriaItem> listaCategorias = new ArrayList<CategoriaItem>();
		CategoriaItem categoria = null;
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("select * from categorias");
			ResultSet rs = statment.executeQuery();
			while (rs.next()) {
				categoria = new CategoriaItem();
				categoria.setId(rs.getLong("id"));
				categoria.setDescripcion(rs.getString("descripcion"));
				listaCategorias.add(categoria);
			}
			statment.close();
		} catch (SQLException e) {
			throw new ServiceException("Error al recuperar las categorias", e);
		}
		return listaCategorias;
	}

	@Override
	public CategoriaItem obtenerCategoria(Long id) throws ServiceException {
		CategoriaItem categoria = null;
		try {
			Connection conn = BaseDatos.getConnection();
			PreparedStatement statment = conn.prepareStatement("select * from categorias where id = ?");
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
		}catch (NullPointerException e) {
			throw new ServiceException("Error al recuperar la categoria: Debe especificar el identificador");
		}
		return categoria;
	}

}
