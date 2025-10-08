package dendrogram;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import choices.StartUp;

/**
 * Classe LoadDendrogramFromFile
 * Gestisce la schermata per il caricamento di un dendrogramma da un file salvato. 
 * Permette di selezionare un file, inviare la richiesta al server, e visualizzare il dendrogramma caricato.
 * 
 * @author Gruppo A14
 */
public class LoadDendrogramFromFile extends JPanel {
	/** Stream di output per inviare dati al server */
	private ObjectOutputStream out;
	/** Stream di input per ricevere dati dal server */
	private ObjectInputStream in ;
	/** Area di testo per visualizzare il dendrogramma caricato */
	private JTextArea dendrogram_label;
	/** Combobox per la selezione del file */
	private JComboBox<String> combo_box;
	/** Nome del file selezionato */
	private String file_name = null;
	/** Etichetta per visualizzare messaggi di errore */
	private JLabel error_label;
    
	/**
     * Costruttore della classe LoadDendrogramFromFile.
     * Inizializza l'interfaccia grafica per la selezione del file e imposta la logica di caricamento del dendrogramma.
     *
     * @param start_up Oggetto StartUp per gestire la comunicazione con il server e la navigazione delle schermate.
     */
	public LoadDendrogramFromFile(StartUp start_up) {
		this.out = start_up.getOut();
		this.in = start_up.getIn();
    	
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);

        JPanel title_panel = start_up.getTitlePanel();
        
        JLabel table_label = new JLabel("Inserire il nome dell'archivio");
        table_label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 18));
        table_label.setAlignmentX(Component.CENTER_ALIGNMENT);
	
		combo_box = new JComboBox<>();
		for(String o: start_up.getTableInsertion().getFileNames())
			combo_box.addItem(o);
		combo_box.setBackground(Color.WHITE);
		combo_box.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		combo_box.setSelectedItem(combo_box.getItemAt(0));
		combo_box.setPreferredSize(new Dimension(150, 20)); 
		combo_box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				file_name = (String)combo_box.getSelectedItem();
			}
		});
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
		combo_panel.add(table_label);
		combo_panel.add(combo_box);
		combo_panel.setMaximumSize(new Dimension(500, 50));
		combo_panel.setBackground(Color.WHITE);
		
		dendrogram_label = new JTextArea("");
		dendrogram_label.setEditable(false);
	    dendrogram_label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 18));
	    dendrogram_label.setAlignmentX(Component.CENTER_ALIGNMENT);
	    
	    JScrollPane scroll_pane = new JScrollPane();
	    scroll_pane.setViewportView(dendrogram_label);
	    scroll_pane.setBackground(Color.WHITE);
	    scroll_pane.setLocation(70, 60);
	    scroll_pane.getViewport().setBackground(Color.WHITE);
	    scroll_pane.getVerticalScrollBar().setBackground(Color.WHITE); 
	    scroll_pane.getHorizontalScrollBar().setBackground(Color.WHITE); 
	    scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scroll_pane.setMaximumSize(new Dimension(900, 500));
	    
	    error_label = new JLabel(" ");
        error_label.setForeground(Color.RED);
        error_label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
        error_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        error_label.setHorizontalAlignment(SwingConstants.CENTER);
        Dimension label_size = new Dimension(900, 17);
        error_label.setPreferredSize(label_size);
        error_label.setMinimumSize(label_size);
        error_label.setMaximumSize(label_size);
	    
	    JButton send_button = new JButton("Carica"); 
	    send_button.setBackground(new Color(0, 0, 68)); 
	    Dimension labelSize2 = new Dimension(85, 35);
	    send_button.setPreferredSize(labelSize2);
	    send_button.setMinimumSize(labelSize2);
	    send_button.setMaximumSize(labelSize2);
	    send_button.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
	    send_button.setFocusPainted(false); 
	    send_button.setOpaque(true);
	    send_button.setForeground(Color.WHITE); 
	    send_button.setAlignmentX(Component.CENTER_ALIGNMENT); 
	    send_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					loadDendrogramFromFileOnServer();
                } catch (IOException | ClassNotFoundException ex) {
                	start_up.ShowScreenOperation("Problema nella comunicazione con il Server");
                }
			}
		});
	    
	    add(title_panel);
	    add(combo_panel);
	    add(scroll_pane);
	    add(error_label);;
	    add(send_button);
	    add(Box.createRigidArea(new Dimension(0, 10)));
		
	}
	
	/**
	 * Metodo loadDendrogramFromFileOnServer
     * Carica il dendrogramma selezionato dal file sul server.
     * Invia il nome del file al server, riceve il dendrogramma e lo visualizza.
     * 
     * @throws IOException Se si verifica un errore durante la comunicazione con il server
     * @throws ClassNotFoundException Se si verifica un errore nel caricamento della risposta dal server
     */
	private void loadDendrogramFromFileOnServer() throws IOException, ClassNotFoundException {
		out.writeObject(2);
		file_name = (String) combo_box.getSelectedItem();
		out.writeObject(file_name);
		String answer = (String) in.readObject();
		if (!answer.equals("OK")) {
			error_label.setText(answer);
		} else {
			dendrogram_label.setText((String) in.readObject());
			error_label.setText(" ");
		}
	}
}
