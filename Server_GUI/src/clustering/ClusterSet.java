package clustering;
import data.Data;
import data.InvalidSizeException;
import java.io.Serializable;
import distance.ClusterDistance;

/**
 * Classe ClusterSet
 * Modella un insieme di cluster
 * 
 * @author Gruppo A14
 */
class ClusterSet implements Serializable {
	/**
	 * Insieme di Cluster
	 */
	private final Cluster C[];
	/**
	 * Indice dell'ultimo Cluster contenuto nel ClusterSet
	 */
	private int lastClusterIndex = 0;
	
	/**
	 * Costruttore
	 * Crea un array di oggetti Cluster con una lunghezza di k
	 * 
	 * @param k dimensione dell'array di Cluster
	 */
	ClusterSet(int k) {
		C = new Cluster[k];
	}
	
	/**
	 * Metodo add
	 * Aggiunge il Cluster c nel ClusterSet se non è già stato inserito
	 * 
	 * @param c Cluster da aggiungere all'insieme di Cluster
	 */
	void add(Cluster c) {
		for (int j = 0;j < lastClusterIndex;j++)
			if (c == get(j))
				return;
		C[lastClusterIndex] = c;
		lastClusterIndex++;
	}
	
	/**
	 * Metodo get
	 * Restituisce il Cluster di posizione i contenuto nel ClusterSet
	 * 
	 * @param i indice del Cluster 
	 * @return Cluster di posizione i
	 */
	private Cluster get(int i) {
		return C[i];
	}
	
	/**
	 * Metodo toString
	 * Restituisce un stringa contentente gli indice degli esempi inseriti per ogni Cluster del ClusterSet
	 * 
	 * @return str stringa contentente gli indice degli esempi inseriti per ogni Cluster del ClusterSet
	 */
	public String toString() {
		String str = "";
		for (int i = 0;i < C.length;i++) {
			if (get(i) != null) {
				str += "cluster" + i + ":" + get(i) + "\n";
			}
		}
		return str;
	}

	/**
	 * Metodo toString
	 * Restituisce un stringa contentente gli esempi inseriti per ogni Cluster del ClusterSet
	 * 
	 * @param data è istanza di Data che modella il dataset su cui il clustering è calcolato
	 * @return str stringa contentente gli esempi inseriti per ogni Cluster del ClusterSet
	 */
	public String toString(Data data) {
		String str = "";
		for (int i = 0;i < C.length;i++) {
			if (get(i) != null) { 
				str += "cluster" + i + ":" + get(i).toString(data) + "\n";
			}
		}
		return str;
	}
	
	/**
	 * Metodo mergeClosestClusters
	 * Crea una nuova istanza di Cluster set che contiene tutti i Cluster dell’oggetto this a 
	 * meno dei due Cluster fusi al posto dei quali inserisce il Cluster risultante dalla fusione
	 * 
	 * @param distance oggetto per il calcolo della distanza tra cluster
	 * @param data oggetto istanza che rappresenta il dataset in cui si sta calcolando l’oggetto istanza di ClusterSet
	 * @return c_set nuova istanza di ClusterSet con la coppia di cluster più simili fusa in un unico Cluster 
	 * 
	 * @throws InvalidNumberOfClusters eccezione sollevata nel caso in cui l'insime di Cluster non contiene almeno 2 cluster per effettuare il merge
	 * @throws InvalidSizeException eccezione sollevata nel caso in cui gli esempi hanno dimensioni diverse 
	 */
	ClusterSet mergeClosestClusters(ClusterDistance distance, Data data) throws InvalidNumberOfClusters, InvalidSizeException{
		if (lastClusterIndex <= 1)
			throw new InvalidNumberOfClusters("Impossibile effettuare merge, devono essere presenti almeno 2 cluster");
		
		ClusterSet c_set = new ClusterSet(this.C.length - 1);
        double min = Double.MAX_VALUE;
        for (int i = 0; i < this.lastClusterIndex; i++) {
            for (int j = i + 1; j < this.lastClusterIndex; j++) {
                double current_min = distance.distance(this.get(i), this.get(j), data);
                if (current_min < min)
                    min = current_min;
            }
        }

        double[][] distance_matrix = data.distance();
        int i_index = 0, j_index = 1;
        double shorter_distance = Math.abs(min - distance_matrix[i_index][j_index]);
              
        for (int i = 0;i < this.C.length;i++) {
            for (int j = i + 1;j < this.C.length;j++) {
            	double current_distance = Math.abs(min - distance_matrix[i][j]);
                if (current_distance < shorter_distance) {
                	shorter_distance = current_distance;
                	i_index = i;
                	j_index = j;
                }
            }
	    } 
        
        for (int k = 0;k < this.C.length;k++) {
    		if (k != j_index && k != i_index) {
    			c_set.add(this.get(k));
    		} else if (k == i_index) {
    			c_set.add(this.get(i_index).mergeCluster(this.get(j_index)));
    		}
    	}
        return c_set;
    }
	
}