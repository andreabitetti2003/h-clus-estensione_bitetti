package distance;
import clustering.Cluster;
import data.InvalidSizeException;
import data.Data;

/**
 * Interfaccia ClusterDistance contiene metodo per calcolare la distanza tra due cluster
 * 
 * @author Gruppo A14
 *
 */
public interface ClusterDistance {
	/**
	 * Metodo distance
	 * 
	 * @param c1 cluster con cui calcolare la distanza
	 * @param c2 cluster con cui calcolare la distanza
	 * @param d istanza di Data
	 * @return double
	 * @throws InvalidSizeException eccezione sollevata nel caso in cui gli esempi hanno dimensioni diverse 
	 */
	double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException;
}