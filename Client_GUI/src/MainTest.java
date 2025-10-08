import java.io.IOException;
import choices.StartUp;
/**
 * Classe Main del Client
 * Avvia un client
 * 
 * @author Gruppo A14
 */
public class MainTest {
	/**
 	* Metodo main
 	* Punto di ingresso dell'applicazione lato Client
 	* 
 	* @param args argomenti passati da terminale
 	*/
	public static void main(String[] args) {
    	String ip = args[0];
		int port = Integer.parseInt(args[1]);
		StartUp start_up = null;
		try {
			start_up = new StartUp(ip, port);
	    } catch (IOException | ClassNotFoundException| NullPointerException e) {
	    	if (start_up != null) {
	    		start_up.ShowScreenOperation("Problema nella comunicazione con il Server");
            } else {
            	new StartUp("Problema nella comunicazione con il Server");
            }
		} 
    }
}
