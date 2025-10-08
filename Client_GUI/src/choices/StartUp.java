package choices;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import dendrogram.*;

/**
 * Classe StartUp
 * La classe StartUp rappresenta il punto di ingresso principale per l'applicazione client
 * H-Clus. Gestisce la navigazione GUI usando CardLayout e si occupa della comunicazione
 * con il server tramite socket. La classe fornisce diversi schermi per consentire 
 * l'interazione con il server per l'estrazione e il caricamento di dendrogrammi.
 * 
 * @author Gruppo A14
 */
public class StartUp extends JFrame{
	/** Stream di output per inviare oggetti al server. */
	private ObjectOutputStream out;
	/** Stream di input per ricevere oggetti dal server. */
	private ObjectInputStream in ;
	/** Socket utilizzato per la connessione al server. */
	private static Socket socket;
	/** Contenitore principale che contiene i componenti della GUI. */
    private Container container;
    /** Layout per gestire la navigazione tra i diversi pannelli della GUI. */
    private CardLayout cardLayout;
    /** Riferimento all'istanza della schermata di inserimento delle tabelle. */
	private TableInsertion table_insertion;
	/** Riferimento all'istanza della schermata di estrazione dei dendrogrammi. */
	private MineDendrogram mine_dendrogram;
	
	/**
	 * Metodo getOut
     * Restituisce lo stream di output per la comunicazione con il server.
     * 
     * @return ObjectOutputStream per inviare dati al server
     */
	public ObjectOutputStream getOut() {
		return this.out;
	}
	
	/**
	 * Metodo getIn
     * Restituisce lo stream di input per la comunicazione con il server.
     * 
     * @return ObjectInputStream per ricevere dati dal server
     */
	public ObjectInputStream getIn() {
		return this.in;
	}
	
	/**
	 * Metodo getSocket
     * Restituisce il socket attualmente connesso al server.
     * 
     * @return Il socket utilizzato per la connessione al server
     */
	static Socket getSocket() {
		return socket;
	}
	
	/**
	 * Metodo setTableInsertion
     * Imposta l'istanza di TableInsertion.
     * 
     * @param table_insertion l'istanza di TableInsertion da associare
     */
	void setTableInsertion(TableInsertion table_insertion) {
		this.table_insertion = table_insertion;
	}
	
	/**
	 * Metodo getTableInsertion
     * Restituisce l'istanza di TableInsertion.
     * 
     * @return L'istanza corrente di TableInsertion
     */
	public TableInsertion getTableInsertion() {
		return this.table_insertion;
	}
	
	/**
	 * Metodo setMineDendrogram
     * Imposta l'istanza di MineDendrogram.
     * 
     * @param mine_dendrogram l'istanza di MineDendrogram da associare
     */
	public void setMineDendrogram(MineDendrogram mine_dendrogram) {
		this.mine_dendrogram = mine_dendrogram;
	}
	
	/**
	 * Metodo getMineDendrogram
     * Restituisce l'istanza di MineDendrogram.
     * 
     * @return L'istanza corrente di MineDendrogram
     */
	public MineDendrogram getMineDendrogram() {
		return this.mine_dendrogram;
	}
	
	/**
	 * Metodo getTitlePanel
     * Crea un pannello con il titolo e il pulsante di riavvio client.
     * 
     * @return Un JPanel contenente il titolo e il pulsante di riavvio
     */
	public JPanel getTitlePanel() {
		JButton restart_button = new JButton("Riavvia client");
		restart_button.setBackground(Color.GRAY); 
		restart_button.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
		restart_button.setFocusPainted(false); 
		restart_button.setOpaque(true);
		restart_button.setForeground(Color.WHITE); 
		restart_button.setAlignmentX(Component.RIGHT_ALIGNMENT); 
		restart_button.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
        	restart();
        	cardLayout.show(container, "ScreenOperation");
          }
        });
        JPanel restart_panel = new JPanel();
        restart_panel.setLayout(new BoxLayout(restart_panel, BoxLayout.X_AXIS));
        restart_panel.add(Box.createHorizontalGlue());
        restart_panel.add(restart_button);
        restart_panel.setBackground(Color.WHITE);
		
		ImageIcon logo = new ImageIcon("src/img/logo.png");
	    JLabel logo_label = new JLabel(new ImageIcon(logo.getImage().getScaledInstance(60, 25, Image.SCALE_SMOOTH)));
	    logo_label.setAlignmentX(Component.CENTER_ALIGNMENT); 
		
	    JLabel title_label = new JLabel("H-Clus");
	    title_label.setForeground(new Color(0, 0, 160)); 
	    title_label.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 42));
	    title_label.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    JLabel title_label2 = new JLabel("H-Clus");
	    title_label2.setForeground(new Color(128, 128, 128)); 
	    title_label2.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 42));
	    title_label2.setAlignmentX(Component.CENTER_ALIGNMENT); 
	    
	    JLabel client_label = new JLabel("Client");
	    client_label.setBackground(new Color(192, 192, 192));
	    client_label.setForeground(new Color(0, 0, 128)); 
	    client_label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 23));
	    client_label.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    JPanel title_panel = new JPanel();
	    title_panel.setLayout(new BoxLayout(title_panel, BoxLayout.Y_AXIS));
	    title_panel.setBackground(Color.WHITE);
	    title_panel.add(logo_label);;
	    title_panel.add(title_label);
	    title_panel.add(Box.createRigidArea(new Dimension(0, -60)));
	    title_panel.add(title_label2);
	    title_panel.add(client_label);
		
		JPanel main_panel = new JPanel();
		main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
		main_panel.setBackground(Color.WHITE);
		main_panel.add(Box.createRigidArea(new Dimension(0, 10)));
		main_panel.add(restart_panel);
		main_panel.add(Box.createRigidArea(new Dimension(0, 6)));
		main_panel.add(title_panel);
		main_panel.add(Box.createRigidArea(new Dimension(0, 4)));
		return main_panel;
	}
	
	/**
	 * Costruttore della classe StartUp.
     * Costruttore principale. Inizializza la connessione al server e imposta la
     * finestra principale dell'interfaccia utente.
     * 
     * @param ip   L'indirizzo IP del server
     * @param port La porta del server
     * @throws IOException              Se ci sono problemi nella comunicazione con il server
     * @throws ClassNotFoundException   Se si verifica un problema nel ricevere gli oggetti
     * @throws NullPointerException     Se non viene trovata la connessione
     */
	public StartUp(String ip, int port) throws IOException, ClassNotFoundException, NullPointerException {
		cardLayout = new CardLayout();
		container = getContentPane();   
		container.setLayout(cardLayout); 
		start(ip,port);
		setSize(900,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Client H-CLUS");
		setResizable(false);
		ImageIcon logo = new ImageIcon("src/img/logo.png");
		setIconImage(logo.getImage());
		setVisible(true);
	}
	
	/**
	 * Costruttore alternativo della classe StartUp.
     * Costruttore alternativo che mostra una schermata di errore.
     * 
     * @param error Il messaggio di errore da visualizzare
     */
	public StartUp(String error) {
		cardLayout = new CardLayout();
		container = getContentPane();   
		container.setLayout(cardLayout);  
		ScreenOperation start_up = new ScreenOperation(this,error);
		container.add(start_up, "ScreenOperation");
		setSize(900,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Client H-CLUS");
		setResizable(false);
		ImageIcon logo = new ImageIcon("src/img/logo.png");
		setIconImage(logo.getImage());
		setVisible(true);
	}
	
	/**
	 * Metodo start
     * Avvia la connessione al server e carica la schermata principale.
     * 
     * @param ip   L'indirizzo IP del server
     * @param port La porta del server
     * @throws IOException            Se si verifica un errore di connessione
     * @throws ClassNotFoundException Se si verifica un errore nella ricezione degli oggetti
     */
	private void start(String ip, int port) throws IOException, ClassNotFoundException, NullPointerException {
		InetAddress addr = InetAddress.getByName(ip); 
		socket = new Socket(addr, port);
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream()); 
		ScreenOperation start_up = new ScreenOperation(this,null);
		container.add(start_up, "ScreenOperation");
	}
	
	/**
	 * Metodo restart
     * Riavvia la connessione al server.
     * 
     */
	private void restart() {
		try {
			StartUp.socket.close();
			start(StartUp.getSocket().getInetAddress().getHostAddress(),StartUp.getSocket().getPort());
		} catch (IOException | ClassNotFoundException | NullPointerException e) {
			ShowScreenOperation("Problema nella comunicazione con il Server");
        }
	}
	
	/**
	 * Metodo ShowScreenOperation
     * Mostra la schermata di errore o operazione.
     * 
     * @param error Il messaggio di errore da visualizzare
     */
	public void ShowScreenOperation(String error) {
		ScreenOperation startup_screen = new ScreenOperation(this,error);
		container.add(startup_screen, "ScreenOperation");
		cardLayout.show(container, "ScreenOperation");
	}
	
	/**
	 * Metodo ShowTableInsertion
     * Mostra la schermata per l'inserimento delle tabelle.
     */
	void ShowTableInsertion() {
		try {
			Object o = in.readObject();
	        if (o instanceof List<?>) {
	            @SuppressWarnings("unchecked")
	            List<String> temp_tables = (List<String>) o;
	            if(temp_tables.isEmpty()) {
	            	ShowScreenOperation("Database non ha tabelle");
	            } else {
	            	table_insertion  = new TableInsertion(temp_tables,this);
		    		container.add(table_insertion , "TableInsertion");
		    		cardLayout.show(container, "TableInsertion");
	            }
	        } else {
	        	ShowScreenOperation("Problema nella comunicazione con il Server");
	        }
		} catch (IOException |ClassNotFoundException | NullPointerException ex) {
			ShowScreenOperation("Problema nella comunicazione con il Server");
        }
		
	}
	
	/**
	 * Metodo ShowOperationChoice
     * Mostra la schermata di scelta delle operazioni.
     */
	void ShowOperationChoice() {
		OperationChoice choice = new OperationChoice(this);
		container.add(choice, "OperationChoice");
		cardLayout.show(container, "OperationChoice");
	}
	
	/**
	 * Metodo ShowLoadDendrogramFromFile
     * Mostra la schermata per il caricamento del dendrogramma da file.
     */
	void ShowLoadDendrogramFromFile() {
		LoadDendrogramFromFile load_dendrogram_from_file = new LoadDendrogramFromFile(this);
		container.add(load_dendrogram_from_file, "LoadDendrogramFromFile");
		cardLayout.show(container, "LoadDendrogramFromFile");
	}
	
	/**
	 * Metodo ShowMineDendrogram
     * Mostra la schermata per estrarre dendrogrammi.
     */
	void ShowMineDendrogram() {
		mine_dendrogram = new MineDendrogram(this);
		container.add(mine_dendrogram, "MineDendrogram");
		cardLayout.show(container, "MineDendrogram");
	}
	
	/**
	 * Metodo ShowMineDendrogram2
     * Mostra la seconda schermata per estrarre dendrogrammi.
     */
	public void ShowMineDendrogram2() {
		MineDendrogram2 mine_dendrogram2 = new MineDendrogram2(this);
		container.add(mine_dendrogram2, "MineDendrogram2");
		cardLayout.show(container, "MineDendrogram2");
	}
}
