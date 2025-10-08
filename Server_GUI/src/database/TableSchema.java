package database;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe TableSchema
 * Utilizzata per rappresentare la struttura di una tabella di un database
 * 
 * @author Gruppo A14
 *
 */
public class TableSchema {
	/** Istanza della classe DbAccess gestisce l'accesso al database */
	private final DbAccess db;
	/** Lista di Column che rappresentano le colonne della tabella */
	List<Column> tableSchema = new ArrayList<Column>();
	/**
	 * Classe Column
	 * Classe interna che rappresenta un attributo della tabella
	 * 
	 * @author Gruppo A14
	 *
	 */
	public class Column {
		/** Nome attributo */
		private final String name;
		/** Tipo attributo */
		private final String type;
		/**
		 * Costruttore 
		 * Inizializza un'istanza di Column con un nome e un tipo.
		 * 
		 * @param name nome attributo
		 * @param type tipo attributo
		 */
		Column(String name, String type) {
			this.name = name;
			this.type = type;
		}
		/**
		 * Metodo getColumnName
		 * Restituisce il nome dell'attributo/colonna della tabella
		 * 
		 * @return name nome attributo
		 */
		public String getColumnName() {
			return name;
		}
		/**
		 * Metodo isNumber
		 * Verifica se il tipo dell'attributo è "number"
		 * 
		 * @return true se è number, false altrimenti
		 */
		public boolean isNumber() {
			return type.equals("number");
		}
		/**
		 * Metodo toString
		 * Restituisce una rappresentazione testuale dell'attributo/colonna della tabella 
		 * 
		 * @return restituisce una rappresentazione testuale dell'attributo/colonna della tabella 
		 */
		public String toString() {
			return name + ":" + type;
		}
	}
	
	/**
	 * Costruttore
	 * Recupera i metadati della tabella utilizzando DatabaseMetaData e popola la lista tableSchema con 
	 * le colonne della tabella, convertendo i tipi SQL nei tipi Java appropriati
	 * 
	 * @param db stanza della classe DbAccess gestisce l'accesso al database
	 * @param tableName nome della tabella del database
	 * @throws SQLException eccezione sollevata nel caso in cui ci sono errori SQL
	 * @throws DatabaseConnectionException eccezione sollevata nel caso in cui è impossibile connettersi al database
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException, DatabaseConnectionException {
		this.db = db;
		HashMap<String,String> mapSQL_JAVATypes = new HashMap<String, String>();
		
		mapSQL_JAVATypes.put("CHAR","string");
		mapSQL_JAVATypes.put("VARCHAR","string");
		mapSQL_JAVATypes.put("LONGVARCHAR","string");
		mapSQL_JAVATypes.put("BIT","string");
		mapSQL_JAVATypes.put("SHORT","number");
		mapSQL_JAVATypes.put("INT","number");
		mapSQL_JAVATypes.put("LONG","number");
		mapSQL_JAVATypes.put("FLOAT","number");
		mapSQL_JAVATypes.put("DOUBLE","number");

		Connection con = this.db.getConnection();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);
   
		while (res.next()) {
			if (mapSQL_JAVATypes.containsKey(res.getString ("TYPE_NAME")))
				tableSchema.add(new Column(res.getString("COLUMN_NAME"),mapSQL_JAVATypes.get(res.getString ("TYPE_NAME"))));	         
		}
		this.db.closeConnection();
		res.close();
	}
	
	/**
	 * Metodo getNumberOfAttributes
	 * Restituisce il numero di colonne/attributi nella tabella
	 * 
	 * @return numero di colonne/attributi
	 */
	public int getNumberOfAttributes() {
		return tableSchema.size();
	}
	
	/**
	 * Metodo getColumn
	 * Restituisce l'oggetto Column corrispondente all'indice specificato
	 * 
	 * @param index indice dell'attributo/colonna
	 * @return attributo/colonna di posizione index
	 */
	public Column getColumn(int index) {
		return tableSchema.get(index);
	}		
}

