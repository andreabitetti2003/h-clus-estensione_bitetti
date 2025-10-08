# Manuale utente del progetto H-CLUS estensione
### Manuale redatto dal gruppo _A14_
---
---

### Indice
1. [Introduzione](#1-introduzione)
    - 1.1 [Struttura del progetto](#11-struttura-del-progetto)
    - 1.2 [Gruppo di lavoro](#12-gruppo-di-lavoro) 

2. [Guida di installazione (con Jar+ Bat+ Script SQL)](#2-guida-di-installazione)

3. [Come eseguire H-Clus](#3-come-eseguire-h-clus)
    - 3.1 [Esecuzione del server](#31-esecuzione-del-server)
    - 3.2 [Esecuzione del client](#32-esecuzione-del-client)

4. [Diagramma delle classi](#4-diagramma-delle-classi)

5. [JavaDoc](#5-javadoc)

6. [Casi di test](#6-casi-di-test)
---
---

## **1. Introduzione**
Il progetto svolto, denominato _H-CLUS_, è stato assegnato e sviluppato durante il corso di Metodi Avanzati di Programmazione.
Il concetto alla base del progetto è il _data mining_, ossia l’estrazione (semi) automatica di conoscenza nascosta in voluminose basi di dati al fine di renderla disponibile e direttamente utilizzabile.

Dati una collezione $D$ di transazioni (vettore di valori misurati per una collezione di attributi numerici) ed un intero $k$, partizionare $D$ in $k$ insiemi, tale che:
- $D_i$ (i = 1, …, k) è un segmento omogeneo di $D$
- $D$ =$\bigcup_{i}^{k}D_i$ e $D_i$∩$D_j$ = ∅

L'obiettivo è implementare una soluzione mediante un sistema Client-Server per il clustering gerarchico di valori interi.

---
### 1.1 Struttura del progetto
Il progetto è stato sviluppato in linguaggio _Java_ e si compone di due elementi principali:
- **Server**: funzionalità di data mining per la scoperta di un dendrogramma di cluster di dati con algoritmo di _clustering_ 
agglomerativo, attraverso richieste Client
- **Client**: applicativo che consente di usufruire del servizio di scoperta remoto e visualizzare la conoscenza (cluster) scoperta


---
### 1.2 Gruppo di lavoro
Il progetto è sviluppato dagli studenti:
- **Bitetti Andrea**
    - MATRICOLA: 778423
    - E-MAIL ISTUZIONALE: a.bitetti16@studenti.uniba.it
- **Franco Andrea**
    - MATRICOLA: 774248
    - E-MAIL ISTUZIONALE: a.franco56@studenti.uniba.it
- **Gatti Giovanni**
    - MATRICOLA: 778546
    - E-MAIL ISTUZIONALE: g.gatti15@studenti.uniba.it

---
---
## **2. Guida di installazione**
Affinchè sia possibile utilizzare il programma nella sua interezza è necessario installare alcuni componenti software:

1. **Java Development Kit (JDK):**

JDK è un insieme di strumenti necessari per sviluppare ed utilizzare applicazioni Java. Include il compilatore (per tradurre il codice sorgente Java in bytecode eseguibile dalla JVM - Java Virtual Machine), un ambiente di runtime (JRE - Java Runtime Environment) per eseguire il bytecode, e varie librerie standard. 
Questo strumento risulta essere indispensabile per scrivere, compilare e testare programmi Java.

- E' necessario installare JDK versione 17 o successiva, accedendo al sito ufficiale di Oracle e selezionando il download per il proprio sistema operativo: [scarica JDK](https://www.oracle.com/java/technologies/downloads/#java17)

2. **MySQL:**

MySQL è un sistema di gestione di database relazionali (DBMS) che serve per memorizzare, organizzare e gestire grandi quantità di dati. Utilizza il linguaggio SQL (Structured Query Language) per eseguire operazioni come inserimenti, aggiornamenti, eliminazioni e query sui dati.

- E' necessario installare MySQL versione 8.0.39 o successiva, accedendo al sito ufficiale di  MySQL Community Server e selezionando il download per il proprio sistema operativo: [scarica MySQL](https://dev.mysql.com/downloads/mysql/)

Successivamente effettuare le seguenti operazioni, affinchè l'installazione di MySQL avvenga in maniera corretta:
    
- Dalla barra di ricerca di Windows cercare "variabili d'ambiente".    
- Selezionare `Modifica le variabili d'ambiente relative al sistema`.
- Nella finestra `Avanzate`, cliccare su `Variabili d'ambiente...`.
- Nella sezione `Variabili di sistema`, selezionare la variabile `Path`, quindi cliccare su `Modifica...`.
- Cliccare su `Nuovo`, `Sfoglia file...` e aggiungere il percorso della cartella 'bin' (si trova nella cartella di installazione di MySQL).
- Cliccare su `OK`per chiudere tutte le finestre aperte.

Per verificare la corretta installazione di MySQL, aprire il prompt dei comandi. Digitare `mysql --version` e premere `Invio`.
Se le operazioni descritte precedentemente sono state effettuate correttamente, verrà mostrata la versione di MySQL.

**N.B.** E' importante memorizzare le proprie informazioni d'accesso all'account MySQL (_username_ e _password_), poichè saranno necessarie per avviare il Server.

---
---

## **3. Come eseguire H-Clus**
E' possibile avviare un unico _Server_. Il sistema impedirà di aprirne più di uno contemporaneamente. Inoltre è assolutamente fondamentale chiudere la pagina del Server quando si termina di eseguire le operazioni. E' possibile avviare più _Client_ contemporaneamente.

Per avviare Server e Client sono stati definiti i file batch `Server_GUI.bat` e `Client_GUI.bat`.
Un file batch è un file di testo che contiene una serie di comandi eseguibili da un interprete di comandi, come il prompt dei comandi di Windows. Questi file vengono utilizzati per automatizzare attività ripetitive, eseguendo una sequenza di comandi in modo sequenziale quando il file viene eseguito.
Pertanto è necessario avviare tali file per eseguire il codice dei rispettivi elementi del progetto (Server e Client).

### **3.1 Esecuzione del server**
Il file `Server_GUI.bat` avvia il Server sulla porta _8080_.
Il valore _8080_ è stato definito in fase di programmazione, ma è possibile utilizzare qualsiasi altra porta (es: qualora la porta 8080 sia già utilizzata) purchè non sia attivo qualsiasi servizio su essa.
Qualora si volesse modificare il valore, aprire e modificare il file `Server_GUI.bat` sostituendo il valore _8080_ con il nuovo valore.

E' possibile eseguire il file in due modi differenti:
1. Individuare il file `Server_GUI.bat` nella directory del progetto e fare _doppio clic_ su esso.

2. Aprire il prompt dei comandi od un altro terminale:
    
    E' possibile farlo in due modi:
    - Digitare _Prompt dei comandi_ oppure _cmd_ nel menu _Start_
    - Eseguire la combinazione di tasti _Windows + R_ e digitare cmd, infine premere _OK_.
    
    Navigare fino alla directory dove si trova il file. Eseguire il comando: `Server_GUI.bat`.

Nel prompt dei comandi, inserire la password del proprio account MySQL. Qualora l'utente inserisca la password sbagliata, è necessario rieseguire il file `Server_GUI.bat`.
Solo dopo aver inserito la password, il server sarà attivo.

Verrà aperta l'applicazione lato server. E' necessario attivare il server: premere il tasto _Avvio_.
Successivamente verrano mostrati:
   - numero di porta, sul quale è connesso il Server
   - elenco dei client connessi e disconnessi


INFORMAZIONI SUL FILE .batch:

In generale, il file batch eseguirà il file `setup.sql` ed il file JAR del Server con la porta specificata.

Il file `setup.sql` contiene le istruzioni SQL per generare il database _MapDB_ e la tabella _exampleTab_, necessari per generare il dendogramma. E' possibile modificare il file, aggiungere nuove tabelle o ulteriori tuple nella tabella esistente.


---
### **3.2 Esecuzione del client**
Il file `Client_GUI.bat` avvia il Client e necessita la definizione dell'indirizzo IP del dispositivo e della porta.
Di default sono stati impostati i valori _127.0.0.1_ ed _8080_ rispettivamente. Entrambi i valori possono essere modificati dall'utente, qualora ne abbia bisogno.

RICORDA: sulla porta che andrai a specificare non deve essere attivo qualsiasi altro servizio.
Qualora si volesse modificare i valori, aprire e modificare il file `Client_GUI.bat`.

E' possibile eseguire il file in due modi differenti:
1. Individuare il file `Client_GUI.bat` nella directory del progetto e fare _doppio clic_ su esso.

2. Aprire il prompt dei comandi od un altro terminale:
    - Digitare _Prompt dei comandi_ oppure _cmd_ nel menu _Start_
    - Eseguire la combinazione di tasti _Windows + R_ e digitare cmd, infine premere _OK_.
    
    Navigare fino alla directory dove si trova il file. Eseguire il comando: `Client_GUI.bat`.

In entrambi le modalità, il file batch eseguirà il file JAR del Client con l'indirizzo e la porta specificata.

Verrà aperta l'applicazione lato client. E' necessario attivare il client: premere il tasto _Avvio_.
Successivamente sarà possibile effettuare le stesse operazioni dell'applicazione a riga di comando, ma utilizzando una grafica user-friendly.


---
---
## **4. Diagramma delle classi**
Tutti i diagrammi delle classi sono stati creati utilizzando UML (Unified Modeling Language) per garantire una rappresentazione standardizzata delle strutture e delle relazioni tra le varie classi. Per questo processo, abbiamo scelto di utilizzare StarUML, un software di modellazione che facilita la creazione, la visualizzazione e la gestione di diagrammi UML.

### Diagramma CLIENT completo
---
<p style="text-align: center;">
    <img src="img/UML_client.jpeg">
</p>

### Diagramma DEFAULT PACKAGE del CLIENT
---
<p style="text-align: center;">
    <img src="img/UML_client_default_package.jpeg">
</p>

### Diagramma PACKAGE CHOICES del CLIENT
---
<p style="text-align: center;">
    <img src="img/UML_client_choices.jpeg">
</p>

### Diagramma PACKAGE DENDROGRAM del CLIENT
---
<p style="text-align: center;">
    <img src="img/UML_client_dendrogram.jpeg">
</p>

### Diagramma SERVER completo
---
<p style="text-align: center;">
    <img src="img/UML_server.jpeg">
</p>

### Diagramma DEFAULT PACKAGE del SERVER
---
<p style="text-align: center;">
    <img src="img/UML_server_default_package.jpeg">
</p>

### Diagramma PACKAGE CLUSTERING del SERVER
---
<p style="text-align: center;">
    <img src="img/UML_server_clustering.jpeg">
</p>

### Diagramma PACKAGE DISTANCE del SERVER
---
<p style="text-align: center;">
    <img src="img/UML_server_distance.jpeg">
</p>

### Diagramma PACKAGE DATA del SERVER
---
<p style="text-align: center;">
    <img src="img/UML_server_data.jpeg">
</p>

### Diagramma PACKAGE DATABASE del SERVER
---
<p style="text-align: center;">
    <img src="img/UML_server_database.jpeg">
</p>

### Diagramma PACKAGE SERVER del SERVER
---
<p style="text-align: center;">
    <img src="img/UML_server_server.jpeg">
</p>


---
---

## **5. JavaDoc**
Javadoc è uno strumento di documentazione utilizzato per generare automaticamente la documentazione API in formato HTML per il codice sorgente Java. Viene incluso nel Java Development Kit e si basa su commenti speciali inseriti nel codice sorgente. 

Per visualizzare il Javadoc del client è possibile aprire il file `index.html` presente nella cartella _./Client_GUI/javadocClient_ .

Per visualizzare il Javadoc del server è possibile  aprire il file `index.html` presente nella cartella _./Server_GUI/javadocServer_ .

---
---
## **6. Casi di test**

### Casi di test black-box
Sono stati eseguiti test di sistema utilizzando l'approccio black-box. Questo metodo ci ha permesso di valutare il comportamento del sistema nel suo insieme, concentrandoci sulle funzionalità e sui requisiti senza valutare i dettagli dell'implementazione del codice. Quindi, questa sezione analizza aspetti lato client.
Di seguito vengono elencati i test effettuati ed i risultati ottenuti.

---
### Schermata di avvio server
Test di verifica sulla corretta visualizzazione della schermata d'avvio del server:

![Test schermata d'avvio server](img/test_avvio_server.jpeg)

![Test schermata d'avvio server](img/test_avvio_server_2.jpeg)

La schermata di avvio mostra l'elenco dei client connessi.
In particolare, per ogni client è possibile visualizzare se è connesso o disconnesso e le sue informazioni.

---
### Schermata di avvio client
Test di verifica sulla corretta visualizzazione della schermata d'avvio del client:

![Test schermata d'avvio client](img/test_avvio_client.jpeg)

La schermata di avvio mostra l'indirizzo ip del client ed i dati del socket creato durante la connessione al Server.
Per effettuare le operazioni è necessario premere il bottone 'Avvio'. 

---
### Inserimento nome tabella corretto
Test per l'inserimento di un nome corretto di una tabella:

![Test tabella corretta](img/test_tabella_corretta.jpeg)

---
### Inserimento nome tabella errato
Test per l'inserimento del nome di una tabella con attributi non numerici:

![Test tabella con attributi non numerici](img/test_tabella_non_numerici.jpeg)

Test per l'inserimento del nome di una tabella vuota:

![Test tabella vuota](img/test_tabella_vuota.jpeg)

In entrambi i casi è possibile selezionare un'altra tabella.

---
### Scelta 1 
Test per la scelta dell'operazione. L'utente sceglie il caricamento del dendrogramma da file: 

![Test scelta 1](img/test_opzione_1.jpeg) 

---
### Scelta 2
Test per la scelta dell'operazione. L'utente sceglie di apprendere il dendrogramma da database:

![Test scelta 2](img/test_opzione_2.jpeg) 

---
### Scelta 1: Caricamento da file corretto
Test per il caricamento di un dendrogramma da file binario. L'utente seleziona un file corretto.

![Test carica file binario](img/test_carica_file_binario_1.jpeg)

![Test carica file binario](img/test_carica_file_binario_2.jpeg)

Test per il caricamento di un dendrogramma da file generico di dati. L'utente seleziona un file corretto.

![Test carica file .dat](img/test_carica_file_dat.jpeg)

Test per il caricamento di un dendrogramma molto grande:

![Test carica tabella grande](img/test_carica_tabella_grande_1.jpeg)

![Test carica tabella grande](img/test_carica_tabella_grande_2.jpeg)

In questi casi, e per tutti i file con estensione accettata, il programma stamperà il dendrogramma.

---
### Scelta 1: Caricamento da file errato
Test per il caricamento di un dendrogramma da file vuoto:

![Test carica file vuoto](img/test_carica_file_vuoto.jpeg)

Test per il caricamento di un dendrogramma (salvato su file) la cui profondità è maggiore del numero di tuple della tabella selezionata precedentemente:

![Test carica file diverso](img/test_carica_file_diverso.jpeg)

In entrambi i casi, l'utente ha la possibilità di selezionare un altro file.

---
### Scelta 2: Scelta profondità
Test per l'inserimento della profondità durante l'apprendimento del dendrogramma da database:

![Test profondità corretta](img/test_profondita_corretta.jpeg)

---
### Scelta 2: Scelta tipologia algoritmo  
Test per la scelta di algoritmo Single-Link da utilizzare durante l'apprendimento del dendrogramma da database:

![Test distanza 1](img/test_distanza_1.jpeg)

Test per la scelta di algoritmo Average-Link da utilizzare durante l'apprendimento del dendrogramma da database:

![Test distanza 2](img/test_distanza_2.jpeg)

In entrambi i casi verrà mostrato a video il dendrogramma risultante e sarà possibile salvarlo su file.

---
### Scelta 2: Test per inserimento nome file
Test per l'inserimento del nome corretto di un file su cui salvare dendrogramma:

![Test salva file corretto](img/test_salva_file_corretto_1.jpeg) 

![Test salva file corretto](img/test_salva_file_corretto_2.jpeg) 

Test per l'inserimento del nome corretto di un file su cui verrà salvato un dendrogramma grande:

![Test salva tabella grande](img/test_salva_tabella_grande_1.jpeg) 

![Test salva tabella grande](img/test_salva_tabella_grande_2.jpeg)

Il dendrogramma viene salvato nel file specificato.

---
### Scelta 2: Test per inserimento nome file errato
Test per l'inserimento del nome di un file esistente su cui è già stato salvato un dendrogramma:

![Test salva file esistente](img/test_salva_file_esistente_1.jpeg) 
...

![Test salva file esistente](img/test_salva_file_esistente_2.jpeg) 

Test per l'inserimento del nome di un file con estensione errata:

![Test salva file estensione errata](img/test_salva_file_estensione_errata_1.jpeg)

![Test salva file estensione errata](img/test_salva_file_estensione_errata_2.jpeg)

Test per l'inserimento del nome di un file senza estensione:

![Test salva file no estensione](img/test_salva_file_no_estensione_1.jpeg)

![Test salva file no estensione](img/test_salva_file_no_estensione_2.jpeg)

In ogni caso l'utente può reinserire il nome di un file.

---
---

### Casi di test white-box
I test di sistema condotti utilizzando l'approccio white-box si concentrano sulla verifica delle funzionalità del sistema tenendo conto della sua struttura interna, del codice sorgente e della logica di funzionamento.
Di seguito vengono elencati i test effettuati ed i risultati ottenuti.

---
### Test di avvio del Client con Server spento
Test di avvio del Client tenendo appositamente il Server spento:

![Test avvio Client con Server spento](img/test_avvio_client_server_spento.jpeg) 

---
### Test di avvenuta connessione al Server
Test di verifica della connessione, e successiva disconnessione, di un Client al Server:

![Test avvio Client con Server acceso](img/test_avvio_server_avvio_client.jpeg) 

---
### Test di avvio del Server su porta errata
Test di avvio del Server su un numero di porta errato:

![Test avvio server porta errata](img/test_avvio_server_porta_errata.jpeg)

---
### Test di avvio del Server su porta utilizzata
Test di avvio del Server su porta utilizzata da un altro servizio:

![Test avvio server porta utilizzata](img/test_avvio_server_porta_utilizzata.jpeg)

---
### Test di avvio del Client su un database senza tabelle
Errore nella ricerca delle tabelle nel database:

![Test tabella vuota](img/test_database_vuoto.jpeg)

---
### Test di caricamento da  file con cartella vuota
Errore nel caricamento di un dendrogramma da file con cartella dei file salvati vuota:

![Test cartella file vuota](img/test_cartella_file_vuota.jpeg)

---
---