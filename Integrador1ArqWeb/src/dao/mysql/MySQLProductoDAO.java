package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import dao.ProductoDAO;
import entities.Producto;

public class MySQLProductoDAO implements ProductoDAO{
	private Connection conn;
	
	public MySQLProductoDAO() throws SQLException {
        this.createTable();
    }
	
	@Override
	public void insertar(Producto p) throws SQLException {
        String insert = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
        PreparedStatement ps = this.conn.prepareStatement(insert);
        ps.setInt(1, p.getIdProduct());
        ps.setString(2, p.getName());
        ps.setFloat(3, p.getValue());
        ps.executeUpdate();
        this.conn.commit();
        ps.close();
	}

	@Override
	public void loadData(CSVParser parser) throws SQLException {
        this.conn = DBMySQLDAO.createConnection();
        for(CSVRecord row: parser) {
            Producto p  = new Producto(Integer.parseInt(row.get("idProducto")),row.get("nombre"),Float.parseFloat(row.get("valor")));
            insertar(p);
        }
        this.conn.close();
	}

	@Override
	public void createTable() throws SQLException {
        this.conn = DBMySQLDAO.createConnection();
        String tablaProducto = "CREATE TABLE IF NOT EXISTS Producto("
                + "idProducto INT,"
                + "nombre VARCHAR(45),"
                + "valor FLOAT,"
                + "PRIMARY KEY(idProducto))";

        this.conn.prepareStatement(tablaProducto).execute();
        this.conn.commit();
        this.conn.close();
	}
	
    public Producto productoMasRecaudado() throws SQLException{

        Producto producto = null;
        this.conn = DBMySQLDAO.createConnection();

        String select = "SELECT p.*, SUM(p.valor * fp.cantidad) as total "
                + "FROM producto p JOIN factura_producto fp ON (p.idProducto = fp.idProducto) "
                + "WHERE p.idProducto = fp.idProducto "
                + "GROUP BY idProducto "
                + "ORDER BY `total` DESC "
                + "LIMIT 1";
        PreparedStatement ps = this.conn.prepareStatement(select);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            producto = new Producto(rs.getInt(1), rs.getString(2), rs.getFloat(3));
        }
        this.conn.commit();
        ps.close();
        this.conn.close();

        return producto;
    }
    
}
