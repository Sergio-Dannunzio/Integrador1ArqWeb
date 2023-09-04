package dao.mysql;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBMySQLDAO extends DBDAO{

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URI = "jdbc:mysql://localhost:3306/entregable1";
    private static Connection conn;
    private static DBDAO instance = new DBMySQLDAO();
    private DBMySQLDAO(){
        DBMySQLDAO.registerDriver();
    }
    public static DBDAO getInstance(){
        return instance;
    }
    private static void registerDriver() {
        try {
            Class.forName(DRIVER).getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    public static Connection createConnection() throws SQLException {
        conn = DriverManager.getConnection(URI, "root", "");
        conn.setAutoCommit(false);
        return conn;
    }

	@Override
	public MySQLClienteDAO getClientoDAO() throws SQLException {
		return new MySQLClienteDAO();
	}
	@Override
	public MySQLFacturaDAO getFacturaDAO() throws SQLException {
		return new MySQLFacturaDAO();
	}
	@Override
	public MySQLFacturaProductoDAO getFacturaProductoDAO() throws SQLException {
		return new MySQLFacturaProductoDAO();
	}
	@Override
	public MySQLProductoDAO getProductoDAO() throws SQLException {
		return new MySQLProductoDAO();
	}

}
