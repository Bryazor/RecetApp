package dad.recetapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BaseDatos {
	
	private static final ResourceBundle CONFIG = ResourceBundle.getBundle(BaseDatos.class.getPackage().getName() + ".database");
	
	private static Connection connection = null;
	
	public BaseDatos() {
		
	}
	
	public static Connection getConnection() throws SQLException{
		if (connection == null || connection.isClosed()){
			connection = connect();
		}
		return connection;
	}
	
	private static final void registerDriver(){
		try {
			Class.forName(CONFIG.getString("db.driver.classname"));
		} catch (ClassNotFoundException e) {
			System.err.println("Error al cargar el driver JDBC");
		}
	}

	public static Connection connect() throws SQLException {		
		return connect(CONFIG.getString("db.url"),CONFIG.getString("db.username"),CONFIG.getString("db.password"));
	}
	
	public static Connection connect(String url, String username, String password) throws SQLException {	
		registerDriver();
		return DriverManager.getConnection(url,username,password);
	}

	public static void disconnect() throws SQLException{
		disconnect(connection);
		connection = null;
	}

	public static void disconnect(Connection connection) throws SQLException {
		if (connection != null && !connection.isClosed()){
			connection.close();
		}		
	}
	
	public static Boolean test(){
		boolean testOK = false;
		try {
			Connection c = BaseDatos.connect();
			BaseDatos.disconnect(c);
			testOK = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return testOK;	
	}
	
}
