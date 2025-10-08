import server.MultiServer;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.Box;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Container;
import java.awt.CardLayout;
/**
 * Classe Main del Server
 * Avviare un server
 * 
 * @author Gruppo A14
 *
 */
public class Main extends JFrame{
	/** Istanza di Container, visualizzazione pannelli */
	private Container container;
	/** Istanza di CardLayout, per gestione dei pannelli */
	private CardLayout cardLayout;
	/** Variabile stringa per la visualizzazione degli errori */
	private static String error = null;
	
	/**
	 * Costruttore
	 * Punto di ingresso dell'applicazione lato Server e creazione interfaccia
	 * 
	 * @param args argomenti passati da terminale
	 */
	public Main(String[] args){
		cardLayout = new CardLayout();
		container = getContentPane();   
		container.setLayout(cardLayout); 
		int port = validatePort(args);
       
        JPanel main_panel = new JPanel();
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));
        main_panel.setBackground(Color.WHITE);
        
    	ImageIcon logo = new ImageIcon("src/img/logo.png");
        JLabel logo_label = new JLabel(new ImageIcon(logo.getImage().getScaledInstance(60, 25, Image.SCALE_SMOOTH)));
        logo_label.setAlignmentX(Component.CENTER_ALIGNMENT); 
        
        JLabel title_label = new JLabel("H-Clus");
        title_label.setForeground(new Color(0, 0, 160)); 
        title_label.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 52));
        title_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        

        JLabel title_label2 = new JLabel("H-Clus");
        title_label2.setForeground(new Color(128, 128, 128)); 
        title_label2.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 52));
        title_label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel client_label = new JLabel("Server");
        client_label.setBackground(new Color(192, 192, 192));
        client_label.setForeground(new Color(0, 0, 128)); 
        client_label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 23));
        client_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel created_by_label = new JLabel("created by GruppoA14");
        created_by_label.setBackground(new Color(192, 192, 192));
        created_by_label.setForeground(new Color(0, 0, 128)); 
        created_by_label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 23));
        created_by_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel our_name_label = new JLabel(" Bitetti Andrea - Franco Andrea - Gatti Giovanni ");
        our_name_label.setForeground(new Color(0, 0, 64)); 
        our_name_label.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 12));
        our_name_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        main_panel.add(Box.createVerticalGlue());
        main_panel.add(logo_label); 
        main_panel.add(title_label);
        main_panel.add(Box.createRigidArea(new Dimension(0, -75)));
        main_panel.add(title_label2);
        main_panel.add(Box.createRigidArea(new Dimension(0, -10)));
        main_panel.add(client_label);
        main_panel.add(created_by_label);
        main_panel.add(Box.createRigidArea(new Dimension(0, -10))); 
        main_panel.add(our_name_label);
        main_panel.add(Box.createVerticalGlue()); 
        
        
        JPanel button_panel = new JPanel();
        button_panel.setLayout(new FlowLayout());
        button_panel.setBackground(Color.WHITE);
        Dimension label_size1 = new Dimension(900, 39);
        button_panel.setPreferredSize(label_size1);
        button_panel.setMinimumSize(label_size1);
        button_panel.setMaximumSize(label_size1);
        JButton start_button =  null;
        JLabel our_name_label2 = null;
        if (port != -1) {
        	start_button = new JButton("Avvio");
        	start_button.setBackground(new Color(0, 0, 68)); 
	        Dimension labelSize2 = new Dimension(85, 35);
	        start_button.setPreferredSize(labelSize2);
	        start_button.setMinimumSize(labelSize2);
	        start_button.setMaximumSize(labelSize2);
	        start_button.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
	        start_button.setFocusPainted(false); 
	        start_button.setOpaque(true);
	        start_button.setForeground(Color.WHITE); 
	        start_button.setAlignmentX(Component.CENTER_ALIGNMENT); 
	        start_button.setBounds(158, 73, 100, 30);
	        start_button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	MultiServer multiserver = MultiServer.MultiServerOn(port);
		        	container.add(multiserver, "MultiServer");
			        cardLayout.show(container, "MultiServer");
	            }
	        });
	        button_panel.add(start_button);
        } else {
        	our_name_label2 = new JLabel(error);
        	our_name_label2.setForeground(Color.RED); 
        	our_name_label2.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 12));
        	our_name_label2.setAlignmentX(Component.CENTER_ALIGNMENT); 
        	button_panel.add(our_name_label2);
            
        }
        
        main_panel.add(button_panel);
        main_panel.add(Box.createRigidArea(new Dimension(0, 10)));
        container.add(main_panel, "MainPanel");
        
		setSize(900,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Server H-CLUS");
		setIconImage(logo.getImage());
		setResizable(false);
		setVisible(true);
	}
	
	/**
     * main
     * Punto di ingresso dell'applicazione lato Server
     * 
     * @param args argomenti passati da terminale
     */
	public static void main(String[] args) {
    	new Main(args);
    }
	
	 /**
     * Metodo validatePort
     * Si assicura che la porta fortina sia valida e gestisce i casi in cui l'input non è valido
     * 
     * @param args argomenti passati da terminale
     * @return ritorna il numero di porta se è valido altrimenti -1
     */
    private static int validatePort(String[] args) {
    	if (args.length == 0) {
    		error = "Il numero di porta non e' inserito";
            return -1;
        }

        int port;
        try {
            port = Integer.parseInt(args[0]);
            if (port < 0 || port > 65535) {
            	error = "Numero di porta " + args[0] + " non valido";
                return -1;
            }
            return port;
        } catch (NumberFormatException e) {
        	error = "Numero di porta " + args[0] + " non valido";
        	return -1;
        }
    }

}
