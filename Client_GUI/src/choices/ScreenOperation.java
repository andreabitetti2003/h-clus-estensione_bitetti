package choices;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.Box;

import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;

/**
 * Classe ScreenOperation
 * Modella una schermata iniziale con un'interfaccia grafica per l'avvio del client e 
 * visualizza eventuali messaggi di errore.
 * 
 * @author Gruppo A14
 */
class ScreenOperation extends JPanel  {
	
	/**
     * Costruttore
     * Crea la schermata iniziale dell'applicazione con titolo, logo, e un pulsante per iniziare
     * oppure un messaggio di errore se presente.
     * 
     * @param start_up Istanza della classe StartUp, utilizzata per avviare le varie operazioni dell'applicazione
     * @param error Stringa contenente un messaggio di errore da visualizzare (null se non ci sono errori)
     */
    ScreenOperation(StartUp start_up, String error) {
    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    	setBackground(Color.WHITE);
    	
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
        
        JLabel client_label = new JLabel("Client");
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
        
        add(Box.createVerticalGlue());
        add(logo_label); 
        add(title_label);
        add(Box.createRigidArea(new Dimension(0, -75)));
        add(title_label2);
        add(Box.createRigidArea(new Dimension(0, -10)));
        add(client_label);
        add(created_by_label);
        add(Box.createRigidArea(new Dimension(0, -10))); 
        add(our_name_label);
        add(Box.createVerticalGlue()); 
        
        if (StartUp.getSocket() != null) {
	        JLabel label_socket_info = new JLabel(StartUp.getSocket().toString());
	        label_socket_info.setForeground(new Color(0, 0, 64));
	        label_socket_info.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
	        label_socket_info.setAlignmentX(0.5f);
	        add(label_socket_info);
        }
        
        JButton start_button =  null;
        JLabel label_error = null;
        JPanel button_panel = new JPanel();
        button_panel.setLayout(new FlowLayout());
        button_panel.setBackground(Color.WHITE);
        Dimension label_size1 = new Dimension(900, 39);
        button_panel.setPreferredSize(label_size1);
        button_panel.setMinimumSize(label_size1);
        button_panel.setMaximumSize(label_size1);
        if (error == null) {
        	start_button = new JButton("Avvio");
	        Dimension label_size = new Dimension(85, 35);
	        start_button.setPreferredSize(label_size);
	        start_button.setMinimumSize(label_size);
	        start_button.setMaximumSize(label_size);
	        start_button.setBackground(new Color(0, 0, 68)); 
	        start_button.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 15));
	        start_button.setFocusPainted(false); 
	        start_button.setOpaque(true);
	        start_button.setForeground(Color.WHITE); 
	        start_button.setAlignmentX(Component.CENTER_ALIGNMENT); 
	        start_button.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	start_up.ShowTableInsertion();
	            }
	        });
	        button_panel.add(start_button);
	        
        } else {
        	label_error = new JLabel(error);
        	label_error.setForeground(Color.RED); 
        	label_error.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 12));
        	label_error.setAlignmentX(Component.CENTER_ALIGNMENT);
        	label_error.setHorizontalAlignment(SwingConstants.CENTER);
        	button_panel.add(label_error);
        }
        add(button_panel);
        add(Box.createRigidArea(new Dimension(0, 10)));
    }
}
