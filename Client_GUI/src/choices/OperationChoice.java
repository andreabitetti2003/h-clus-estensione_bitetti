package choices;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

/**
 * Classe OperationChoice
 * Modella una GUI per consentire all'utente di scegliere tra il caricamento di un dendrogramma da file
 * o l'apprendimento di un dendrogramma da database.
 * 
 * @author Gruppo A14
 */
class OperationChoice extends JPanel {
	
	/**
     * Costruttore
     * Crea un pannello per l'interfaccia utente che consente all'utente di scegliere un'opzione e confermare la scelta.
     * 
     * @param start_up Istanza della classe StartUp, utilizzata per interagire con altre componenti dell'applicazione
     */
	OperationChoice(StartUp start_up) {
    	JTextArea txt_option = new JTextArea("Scegli una opzione");
		txt_option.setEditable(false);
		txt_option.setLocation(0, 0);
		txt_option.setSize(252, 20);
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(Color.WHITE);
		
		JPanel title_panel = start_up.getTitlePanel();
		
		JLabel table_label = new JLabel("Scegli una opzione");
		table_label.setForeground(new Color(0, 0, 160)); 
		table_label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 18));
		table_label.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JRadioButton button_load = new JRadioButton("Carica Dendrogramma da File");
		button_load.setFocusPainted(false); 
	    button_load.setSelected(true);
	    button_load.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 18));
	    button_load.setBackground(Color.WHITE);
	    
	    JRadioButton button_load_db = new JRadioButton("Apprendi Dendrogramma da Database");
	    button_load_db.setFocusPainted(false); 
	    button_load_db.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 18));
	    button_load_db.setBackground(Color.WHITE);
	    
	    ButtonGroup group = new ButtonGroup();
	    group.add(button_load);
	    group.add(button_load_db);
	    
	    JPanel combo_panel = new JPanel();
	    combo_panel.setLayout(new FlowLayout());
	    combo_panel.setBackground(Color.WHITE);
	    combo_panel.add(table_label);
	    combo_panel.add(button_load);
	    combo_panel.add(button_load_db); 
	    
	    JLabel error_label = new JLabel(" ");
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
				if(button_load.isSelected()) {
					if(start_up.getTableInsertion().getNumberFiles() == 0) {
						error_label.setText("Non ci sono file da caricare");
					} else {
						error_label.setText(" ");
						start_up.ShowLoadDendrogramFromFile();
					}
				} else {
					error_label.setText(" ");
					start_up.ShowMineDendrogram();
				}
				
			}
		});
	    
	    add(title_panel);
	    add(combo_panel);
	    add(error_label);
	    add(send_button);
	    add(Box.createRigidArea(new Dimension(0, 10)));
	}
}
