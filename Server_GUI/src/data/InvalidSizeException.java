package data;
/**
 * Eccezione InvalidSizeException
 * Eccezione sollevata nel caso in cui gli esempi hanno dimensioni diverse
 * 
 * @author Gruppo A14
 */
public class InvalidSizeException extends Exception {
	/**
	 * Costruttore
	 * 
	 * @param msg messaggio di errore
	 */
	public InvalidSizeException(String msg) {
        super(msg);
    }
}
