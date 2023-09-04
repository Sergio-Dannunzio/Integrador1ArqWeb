package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import dao.ClienteDAO;
import entities.Cliente;


public class MySQLClienteDAO implements ClienteDAO{
	private Connection conn;
	
	public MySQLClienteDAO() throws SQLException{
		this.createTable();
	}
	
	@Override
	public void loadData(CSVParser parser) throws SQLException {
		this.conn = DBMySQLDAO.createConnection();
		for(CSVRecord row: parser) {
			Cliente c = new Cliente(Integer.parseInt(row.get("idCliente")),row.get("nombre"),row.get("email"));
			insertar(c);
		}
		this.conn.close();
		
	}

	@Override
	public void insertar(Cliente c) throws SQLException{
        String insert = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
        PreparedStatement ps = this.conn.prepareStatement(insert);
        ps.setInt(1, c.getIdCliente());
        ps.setString(2, c.getNombre());
        ps.setString(3, c.getEmail());
        ps.executeUpdate();
        this.conn.commit();
        ps.close();
	}


	@Override
	public void createTable() throws SQLException {
        this.conn = DBMySQLDAO.createConnection();
        String tablaCliente = "CREATE TABLE IF NOT EXISTS Cliente("
                + "idCliente INT,"
                + "nombre VARCHAR(500),"
                + "email VARCHAR(150),"
                + "PRIMARY KEY(idCliente))";

        this.conn.prepareStatement(tablaCliente).execute();
        this.conn.commit();
        this.conn.close();	
	}

    public ArrayList<Cliente> masFacturados() throws SQLException{
        ArrayList<Cliente> clientes = new ArrayList<>();
        this.conn = DBMySQLDAO.createConnection();

        String select = "SELECT c.*, SUM(p.valor * fp.cantidad) as total "
                + "FROM cliente c JOIN factura f ON (c.idCliente = f.idCliente_FK) JOIN factura_producto fp ON(f.idFactura = fp.idFactura) JOIN producto p ON (p.idProducto = fp.idProducto) "
                + "WHERE c.idCliente = f.idCliente_FK "
                + "GROUP BY c.idCliente "
                + "ORDER BY `total` DESC";
        PreparedStatement ps = this.conn.prepareStatement(select);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            Cliente c = new Cliente(rs.getInt(1), rs.getString(2), rs.getString(3));
            clientes.add(c);
        }
        this.conn.commit();
        ps.close();
        this.conn.close();

        return clientes;
    }

}
