package database;
import data.Example;
import java.sql.*;
import java.util.*;
/**
 * Classe TableData
 * Gestisce l'accesso e l'elaborazione dei dati contenuti in una tabella di un database
 * 
 * @author Gruppo A14
 *
 */
public class TableData {
	/** Istanza della classe DbAccess gestisce l'accesso al database */
	private final DbAccess db;
	
	/**
	 * Costruttore
	 * Inizializza il campo db con l'istanza fornita di DbAccess
	 * 
	 * @param db connessione al database
	 */
	public TableData(DbAccess db) {
		this.db = db;
	}
	
	/**
	 * Metodo getDistinctTransazioni
	 * Carica gli esempi contenuti nella tabella table in una lista di Example
	 * 
	 * @param table nome della tabella del database da cui recuperare gli esempi
	 * @return list_example lista di Example contenuti nella tabella table
	 * @throws SQLException eccezione sollevata nel caso in cui ci sono errori nell'interrogazione
	 * @throws EmptySetException eccezione sollevata nel caso in cui la tabella letta dal database è vuota
	 * @throws MissingNumberException eccezione sollevata nel caso in cui la tabella letta dal database contiene attributi non numerici
	 * @throws DatabaseConnectionException eccezione sollevata nel caso in cui è impossibile connettersi al database
	 */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException, MissingNumberException, DatabaseConnectionException {
		List<Example> list_example = new ArrayList<>();
		TableSchema table_schema = new TableSchema(db, table);
		
		for (int i = 0; i < table_schema.getNumberOfAttributes();i++) {
			if (!table_schema.getColumn(i).isNumber())
				throw new MissingNumberException();
		}
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
        con = db.getConnection();
        st = con.createStatement();
        rs = st.executeQuery("SELECT DISTINCT * FROM " + table);
        if (!rs.isBeforeFirst())
			throw new EmptySetException();
        while (rs.next()) {
        	Example example = new Example();
        	for (int i = 0; i < table_schema.getNumberOfAttributes();i++) {
        		example.add(rs.getDouble(i+1));
        	}
        	list_example.add(example);
        }
        db.closeConnection();
        rs.close();
        st.close();
		return list_example;
	}
	
	
	/**
	 * Metodo getTableNames
	 * Permette di avere la lista contenente i nomi delle tabelle del db
	 * 
	 * @return tables lista contenente i nomi delle tabelle del db
	 * @throws SQLException eccezione sollevata nel caso in cui ci sono errori nell'interrogazione
	 * @throws DatabaseConnectionException eccezione sollevata nel caso in cui è impossibile connettersi al database
	 */
	public static List<String> getTableNames() throws SQLException, DatabaseConnectionException{
		DbAccess db = new DbAccess();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		con = db.getConnection();
		st = con.createStatement();
        rs = st.executeQuery("SHOW TABLES");
        List<String> tables = new ArrayList<>();
        while (rs.next()) {
        	tables.add(rs.getString(1));
        }
        db.closeConnection();
        rs.close();
        st.close();
        return tables;
	}
}