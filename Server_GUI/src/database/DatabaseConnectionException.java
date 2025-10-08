package database;
/**
 * Eccezione DatabaseConnectionException
 * Eccezione sollevata nel caso in cui è impossibile connettersi al database
 * 
 * @author Gruppo A14
 */
public class DatabaseConnectionException extends Exception {
	/**
	 * Costruttore
	 * 
	 * @param msg messaggio di errore
	 */
	DatabaseConnectionException(String msg) {
		super(msg);
	}
}