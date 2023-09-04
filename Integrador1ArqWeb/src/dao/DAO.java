package dao;

import java.sql.SQLException;

import org.apache.commons.csv.CSVParser;

public interface DAO<T> {

	void insertar(T a) throws SQLException;
    void loadData(CSVParser parser) throws SQLException;
    void createTable() throws SQLException;
}
