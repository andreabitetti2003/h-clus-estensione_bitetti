package data;
/**
 * Eccezione NoDataException
 * Eccezione sollevata nel caso in cui ci sono problemi con la lettura dal database
 * 
 * @author Gruppo A14
 */
public class NoDataException extends Exception {
	/**
	 * Costruttore
	 * 
	 * @param msg messaggio di errore
	 */
	public NoDataException(String msg) {
		super(msg);
	}
}