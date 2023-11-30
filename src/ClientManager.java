import java.net.*;
import java.io.*;
import java.util.LinkedList;

class ClientManager extends Thread {
    /* Dette er definitionen af klassen ClientManager, der udvider Thread-klassen.
    Ved at udvide Thread kan denne klasse fungere som en tråd, der kan køre asynkront.
    Klassen vil blive brugt til at administrere klientforbindelser. */
    private LinkedList workers;
    /* Dette er en privat instansvariabel workers, der bruges til at gemme en liste over arbejdertråde.
    Hver arbejdertråd håndterer en individuel klientforbindelse. */
    private int port;
    /* Dette er en privat instansvariabel port, der indeholder den port,
    serveren skal lytte på for klientforbindelser. */
    private boolean stop;
    /* Dette er en privat instansvariabel stop, der bruges til at styre serverens levetid.
    Når stop er sand, vil serveren stoppe. */

    public ClientManager( int port ) {
        /* Dette er konstruktøren for ClientManager-klassen.
        Den tager en port som argument og initialiserer instansvariablerne port og stop. */

        this.port = port;
        this.stop = false;

        workers = new LinkedList();
    }

    public void run() {
        /* Dette er metoden, der udføres, når ClientManager-tråden startes. Her oprettes en ServerSocket, der lytter på den specificerede port, og den venter på klientforbindelser.
        Når en klientforbindelse accepteres, oprettes en ClientWorker-tråd til at håndtere klienten. */

        ServerSocket server;
        /* Dette er en reference til ServerSocket-objektet, der bruges til at lytte efter klientforbindelser. */

        Socket connection;
        /* Dette er en reference til Socket-objektet, der bruges til at håndtere en individuel klientforbindelse. */

        try {
            server = new ServerSocket( port, 100 );
            /* Her oprettes en ServerSocket med den specificerede port (f.eks. 100) og en maksimal ventetid på 100 ms for acceptering af klientforbindelser.
            Dette betyder, at serveren venter i højst 100 ms på en klientforbindelse, inden den fortsætter med at køre. */

            server.setSoTimeout( 100 );
            /* Dette indstiller en timeout-værdi på 100 ms for ServerSocket.
            Hvis der ikke kommer nogen klientforbindelse inden for denne tid, kastes en
            SocketTimeoutException, som fanges for at fortsætte med at acceptere forbindelser. */

            System.out.println( "[ClientManager] Server online" );

            while ( !stop )
                /* Dette er en løkke, der kører, indtil stop-variablen bliver sand. Inden i løkken forsøger serveren at acceptere klientforbindelser ved hjælp af server.accept().
                Når en forbindelse accepteres, oprettes en ClientWorker-tråd til at håndtere klienten, og tråden tilføjes til listen over workers. */
                try {
                    connection = server.accept();

                    ClientWorker worker = new ClientWorker( this, connection );
                    workers.add( worker );
                    worker.start();
                }
                catch ( SocketTimeoutException e ) {
                    // ignore timeout
                }

            System.out.println( "[ClientManager] Server offline" );
        }
        catch ( IOException e ) {
            /* Dette fanger en SocketTimeoutException, der kan kastes, hvis der ikke kommer nogen klientforbindelse inden for timeout-perioden.
            Dette giver serveren mulighed for at tjekke, om stop-variablen er blevet sand for at beslutte, om den skal fortsætte med at køre. */
            System.out.println( "[ClientManager] I/O error" );
        }
    }

    public void shutdown() {
        /* Dette er en metode, der bruges til at stoppe serveren. Når denne metode kaldes, indstilles stop-variablen til sand,
        hvilket får serveren til at afslutte, når den næste gang tjekker betingelsen i løkken. */
        stop = true;
    }
}