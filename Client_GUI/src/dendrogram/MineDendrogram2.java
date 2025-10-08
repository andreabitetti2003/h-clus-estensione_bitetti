package dendrogram;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

import choices.StartUp;

/**
 * Classe MineDendrogram2
 * Visualizza il risultato dell'estrazione del dendrogramma e fornisce un'opzione per salvare il risultato in un file.
 * Comunica con il server per ottenere il dendrogramma e per inviare i dettagli del file di salvataggio.
 * 
 * @author Gruppo A14
 */
public class MineDendrogram2 extends JPanel{
	/** Stream di output per inviare dati al server */
	private ObjectOutputStream out;
	/** Stream di input per ricevere dati dal server */
	private ObjectInputStream in;
	/** Profondità del dendrogramma */
	private int depth;
	/** Distanza selezionata (single-link o average-link) */
	private int distance;
	/** Etichetta per visualizzare il dendrogramma */
	private JTextArea dendrogram_label;
	/** Campo di testo per inserire il nome del file di salvataggio */
	private JTextField txt_file_name;
	/** Etichetta per visualizzare eventuali errori */
	private JLabel error_label;
	
	 /**
     * Costruttore della classe MineDendrogram2.
     * Inizializza l'interfaccia grafica e carica il dendrogramma dal server.
     * 
     * @param start_up Oggetto StartUp che gestisce la comunicazione e le schermate.
     */
	public MineDendrogram2(StartUp start_up) {
		this.depth = start_up.getMineDendrogram().getDepth();
		this.distance = start_up.getMineDendrogram().getDistance();
		this.out = start_up.getOut();
		this.in = start_up.getIn();
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		
	    JPanel title_panel = start_up.getTitlePanel();
	    
	    dendrogram_label = new JTextArea("");
	    dendrogram_label.setEditable(false);
	    dendrogram_label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 18));
	    dendrogram_label.setAlignmentX(Component.CENTER_ALIGNMENT);
	      
	    JScrollPane scroll_pane = new JScrollPane();
	    scroll_pane.setViewportView(dendrogram_label);
	    scroll_pane.setLocation(70, 60);
	    scroll_pane.setBackground(Color.WHITE);
	    scroll_pane.getViewport().setBackground(Color.WHITE);
	    scroll_pane.getVerticalScrollBar().setBackground(Color.WHITE); 
	    scroll_pane.getHorizontalScrollBar().setBackground(Color.WHITE); 
	    scroll_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scroll_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scroll_pane.setMaximumSize(new Dimension(900, 500));
		
		try {
			mineDedrogramOnServer();
		} catch (IOException | ClassNotFoundException ex) {
        	start_up.ShowScreenOperation("Problema nella comunicazione con il Server");
        }
		
		JLabel file_label = new JLabel("Inserire il nome dell'archivio (comprensivo di estensione)");
		file_label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 18));
		file_label.setAlignmentX(Component.CENTER_ALIGNMENT);

	    txt_file_name = new JTextField();
	    txt_file_name.setAlignmentX(Component.CENTER_ALIGNMENT);
	    txt_file_name.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 16));
	    txt_file_name.setPreferredSize(new Dimension(300, 20));

	    error_label = new JLabel(" ");
        error_label.setForeground(Color.RED);
        error_label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
        error_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        error_label.setHorizontalAlignment(SwingConstants.CENTER);
        Dimension label_size = new Dimension(900, 17);
        error_label.setPreferredSize(label_size);
        error_label.setMinimumSize(label_size);
        error_label.setMaximumSize(label_size);

	    
	    JButton send_button = new JButton("Salva"); 
	    send_button.setBackground(new Color(0, 0, 68)); 
	    Dimension label_size2 = new Dimension(85, 35);
	    send_button.setPreferredSize(label_size2);
	    send_button.setMinimumSize(label_size2);
	    send_button.setMaximumSize(label_size2);
	    send_button.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
	    send_button.setFocusPainted(false); 
	    send_button.setOpaque(true);
	    send_button.setForeground(Color.WHITE); 
	    send_button.setAlignmentX(Component.CENTER_ALIGNMENT); 
	    send_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mineDedrogramOnServer2();
				} catch (IOException | ClassNotFoundException ex) {
                	start_up.ShowScreenOperation("Problema nella comunicazione con il Server");
                }
			}
		});
		
	    JPanel combo_panel = new JPanel();
	    combo_panel.setLayout(new FlowLayout());
	    combo_panel.setBackground(Color.WHITE);
	    combo_panel.add(file_label);
	    combo_panel.add(txt_file_name);
	    combo_panel.setMaximumSize(new Dimension(900, 50));
	    
	    add(title_panel);
	    add(Box.createRigidArea(new Dimension(0, 4)));
	    add(combo_panel);
	    add(scroll_pane);
	    add(error_label);
	    add(send_button);
	    add(Box.createRigidArea(new Dimension(0, 10)));
	}
	
	/**
	 * Metodo mineDedrogramOnServer
     * Metodo per richiedere al server il dendrogramma e visualizzarlo nell'interfaccia.
     * 
     * @throws IOException Se si verifica un errore nella comunicazione con il server
     * @throws ClassNotFoundException Se si verifica un errore durante la lettura della risposta dal server
     */
	private void mineDedrogramOnServer() throws IOException, ClassNotFoundException {
		out.writeObject(distance);
		in.readObject();
		dendrogram_label.setText((String)in.readObject());
	}
	
	/**
	 * Metodo mineDedrogramOnServer2
     * Metodo per inviare al server il nome del file e salvare il dendrogramma.
     * 
     * @throws IOException Se si verifica un errore nella comunicazione con il server
     * @throws ClassNotFoundException Se si verifica un errore durante la lettura della risposta dal server
     */
	private void mineDedrogramOnServer2() throws IOException, ClassNotFoundException {
		String file_name = txt_file_name.getText().trim();
		out.writeObject(file_name);
		String answer = (String) in.readObject();

		if (!answer.equals("OK")) 
			error_label.setText(answer);
		else 
			error_label.setText((String)in.readObject());
		
		txt_file_name.setText("");
		txt_file_name.requestFocus();
		out.writeObject(1);
		out.writeObject(depth);
		in.readObject();
		out.writeObject(distance);
		in.readObject();
		in.readObject();
	}
}
