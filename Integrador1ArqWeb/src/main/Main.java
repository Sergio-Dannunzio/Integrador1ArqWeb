package main;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import dao.mysql.DBDAO;
import dao.mysql.MySQLClienteDAO;
import dao.mysql.MySQLFacturaDAO;
import dao.mysql.MySQLFacturaProductoDAO;
import dao.mysql.MySQLProductoDAO;
import entities.Cliente;
import factory.DAOFactory;

public class Main {
	public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {
		DBDAO mysqlFactory = DAOFactory.getDAODB(DAOFactory.DB_MYSQL);

	    MySQLClienteDAO MySQLClienteDAO = mysqlFactory.getClientoDAO();
	    MySQLFacturaDAO MySQLFacturaDAO = mysqlFactory.getFacturaDAO();
	    MySQLProductoDAO MySQLProductoDAO = mysqlFactory.getProductoDAO();
        MySQLFacturaProductoDAO MySQLFacturaProductoDAO = mysqlFactory.getFacturaProductoDAO();

        CSVParser ProductoParser = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./resources/productos.csv"));
        CSVParser ClienteParser = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./resources/clientes.csv"));
        CSVParser FacturaParser = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./resources/facturas.csv"));
        CSVParser FacturaProductoParser = CSVFormat.DEFAULT.withHeader().parse(new FileReader("./resources/facturas-productos.csv"));

        MySQLClienteDAO.loadData(ClienteParser);
        MySQLProductoDAO.loadData(ProductoParser);
        MySQLFacturaDAO.loadData(FacturaParser);
        MySQLFacturaProductoDAO.loadData(FacturaProductoParser);

        System.out.println("Producto mas recaudado: ");
        System.out.println(MySQLProductoDAO.productoMasRecaudado() + System.lineSeparator());

        System.out.println("Listado de clientes ordenado por facturacion: ");
        ArrayList<Cliente> clientes = MySQLClienteDAO.masFacturados();
        for(Cliente c: clientes) {
            System.out.println(c);
        }
	}

}
