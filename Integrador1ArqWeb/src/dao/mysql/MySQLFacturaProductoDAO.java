package dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import dao.FacturaProductoDAO;
import entities.FacturaProducto;

public class MySQLFacturaProductoDAO implements FacturaProductoDAO{
    private Connection conn;

    public MySQLFacturaProductoDAO() throws SQLException {
		this.createTable();
	}
	
	@Override
	public void insertar(FacturaProducto bp) throws SQLException {
        String insert = "INSERT INTO Factura_Producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        PreparedStatement ps = this.conn.prepareStatement(insert);
        ps.setInt(1, bp.getIdBill());
        ps.setInt(2, bp.getIdProduct());
        ps.setInt(3, bp.getQuantity());
        ps.executeUpdate();
        this.conn.commit();
        ps.close();
	}

	@Override
	public void loadData(CSVParser parser) throws SQLException {
        this.conn = DBMySQLDAO.createConnection();
        for(CSVRecord row: parser) {
            FacturaProducto bp = new FacturaProducto(Integer.parseInt(row.get("idFactura")),Integer.parseInt(row.get("idProducto")),Integer.parseInt(row.get("cantidad")));
            insertar(bp);

        }
        this.conn.close();
		
	}

	@Override
	public void createTable() throws SQLException {
        this.conn = DBMySQLDAO.createConnection();

        String tablaFactura_Producto = "CREATE TABLE IF NOT EXISTS Factura_Producto("
                + "idFactura INT,"
                + "idProducto INT,"
                + "cantidad INT,"
                + "PRIMARY KEY(idFactura, idProducto),"
                + "FOREIGN KEY(idFactura) references Factura(idFactura),"
                + "FOREIGN KEY(idProducto) references Producto(idProducto))";

        this.conn.prepareStatement(tablaFactura_Producto).execute();
        this.conn.commit();
        this.conn.close();
	}

}
