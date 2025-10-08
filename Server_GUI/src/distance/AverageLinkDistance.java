package distance;
import clustering.Cluster;
import data.Data;
import data.InvalidSizeException;
import data.Example;

/**
 * Classe AverageLinkDistance
 * Implementa l'interfaccia ClusterDistance 
 * Calcola la media della distanza tra due cluster
 * 
 * @author Gruppo A14
 *
 */
public class AverageLinkDistance implements ClusterDistance {
	/**
 	 * Metodo distance
 	 * Restituisce la media delle distanze minime tra i cluster
 	 *      	
 	 * @param c1 cluster con cui calcolare la distanza
 	 * @param c2 cluster con cui calcolare la distanza
 	 * @param d istanza di Data
 	 * @return media delle distanze tra i cluster
 	 * @throws InvalidSizeException eccezione sollevata nel caso in cui gli esempi hanno dimensioni diverse
 	 */
	public double distance(Cluster c1, Cluster c2, Data d) throws InvalidSizeException {
		double sum = 0.0;
		for (Integer i1: c1) {
			Example e1 = d.getExample(i1);
			for (Integer i2: c2) {
				double distance = e1.distance(d.getExample(i2));
				sum = sum + distance;
			}
		}
		return sum / (c1.getSize() * c2.getSize());
	}
}
