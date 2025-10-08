package clustering;
import data.Data;
import java.io.Serializable;
import java.util.TreeSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Classe Cluster 
 * Modella un Cluster come la collezione delle posizioni occupate dagli esempi 
 * raggruppati nel Cluster nel vettore data dell’oggetto che modella il dataset 
 * su cui il clustering è calcolato (istanza di Data) 
 *
 * @author Gruppo A14
 *
 */
public class Cluster implements Iterable<Integer>,Cloneable,Serializable {	
	/**
	 * Set di interi che contine gli indici degli esempi
	 */
	private Set<Integer> clusteredData = new TreeSet<>(new SortingCluster());
	
	/**
	 * Metodo addData
	 * Aggiunge l'indice di un esempio id al Cluster
	 * 
	 * @param id rappresenta l'indice dell'esempio da aggiungere al Cluster
	 */
	void addData(int id) {
		clusteredData.add(id);
	}
	
	/**
	 * Metodo Iterator 
	 * Restituisce un iteratore per muoversi attraverso una sequenza di Cluster 
	 * 
	 * @return clusteredData.iterator() iterator per scorrere gli elementi del Cluster
	 */
	public Iterator<Integer> iterator() {
		return clusteredData.iterator();
	}
	
	/**
	 * Metodo getSize 
	 * Restiuisce il numero di indici di esempi contenuti nel Cluster su cui viene richiamato
	 * 
	 * @return clusteredData.size() dimensione del Cluster
	 */
	public int getSize() {
		return clusteredData.size();
	}
	
	/**
	 * Metodo clone 
	 * Creare una copia di un oggetto Cluster
	 * 
	 * @return copy copia del Cluster
	 */
	public Object clone() throws CloneNotSupportedException {
		Cluster copy;
		try {
			copy = (Cluster)super.clone();
			copy.clusteredData = new TreeSet<>(new SortingCluster());
			copy.clusteredData.addAll(this.clusteredData);
		} catch (CloneNotSupportedException e) {
			throw new CloneNotSupportedException("Errore durante la clonazione dei cluster");
		}
		return copy;
	}
	
	/**
	 * Metodo mergeCluster
	 * Crea un nuovo oggetto Cluster che contiene gli elementi di due oggetti Cluster esistenti, this e c1
	 * 
	 * @param c1 Cluster da fondere al Cluster su cui chiamato il metodo
	 * @return new_c è il Cluster ricavato dall'unione dei Cluster this e c1
	 */
	Cluster mergeCluster(Cluster c1) {
		Cluster new_c = new Cluster();
		//newC = (Cluster)this.clone(); test del clone
		for (Integer c: this.clusteredData)
			new_c.addData(c);
		for (Integer c: c1.clusteredData)
			new_c.addData(c);
		return new_c;
	}
	
	/**
	 * Metodo toString
	 * Restituisce un stringa contentente gli indice degli esempi inseriti nel Cluster corrente
	 * 
	 * @return str stringa contentente gli indice degli esempi inseriti nel Cluster corrente
	 */
	public String toString() {		
		String str = "";
		int i = 0;
		for (Integer c: this.clusteredData) {
			str += c;
			if (i < this.clusteredData.size() - 1) {
				str += ",";
			}
			i++;
		}
		return str;	
	}
	
	/**
	 * Metodo toString
	 * Restituisce un stringa contentente gli esempi inseriti nel Cluster corrente
	 * 
	 * @param data è istanza di Data che modella il dataset su cui il clustering è calcolato 
	 * @return str stringa contentente gli esempi inseriti nel Cluster corrente
	 */
	public String toString(Data data) {
		String str = "";
		for (Integer c: this.clusteredData)
			str += "<" + data.getExample(c) + ">";	
		return str;
	}

}
