package server;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import java.net.Socket;
import java.net.ServerSocket;

import java.io.IOException;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
/**
 * Classe MultiServer
 * Creazione del socket relativo al server e gestione delle richieste client.
 * Implementa l'interfaccia grafica per visualizzare lo stato del server e le connessioni client.
 * 
 * Per ogni richiesta da parte di un nuovo client viene creata un'istanza di ServerOneClient,
 * gestendo la comunicazione su un Thread separato.
 * 
 * @author Gruppo A14
 */
public class MultiServer extends JPanel{
	/** Istanza di Serversocket, socket relativo al server */
    private static ServerSocket s;
    /** Booleano che verifica se il server è stato attivato */
    private static boolean serverOn = false;
    /** Numero di porta del server per la connessione */
    private final int PORT;
    /** Elemento per la stampa della porta su cui è connesso il server*/
    private JLabel txt_status;
    /** Elemento per la stampa delle connessioni client */
    private JTextArea txt_log;
    
    /**
	 * Costruttore
	 * Crea il socket relativo al server e gestisce le richieste client
	 * Per ogni richiesta da parte di un nuovo client viene creata un'istanza di ServerOneClient
	 * gestendo la comunicazione su un Thread separato
	 * 
	 * @param port numero di porta per la connessione
	 */
    private MultiServer(int port) {
    	serverOn = true;
        this.PORT = port;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        
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
        
        JLabel server_label = new JLabel("Server");
        server_label.setBackground(new Color(192, 192, 192));
        server_label.setForeground(new Color(0, 0, 128)); 
        server_label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 23));
        server_label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        txt_status = new JLabel("");
        txt_status.setBackground(new Color(192, 192, 192));
        txt_status.setForeground(new Color(0, 0, 0)); 
        txt_status.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 18));
        txt_status.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        txt_log = new JTextArea("");
        txt_log.setForeground(new Color(0, 0, 64)); 
        txt_log.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 12));
        txt_log.setEditable(false);
        txt_log.setAlignmentX(Component.CENTER_ALIGNMENT);
        JScrollPane scroll_pane = new JScrollPane(txt_log);
        scroll_pane.getViewport().setBackground(Color.WHITE);
        scroll_pane.getVerticalScrollBar().setBackground(Color.WHITE); 
        scroll_pane.getHorizontalScrollBar().setBackground(Color.WHITE); 
        
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(logo_label);
        add(title_label);
        add(Box.createRigidArea(new Dimension(0, -60)));
        add(title_label2);
        add(server_label);
        add(Box.createRigidArea(new Dimension(0, 10))); 
        add(txt_status);
        add(Box.createRigidArea(new Dimension(0, 10))); 
        add(scroll_pane);
        
        startServer(); 	
    }
    
    /**
     * Metodo startServer
     * Avvia il server sulla porta specificata nel costruttore.
     * Il server accetta connessioni client e le gestisce su thread separati.
     * 
     */
    private void startServer() {
        Thread server_thread = new Thread(() -> {
            try {
            	s = new ServerSocket(PORT);
                updateStatus("Server avviato sulla porta " + PORT);
                while (true) {
                    Socket socket = s.accept();
                    addMessage("Inizializzo connessione al client: " + socket);
                    try {
                        new ServerOneClient(socket, MultiServer.this);
                    } catch (IOException e) {
                        addMessage("Errore durante la creazione del socket: " + socket);
                        socket.close();
                    }
                }
            } catch (IOException e) {
                addMessage("Errore durante l'avvio del server");
            } finally {
                try {
                    if (s != null && !s.isClosed()) {
                        s.close();
                    }
                } catch (IOException e) {
                   addMessage("Errore durante la chiusura del server");
                }
            }
        });

        server_thread.start();
    }
    
    /**
     * Metodo MultiServerOn
     * Verifica se il server è attivo. Se non lo è, crea una nuova istanza del server e lo avvia.
     * Viene utilizzato per garantire che venga creata una sola istanza di ServerSocket.
     *
     * @param port numero di porta sulla quale avviare il server
     * @return una nuova istanza di MultiServer o null se il server è già attivo
     */
    public static MultiServer MultiServerOn(int port) {
        if (!serverOn) 
            return new MultiServer(port);
        else 
        	return null;
    }
    
    /**
     * Metodo addMessage
     * Aggiunge un messaggio al log delle connessioni client.
     * 
     * @param message messaggio da aggiungere
     */
    public void addMessage(String message) {
        SwingUtilities.invokeLater(() -> txt_log.append(message + "\n"));
    }

    /**
     * Metodo updateStatus
     * Aggiorna lo stato del server visualizzato nell'interfaccia grafica.
     * 
     * @param status nuovo stato del server da visualizzare
     */
    public void updateStatus(String status) {
        SwingUtilities.invokeLater(() -> txt_status.setText(status));
    }
}