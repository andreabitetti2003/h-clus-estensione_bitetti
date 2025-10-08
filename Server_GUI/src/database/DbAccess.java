package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Gruppo A14
 *
 */
public class DbAccess {
	/** Nome del driver */
	private final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    /** Nome del DBMS */
    private final String DBMS = "jdbc:mysql";
    /** Indirizzo del server */
    private final String SERVER = "localhost";
    /** Nome del database */
    private final String DATABASE = "MapDB";
    /** Porta del server */
    private final int PORT = 3306;
    /** Nome utente per l'accesso al database */
    private final String USER_ID = "MapUser";
    /** Password del database */
    private final String PASSWORD = "map";
    /** Istanza di Connection rappresenta la connessione al database e può essere utilizzato per eseguire query SQL */
    private Connection conn;

    /**
     * Metodo initConnection
     * Inizializza la connessione al database
     * 
     * @throws DatabaseConnectionException eccezione sollevata nel caso in cui è impossibile connettersi al database
     */
    public void initConnection() throws DatabaseConnectionException {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver non trovato: " + e.getMessage());
            throw new DatabaseConnectionException(e.toString());
        }
        String connection_string = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE + "?user=" + USER_ID + "&password=" + PASSWORD + "&serverTimezone=UTC";
        try {
            conn = DriverManager.getConnection(connection_string);
        } catch (SQLException e) {
            throw new DatabaseConnectionException(e.toString());
        }
    }

    /**
     * Metodo getConnection
     * Permette di ottenere una connessione al database
     * 
     * @return conn istanza di Connection rappresenta la connessione al database e può essere utilizzato per eseguire query SQL
     * @throws DatabaseConnectionException eccezione sollevata nel caso in cui è impossibile connettersi al database
     */
    public Connection getConnection() throws DatabaseConnectionException {
        this.initConnection();
        return conn;
    }

    /**
     * Metodo closeConnection
     * Chiude la connessione al database
     * 
     * @throws SQLException eccezione sollevata nel caso in cui si verifica un errore durante la chiusura della connessione
     */
    public void closeConnection() throws SQLException {
        conn.close();
    }

}
