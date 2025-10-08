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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import choices.StartUp;

/**
 * Classe MineDendrogram
 * Gestisce la schermata per l'estrazione di un dendrogramma in base a parametri specifici 
 * come la profondità e il tipo di distanza (single-link o average-link). 
 * Consente l'interazione con un server per elaborare il dendrogramma.
 * 
 * @author Gruppo A14
 */
public class MineDendrogram extends JPanel {
	/** Stream di output per inviare dati al server */
	private ObjectOutputStream out;
	/** Stream di input per ricevere dati dal server */
	private ObjectInputStream in ;
	/** ComboBox per selezionare la profondità */
	private JComboBox<String> combo_box;
	/** RadioButton per il tipo di distanza single-link */
	private JRadioButton button_sngl;
	/** Variabile per memorizzare il tipo di distanza selezionato */
	private int distance = -1;
	/** Variabile per memorizzare la profondità selezionata */
	private int depth = -1;
	/** Riferimento all'oggetto StartUp per la gestione dell'interfaccia */
	private StartUp start_up;
	/** Etichetta per visualizzare eventuali errori */
	private JLabel error_label;
	
	 /**
     * Costruttore della classe MineDendrogram.
     * Inizializza l'interfaccia grafica per selezionare i parametri del dendrogramma e imposta la logica per l'invio al server.
     *
     * @param start_up Oggetto StartUp per gestire la comunicazione con il server e la navigazione delle schermate.
     */
	public MineDendrogram(StartUp start_up) {
		this.out = start_up.getOut();
		this.in = start_up.getIn();
		this.start_up = start_up;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		
		JPanel title_panel = start_up.getTitlePanel();
		
		JLabel depth_label = new JLabel("Introdurre la profondita' del dendrogramma");
		depth_label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 18));
		depth_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		depth_label.setBackground(Color.WHITE);
		
		combo_box = new JComboBox<>();
		combo_box.setBackground(Color.WHITE);
		combo_box.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		for (int i = 1; i <= start_up.getTableInsertion().getMaxDepth();i++)
			combo_box.addItem(Integer.toString(i));
		combo_box.setSelectedItem(combo_box.getItemAt(0));
		combo_box.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				depth = Integer.parseInt((String)combo_box.getSelectedItem());
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
		combo_panel.add(depth_label);
		combo_panel.add(combo_box);
		combo_panel.setMaximumSize(new Dimension(900, 50));
		combo_panel.setBackground(Color.WHITE);
		
		JLabel distance_label = new JLabel("Distanza");
		distance_label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 18));
		distance_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		distance_label.setBackground(Color.WHITE);
		
		button_sngl = new JRadioButton("single-link");
		button_sngl.setFocusPainted(false); 
		button_sngl.setSelected(true);
		button_sngl.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 18));
		button_sngl.setBackground(Color.WHITE);
		
		JRadioButton button_avg = new JRadioButton("average-link");
		button_avg.setFocusPainted(false); 
		button_avg.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 18));
		button_avg.setBackground(Color.WHITE);
		
		ButtonGroup group = new ButtonGroup();
		group.add(button_sngl);
		group.add(button_avg);
		
		JPanel distancePanel = new JPanel();
		distancePanel.setLayout(new FlowLayout());
		distancePanel.add(distance_label);
		distancePanel.add(button_sngl);
		distancePanel.add(button_avg);
		distancePanel.setMaximumSize(new Dimension(500, 50));
		distancePanel.setBackground(Color.WHITE);
		
		error_label = new JLabel(" ");
        error_label.setForeground(Color.RED);
        error_label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
        error_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        error_label.setHorizontalAlignment(SwingConstants.CENTER);
        Dimension label_size = new Dimension(900, 17);
        error_label.setPreferredSize(label_size);
        error_label.setMinimumSize(label_size);
        error_label.setMaximumSize(label_size);
		
        JButton send_button  = new JButton("Invia");
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
        send_button .addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					mineDedrogramOnServer();
				} catch (IOException | ClassNotFoundException ex) {
                	start_up.ShowScreenOperation("Problema nella comunicazione con il Server");
                }
			}
		});
	    add(title_panel);
        add(combo_panel);
        add(distancePanel);
        add(Box.createVerticalGlue());
        add(error_label);
        add(send_button);
        add(Box.createRigidArea(new Dimension(0, 10)));
	}
	
	/**
	 * Metodo getDistance
     * Restituisce la distanza selezionata (1 per single-link, 2 per average-link).
     * 
     * @return Valore della distanza selezionata
     */
	int getDistance() {
		return this.distance;
	}
	
	/**
	 * Metodo getDepth
     * Restituisce la profondità selezionata del dendrogramma.
     * 
     * @return Valore della profondità selezionata
     */
	int getDepth() {
		return this.depth;
	}
	
	/**
	 * Metodo mineDedrogramOnServer
     * Metodo per inviare la richiesta di elaborazione del dendrogramma al server.
     * Invia la profondità e la distanza selezionata, riceve la risposta dal server e gestisce eventuali errori.
     *
     * @throws IOException Se si verifica un errore durante la comunicazione con il server
     * @throws ClassNotFoundException Se si verifica un errore nel caricamento della risposta dal server
     */
	private void mineDedrogramOnServer() throws IOException, ClassNotFoundException {
		out.writeObject(1);
		depth = Integer.parseInt((String)combo_box.getSelectedItem());
		out.writeObject(depth);
		String answer = (String)(in.readObject());
		
		if(button_sngl.isSelected()) {
			distance = 1;
		} else {
			distance = 2;
		}
		
		if(!answer.equals("OK")) {
			error_label.setText(answer);
		} else {
			error_label.setText(" ");
			start_up.setMineDendrogram(MineDendrogram.this);
			start_up.ShowMineDendrogram2();
		}
	}
}
