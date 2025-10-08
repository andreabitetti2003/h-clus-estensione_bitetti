package clustering;

import data.Data;
import java.io.Serializable;
/**
 * Classe Dendrogram
 * Modella un insieme di ClusterSet
 * 
 * @author Gruppo A14
 *
 */
class Dendrogram implements Serializable {
	/**
	 * Insieme di cluster set
	 */
	private final ClusterSet tree[];
	
	/**
	 * Costruttore
	 * Crea un array di oggetti cluster set con una lunghezza di depth
	 * 
	 * @param depth profondità del dendogramma
	 */
	Dendrogram(int depth) {
		tree = new ClusterSet [depth]; 
	}
	
	/**
	 * Metodo setClusterSet
	 * Inserisce un nuovo ClusterSet nel dendrogramma al livello level
	 * 
	 * @param c Cluster set da aggiungere 
	 * @param level livello del dendrogramma in cui aggiungere il ClusterSet c
	 */
	void setClusterSet(ClusterSet c, int level) {
		tree[level] = c;
	}
	
	/**
	 * Metodo getClusterSet
	 * Restituisce il ClusterSet del dendrogramma al livello level
	 * 
	 * @param level livello del dendrogramma
	 * @return tree[level] cluster set al livello level
	 */
	ClusterSet getClusterSet(int level) {
		return tree[level];
	}
	
	/**
	 * Metodo getDepth
	 * Restituisce la profondità del dendrogramma 
	 * 
	 * @return tree.length profondità del dendrogramma 
	 */
	int getDepth() {
		return tree.length;
	}
	
	/**
	 * Metodo toString
	 * Restituisce un stringa contentente per ogni livello del dendrogramma 
	 * gli indici degli esempi inseriti per ogni Cluster del ClusterSet 
	 * 
	 * @return v stringa contentente per ogni livello del dendrogramma 
	 * gli indici degli esempi inseriti per ogni Cluster del ClusterSet
	 */
	public String toString() {
		String v = "";
		for (int i = 0;i < getDepth();i++)
			v += ("level" + i + ":\n" + getClusterSet(i) + "\n");
		return v;
	}
	
	/**
	 * Metodo toString
	 * Restituisce un stringa contentente per ogni livello del dendrogramma 
	 * gli esempi inseriti per ogni Cluster del ClusterSet
	 * 
	 * @param data è istanza di Data che modella il dataset su cui il clustering è calcolato
	 * @return v stringa contentente per ogni livello del dendrogramma 
	 * gli esempi inseriti per ogni Cluster del ClusterSet
	 */
	public String toString(Data data) {
		String v = "";
		for (int i = 0;i < getDepth();i++)
			v += ("level" + i + ":\n" + getClusterSet(i).toString(data) + "\n");
		return v;
	}

}

