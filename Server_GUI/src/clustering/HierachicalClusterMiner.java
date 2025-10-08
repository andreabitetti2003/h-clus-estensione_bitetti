package clustering;
import data.Data;
import distance.ClusterDistance;
import data.InvalidSizeException;
import java.io.*;

/**
 * Classe HierachicalClusterMiner
 * Gestire e manipolare una struttura di clustering 
 * 
 * @author Gruppo A14
 * 
 */
public class HierachicalClusterMiner implements Serializable {
	/**
	 * Percorso della directory in cui verranno salvati o letti oggetti serializzati
	 */
	private static final String FILE = "./file/";
	/**
	 * Oggetto di tipo Dendrogram per gestire la struttura del clustering
	 */
	private final Dendrogram dendrogram;
	
	/**
	 * Costruttore 
	 * Inizializza un oggetto Dendrogram con una profondità depth
	 * 
	 * @param depth profondità del dendrogramma
	 */
	public HierachicalClusterMiner(int depth) {
		dendrogram = new Dendrogram(depth);
	}
	
	/**
	 * Metodo getFile
	 * Restituisce il percorso della directory in cui vengono salvati o letti gli oggetti serializzati.
	 * 
	 * @return il percorso della directory dei file serializzati
	 */
	public static String getFile() {
		return FILE;
	}
	/**
	 * Metodo toString
	 * Rappresentazione testuale del dendrogramma specificando solo gli indici degli esempi
	 * 
	 * @return rappresentazione testuale del dendrogramma
	 */
	public String toString() {
		return dendrogram.toString();
	}
	
	/**
	 * Metodo toString
	 * Rappresentazione testuale del dendrogramma
	 * 
	 * @param data è istanza di Data che modella il dataset su cui il clustering è calcolato
	 * @return rappresentazione testuale del dendrogramma
	 * @throws InvalidDepthException eccezione sollevata nel caso in cui la profondità 
	 * con cui è stato istanziato il dendrogramma è superiore al numero di esempi 
	 * memorizzati nel dataset o minore di 1
	 */
	public String toString(Data data) throws InvalidDepthException {
		return dendrogram.toString(data);
	}

	/**
	 * Metodo mine
	 * Crea inizialmente un cluster per ogni esempio di dati e poi unisce i cluster più vicini fino a 
	 * raggiungere il livello di profondità specificato nel dendrogramma.
	 * 
	 * @param data è istanza di Data che modella il dataset su cui il clustering è calcolato
	 * @param distance oggetto per il calcolo della distanza tra cluster
	 * @throws InvalidNumberOfClusters eccezione sollevata nel caso in cui l'insime di cluster non contiene almeno 2 cluster per effettuare il merge
	 * @throws InvalidSizeException eccezione sollevata nel caso in cui gli esempi hanno dimensioni diverse
	 */
	public void mine(Data data, ClusterDistance distance) throws InvalidNumberOfClusters, InvalidSizeException {
		ClusterSet c_set = new ClusterSet(data.getNumberOfExamples());
		for (int i = 0;i < data.getNumberOfExamples();i++) {
			Cluster c = new Cluster();
			c.addData(i);
			c_set.add(c);
		}
		this.dendrogram.setClusterSet(c_set,0);
		for (int i = 1;i < this.dendrogram.getDepth();i++) {
			c_set = c_set.mergeClosestClusters(distance,data);
			this.dendrogram.setClusterSet(c_set,i);
		}		
	}
	
	/**
	 * Metodo loadHierachicalClusterMiner
	 * Carica un oggetto HierarchicalClusterMiner da un file
	 * 
	 * @param fileName nome del file da cui caricare l'oggetto HierachicalClusterMiner
	 * @return readHierachicalClusterMiner l'oggetto HierachicalClusterMiner caricato da file
	 * @throws IOException eccezione sollevata nel caso in cui si verifica un errore di input/output
	 * @throws ClassNotFoundException eccezione sollevata nel caso in cui la classe dell'oggetto serializzato non viene trovata
	 * @throws IllegalArgumentException eccezione sollevata nel caso in cui il file è vuoto
	 * @throws ExtensionException eccezione sollevata nel caso in cui il file ha estensione errata
	 */
	public static HierachicalClusterMiner loadHierachicalClusterMiner(String fileName) throws IOException, ClassNotFoundException, IllegalArgumentException, ExtensionException {
		String file = FILE + fileName;
		File fileN = new File(file);
		
		if (!containsExtension(fileName))
			throw new ExtensionException("'" + fileName + "' ha estensione errata, il file deve essere di tipo: txt|bin|xml|dat|json\n");
		
		if (fileN.length() == 0) 
			throw new IllegalArgumentException("'" + fileName + "' e' vuoto\n");
		
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileN));
		HierachicalClusterMiner readHierachicalClusterMiner = (HierachicalClusterMiner)in.readObject();
		in.close();
		return readHierachicalClusterMiner;
		
	}
	
	/**
	 * Metodo saveHierachicalClusterMiner
	 * Salva un oggetto corrente HierarchicalClusterMiner in un file
	 * 
	 * @param fileName nome del file in cui salvare l'oggetto corrente HierachicalClusterMiner 
	 * @throws FileNotFoundException eccezione sollevata nel caso in cui il file non viene trovato
	 * @throws IOException eccezione sollevata nel caso in cui si verifica un errore di input/output
	 * @throws ExtensionException eccezione sollevata nel caso in cui il file ha estensione errata
	 */
	public void saveHierachicalClusterMiner(String fileName) throws FileNotFoundException, IOException, ExtensionException {
		String file = FILE + fileName;
		File fileN = new File(file);
		
		if (!containsExtension(fileName))
			throw new ExtensionException("'" + fileName + "' ha estensione errata, il file deve essere di tipo: txt|bin|xml|dat|json\n");
		
		if (fileN.exists()) 
			throw new FileNotFoundException("'" + fileName + "' esiste, creane uno nuovo \n");
		
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileN));
		out.writeObject(this);
		out.close();
	}
	
	/**
	 * Metodo containsExtension
	 * Verifica che il file contiene una delle estensioni concesse 
	 * 
	 * @param file nome del file su cui si effettua il controllo
	 * @return true se il file contiene una delle estensioni, false altrimenti
	 */
	private static boolean containsExtension(String fileName) {
		String extensions[] = {".txt",".bin",".xml",".dat",".json"};
		for (String extension : extensions) {
             if (fileName.endsWith(extension))
                 return true;
        }
        return false;
    }
	
	/**
	 * Metodo getDepth
	 * Restituisce la profondità del dendrogramma
	 * 
	 * @return la profondità del dendrogramma
	 */
	public int getDepth() {
		return dendrogram.getDepth();
	}
}