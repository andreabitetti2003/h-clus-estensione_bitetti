package database;
/**
 * Eccezione MissingNumberException
 * Eccezione sollevata nel caso in cui la tabella letta dal database contiene attributi non numerici
 * 
 * @author Gruppo A14
 */
public class MissingNumberException extends Exception {
	/**
	 * Costruttore
	 */
	public MissingNumberException() {}
	/**
	 * Costruttore
	 * 
	 * @param msg messaggio di errore
	 */
	public MissingNumberException(String msg) {
		super(msg);
	}
}
