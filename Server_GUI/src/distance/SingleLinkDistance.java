package distance;

import clustering.Cluster;
import data.Data;
import data.InvalidSizeException;
import data.Example;
/**
 * Classe SingleLinkDistance
 * Implementa l'interfaccia ClusterDistance 
 * Calcola la distanza minima tra due cluster
 *
 * @author Gruppo A14
 *
 */
public class SingleLinkDistance implements ClusterDistance {
	/**
	 * Metodo distance
	 * Calcola la distanza minima tra due cluster
	 *
	 * @param c1 cluster con cui calcolare la distanza
	 * @param c2 cluster con cui calcolare la distanza
	 * @param d istanza di Data
	 * @return min double che contiene la distanza minima
	 * @throws InvalidSizeException eccezione sollevata nel caso in cui gli esempi hanno dimensioni diverse
	 */
	public double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException {
		double min = Double.MAX_VALUE;
		for (Integer i1: c1) {
			Example e1 = d.getExample(i1);
			for (Integer i2: c2) {
				double distance = e1.distance (d.getExample(i2));
				if (distance < min)				
					min = distance;
			}
		}
		return min;
	}
}