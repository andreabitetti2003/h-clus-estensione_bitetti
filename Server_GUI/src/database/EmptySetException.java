package database;
/**
 * Eccezione EmptySetException
 * Eccezione sollevata nel caso in cui la tabella letta dal database è vuota
 * 
 * @author Gruppo A14
 */
public class EmptySetException extends Exception {
	/**
	 * Costruttore
	 */
	public EmptySetException() {}
	/**
	 * Costruttore
	 * 
	 * @param msg messaggio di errore
	 */
	public EmptySetException(String msg) {
		super(msg);
	}
}
