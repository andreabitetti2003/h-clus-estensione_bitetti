package clustering;
/**
 * Eccezione ExtensionException
 * Eccezione sollevata nel caso in cui il file ha estensione errata
 * 
 * @author Gruppo A14
 */
public class ExtensionException extends Exception{
	/**
	 * Costruttore
	 * 
	 * @param msg messaggio di errore
	 */
	public ExtensionException(String msg) {
		super(msg);
	}
}