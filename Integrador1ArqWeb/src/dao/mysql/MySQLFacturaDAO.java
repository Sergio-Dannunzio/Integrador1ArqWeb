package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import dao.FacturaDAO;
import entities.Factura;

public class MySQLFacturaDAO implements FacturaDAO{
	private Connection conn;
	
	

	public MySQLFacturaDAO() throws SQLException{
		this.createTable();
	}

	@Override
	public void insertar(Factura b) throws SQLException{
        String insert = "INSERT INTO Factura (idFactura, idCliente_FK) VALUES (?, ?)";
        PreparedStatement ps = this.conn.prepareStatement(insert);
        ps.setInt(1, b.getIdBill());
        ps.setInt(2, b.getIdClient());
        ps.executeUpdate();
        this.conn.commit();
        ps.close();
	}

	@Override
	public void loadData(CSVParser parser) throws SQLException {
        this.conn = DBMySQLDAO.createConnection();
        for(CSVRecord row: parser) {
            Factura b = new Factura(Integer.parseInt(row.get("idFactura")),Integer.parseInt(row.get("idCliente")));
            insertar(b);
        }
        this.conn.close();
		
	}

	@Override
	public void createTable() throws SQLException {
        this.conn = DBMySQLDAO.createConnection();
        String tablaFactura = "CREATE TABLE IF NOT EXISTS Factura("
                + "idFactura INT,"
                + "idCliente_FK INT,"
                + "PRIMARY KEY(idFactura),"
                + "FOREIGN KEY(idCliente_FK) references Cliente(idCliente))";

        this.conn.prepareStatement(tablaFactura).execute();
        this.conn.commit();
        this.conn.close();
		
	}

}
