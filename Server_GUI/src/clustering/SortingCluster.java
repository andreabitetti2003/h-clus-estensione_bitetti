package clustering;
import java.util.Comparator;
import java.io.Serializable;
/**
 * Classe SortingCluster
 * Utilizzata per definire un criterio di ordinamento per oggetti di tipo Integer
 * 
 * @author Gruppo A14
 *
 */
class SortingCluster implements Comparator<Integer>, Serializable {
	 /**
	 * Metodo compare
	 * Utilizzata per mantenere l'ordinamento di inserimento
	 * 
	 * @param o1 oggetti Integer
	 * @param o2 oggetti Integer
	 * @return valore positivo 
	 */
	public int compare(Integer o1, Integer o2) { 
	        return 1; 
	 } 
}
