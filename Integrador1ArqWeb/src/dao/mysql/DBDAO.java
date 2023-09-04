package dao.mysql;

import java.sql.SQLException;

public abstract class DBDAO {
    protected static DBDAO instance;
    public static DBDAO getInstance(){
        assert instance != null : "Cannot instantiate an abstract DBDAO";
        return instance;
    }
    public abstract MySQLClienteDAO getClientoDAO() throws SQLException;
    public abstract MySQLFacturaDAO getFacturaDAO() throws SQLException;
    public abstract MySQLFacturaProductoDAO getFacturaProductoDAO() throws SQLException;
    public abstract MySQLProductoDAO getProductoDAO() throws SQLException;

}
