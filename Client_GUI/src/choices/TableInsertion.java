package choices;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.BorderFactory;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Classe TableInsertion
 * Fornisce un'interfaccia grafica per selezionare una tabella da un elenco, 
 * inviare i dati al server e gestire la risposta.
 * Gestisce la selezione della tabella, la comunicazione con il server, 
 * e l'eventuale visualizzazione di errori.
 * 
 * @author Gruppo A14
 */
public class TableInsertion extends JPanel {
	/** Stream di output per inviare dati al server */
	private ObjectOutputStream out;
	/** Stream di input per ricevere dati dal server */
	private ObjectInputStream in ;
	/** Combobox per la selezione della tabella */
	private JComboBox<String> combo_box;
	/** Tabella selezionata */
	private String table = null;
	/** Profondità massima ricevuta dal server */
	private int max_depth = -1;
	/**  Nomi dei file associati alla tabella */
	private String[] file_names = null;
	/** Numero di file ricevuti dal server */
	private int number_files = 0;
	/** Istanza di StartUp per navigare tra le schermate */
	private StartUp start_up;
	/** Etichetta per visualizzare eventuali errori */
	private JLabel error_label;
	
	/**
     * Costruttore della classe TableInsertion.
     * Inizializza l'interfaccia grafica e imposta la logica per l'invio della tabella selezionata al server.
     *
     * @param tables Elenco delle tabelle disponibili per la selezione
     * @param start_up Oggetto StartUp utilizzato per la gestione delle schermate dell'applicazione
     */
	TableInsertion(List<String> tables,StartUp start_up) {
		this.out = start_up.getOut();
		this.in = start_up.getIn();
		this.start_up = start_up;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		
	    JPanel title_panel = start_up.getTitlePanel();

	    JLabel table_label = new JLabel("Inserire il nome della tabella");
	    table_label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 18));
	    table_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		combo_box = new JComboBox<>();
		 if (!tables.isEmpty()) {
            for (String o : tables) {
                combo_box.addItem(o);
            }
		 } 
		combo_box.setBackground(Color.WHITE);
		combo_box.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		combo_box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table = (String)combo_box.getSelectedItem();
			}
		});
		combo_box.setPreferredSize(new Dimension(150, 20));  
	      combo_box.addMouseListener(new MouseAdapter() {
	          public void mouseEntered(MouseEvent e) {
	              combo_box.setBackground(new Color(128, 128, 128)); 
	              combo_box.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 128), 2, true));
	          }
	          public void mouseExited(MouseEvent e) {
	              combo_box.setBackground(Color.WHITE); 
	              combo_box.setBorder(null); 
	          }
	      });
	      
	    JPanel combo_panel = new JPanel();
        combo_panel.setLayout(new FlowLayout());
        combo_panel.setBackground(Color.WHITE);
        combo_panel.add(table_label);
        combo_panel.add(combo_box);
        
        error_label = new JLabel(" ");
        error_label.setForeground(Color.RED);
        error_label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
        error_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        error_label.setHorizontalAlignment(SwingConstants.CENTER);
        Dimension label_size = new Dimension(900, 17);
        error_label.setPreferredSize(label_size);
        error_label.setMinimumSize(label_size);
        error_label.setMaximumSize(label_size);
        
        
	    JButton send_button = new JButton("Invia"); 
      	send_button.setBackground(new Color(0, 0, 68)); 
      	send_button.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
      	Dimension label_size2 = new Dimension(85, 35);
      	send_button.setPreferredSize(label_size2);
      	send_button.setMinimumSize(label_size2);
      	send_button.setMaximumSize(label_size2);
      	send_button.setFocusPainted(false); 
      	send_button.setOpaque(true);
      	send_button.setForeground(Color.WHITE); 
      	send_button.setAlignmentX(Component.CENTER_ALIGNMENT); 
      	send_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					loadDataOnServer();
                } catch (IOException | ClassNotFoundException ex) {
                	start_up.ShowScreenOperation("Problema nella comunicazione con il Server");
                }
			}
		});
      	
	    add(title_panel);
        add(combo_panel);
        add(error_label);
        add(send_button);
        add(Box.createRigidArea(new Dimension(0, 10)));
	}
	
	/**
	 * Metodo getMaxDepth 
     * Restituisce la profondità massima del dendrogramma ricevuta dal server.
     * 
     * @return la profondità massima del dendrogramma
     */
	public int getMaxDepth() {
		return max_depth;
	}
	
	/**
	 * Metodo getFileNames 
     * Restituisce i nomi dei file ricevuti dal server.
     * 
     * @return array di stringhe con i nomi dei file
     */
	public String[] getFileNames() {
		return file_names;
	}
	
	 /**
     * Metodo getNumberFiles 
     * Restituisce il numero di file ricevuti dal server.
     * 
     * @return il numero di file
     */
	int getNumberFiles() {
		return number_files;
	}
	
	/**
	 * Metodo loadDataOnServer
     * Carica i dati della tabella selezionata sul server, gestisce la risposta 
     * del server e visualizza eventuali errori.
     * 
     * @throws IOException se si verifica un errore nella comunicazione con il server
     * @throws ClassNotFoundException se si verifica un errore nel caricamento della risposta
     */
	private void loadDataOnServer() throws IOException, ClassNotFoundException {
		out.writeObject(0);
		table = (String)combo_box.getSelectedItem();
		out.writeObject(table);
		String answer = (String) in.readObject();
		if (!answer.equals("OK")) {
			error_label.setText(answer);
		} else {
			max_depth = (int)in.readObject();
			file_names = (String[])in.readObject();
			number_files = file_names.length;	
			error_label.setText(" ");
			start_up.setTableInsertion(TableInsertion.this);
			start_up.ShowOperationChoice();
		}
	}
}
