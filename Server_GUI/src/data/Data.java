package data;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import database.*;
/**
 * Classe Data
 * Modella un Data come la collezione di esempi
 * 
 * @author Gruppo A14
 *
 */
public class Data {
	/**
	 * Rappresenta il dataset di esempi
	 */
	private List<Example> data = new ArrayList<>();
	
	/**
	 * Costruttore
	 * Avvalora un oggetto data prendendo gli esempi dal database
	 * 
	 * @param tableName nome della tabella del database
	 * @throws NoDataException eccezione sollevata nel caso in cui ci sono problemi con la lettura dal database
	 */
	public Data(String tableName) throws NoDataException {
		try {
			DbAccess db = new DbAccess();
			TableData Table = new TableData(db);
			data = Table.getDistinctTransazioni(tableName);
		} catch (SQLException e) {
            throw new NoDataException("Impossibile recuperare i dati della tabella '" + tableName + "'\n");
        } catch (MissingNumberException e) {
            throw new NoDataException("Errore, la tabella '" + tableName + "' contiene attributi non numerici \n");
        } catch (EmptySetException e) {
            throw new NoDataException("Errore, la tabella '" + tableName + "' è vuota \n");
        } catch (DatabaseConnectionException e) {
            throw new NoDataException("Impossibile connettersi al database \n");
        }
	}
	
	/**
	 * Metodo Iterator  
	 * Restituisce un iteratore per muoversi attraverso una sequenza di esempi 
	 * 
	 * @return data.iterator() iterator per scorrere gli elementi di data
	 */
	public Iterator<Example> iterator() {
        return data.iterator();
    } 
	
	/**
	 * Metodo getNumberOfExamples
	 * Restituisce il numero di esempi memorizzati in data
	 * 
	 * @return numero di esempi di data
	 */
	public int getNumberOfExamples() {
		return data.size();
	}
	
	/**
	 * Metodo getExample
	 * Restitisce l'esempio memorizzato in posizione exampleIndex nell'oggetto data
	 * 
	 * @param exampleIndex indice di un esempio memorizzato in data 
	 * @return l’esempio memorizzato in posizione exampleIndex
	 */
	public Example getExample(int exampleIndex) {
		return data.get(exampleIndex);
	}
	
	/**
	 * Metodo distance
	 * Restituisce la matrice triangolare superiore delle distanze 
	 * 
	 * @return matrix_sup matrice triangolare superiore delle distanze Euclidee calcolate tra gli esempi memorizzati in data.
	 * @throws InvalidSizeException eccezione sollevata nel caso in cui gli esempi hanno dimensioni diverse
	 */
	public double [][] distance() throws InvalidSizeException {
		double matrix_sup [][] = new double[this.getNumberOfExamples()][this.getNumberOfExamples()];
		int i,j;
		i = 0;
		for(Example ex: this.data) {
			j = i + 1;
			List<Example> data1 = new ArrayList<>();
			data1.addAll(this.data.subList(j, this.data.size()));
			for (Example ex1: data1) {
				matrix_sup[i][j] = ex.distance(ex1);
				j++;
			}
			i++;
		}
		return matrix_sup;
	}
	
	/**
	 * Metodo toString
	 * Restituisce un stringa contentente gli esempi inseriti in data,
	 * per ogni esempio verrà indicato l'indice di posizione
	 * 
	 * @return str stringa contentente gli esempi inseriti in data
	 */
	public String toString() {
		String str = "";
		int i = 0;
		for(Example ex: this.data) {
			str += i + ":" + ex.toString() + "\n";
			i++;
		}
		return str;
	}

}


