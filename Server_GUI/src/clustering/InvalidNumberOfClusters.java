package clustering;
/**
 * Eccezione InvalidNumberOfClusters
 * Eccezione sollevata nel caso in cui l'insime di cluster non contiene almeno 2 cluster per effettuare il merge
 * 
 * @author Gruppo A14
 */
public class InvalidNumberOfClusters extends Exception {
	/**
	 * Costruttore
	 * 
	 * @param msg messaggio di errore
	 */
	InvalidNumberOfClusters(String msg) {
        super(msg);
    }
}
