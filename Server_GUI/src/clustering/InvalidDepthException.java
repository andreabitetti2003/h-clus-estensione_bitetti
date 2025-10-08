package clustering;
/**
 * Eccezione InvalidDepthException
 * Eccezione sollevata nel caso in cui la profondità con cui è stato istanziato il 
 * dendrogramma è superiore al numero di esempi memorizzati nel dataset o minore di 1
 *  
 * @author Gruppo A14
 *
 */
public class InvalidDepthException extends Exception {
	/**
	 * Costruttore
	 * 
	 * @param msg messaggio di errore
	 */
	public InvalidDepthException(String msg) {
		super(msg);
	}
}
