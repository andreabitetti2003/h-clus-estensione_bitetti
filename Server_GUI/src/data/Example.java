package data;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
/**
 * Classe Example
 * Modella la entità esempio come lista di valori reali
 * 
 * @author Gruppo A14
 *
 */
public class Example implements Iterable<Double> {
	/**
	 * Lista di valori reali 
	 */
	private final List<Double> example;
	
	/**
	 * Costruttore 
	 * Crea un oggetto della classe Example
	 * 
	 */
	public Example() {
		example = new LinkedList<>();
	}
	
	/**
	 * Metodo Iterator
	 * Restituisce un iteratore per muoversi attraverso una sequenza di reali 
	 * 
	 * @return example.iterator() iterator per scorrere gli elementi di example
	 */
	public Iterator<Double> iterator() {
		return example.iterator();
	}
	
	/**
	 * Metodo add
	 * Aggiunge l'elmento v in example
	 * 
	 * @param v valore reale da aggiungere ad example
	 */
	public void add(Double v) {
		example.add(v);
	}
	
	/**
	 * Metodo get
	 * Restituisce il valore di posizione i contenuto in example
	 * 
	 * @param index indice del valore
	 * @return valore reale di posizione index
	 */
	private Double get(int index) {
		return example.get(index);
	}
	
	/**
	 * Metodo distance
	 * Calcola la distanza euclidea tra this.example e newE.example
	 * 
	 * @param newE istanza di Example
	 * @return sum somma delle distanze tra i valori degli oggetti Example
	 * @throws InvalidSizeException eccezione sollevata nel caso in cui gli esempi hanno dimensioni diverse
	 */
	public double distance(Example newE) throws InvalidSizeException {	
		if (example.size() != newE.example.size())
            throw new InvalidSizeException("Gli esempi hanno dimensioni diverse!");

		Double sum = 0.0;
		Iterator <Double> iterator_newe = newE.iterator();
		Iterator <Double> iterator_this = this.iterator();
		while (iterator_newe.hasNext() && iterator_this.hasNext()) {
			Double diff = 0.0;
			diff = Math.pow((iterator_newe.next() - iterator_this.next()), 2);
			sum = sum + diff;
		}
		return sum;
	}
		
	/**
	 * Metodo toString
	 * Restituisce un stringa contentente i valori di example
	 * 
	 * @return str stringa contentente i valori di example
	 */
	public String toString() {
		String str = "";
		str += "[";
		int i = 0;
		for (Double e: this.example) {
			if (i != 0) {
				str += ",";
			}
			str += e;
			i++;
		}
		str += "]";
		return str;
	}
}
