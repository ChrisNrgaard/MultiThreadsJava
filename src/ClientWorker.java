import java.net.*;
import java.io.*;

class ClientWorker extends Thread {
    /* Dette er definitionen af klassen ClientWorker, der udvider Thread-klassen. Ved at udvide Thread kan denne klasse fungere som en tråd, der kan køre asynkront.
    Klassen vil blive brugt til at håndtere en individuel klientforbindelse. */
    private Socket connection;
    /* Dette er en privat instansvariabel connection, der indeholder en reference til Socket-objektet, der bruges til at kommunikere med klienten. */
    private ClientManager manager;
    /* Dette er en privat instansvariabel manager, der indeholder en reference til ClientManager-objektet. Dette giver ClientWorker-tråden mulighed for at kommunikere med den overordnede ClientManager og informere den om, at en klientforbindelse er blevet behandlet. */

    public ClientWorker( ClientManager manager, Socket connection ) {
        /* Dette er konstruktøren for ClientWorker-klassen. Den tager en reference til ClientManager og Socket-objektet som argumenter og initialiserer de tilsvarende instansvariabler. */
        this.manager = manager;
        this.connection = connection;
    }

    public void run() {
        /* Dette er metoden, der udføres, når ClientWorker-tråden startes. Her kommunikerer tråden med klienten ved at sende beskeder til klienten og lukke forbindelsen. */
        System.out.println( "[ClientWorker] New connection from: " +
                connection.getInetAddress().getHostAddress() );
        /* Dette udskriver en besked i konsollen for at angive, at der er oprettet en ny forbindelse fra en bestemt klient ved at bruge getInetAddress().getHostAddress() til at få værtsadressen. */

        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(
                            connection.getOutputStream() ) );
            /* Dette opretter en BufferedWriter, der bruges til at skrive data til klienten. Den bruger connection.getOutputStream() til at få adgang til outputstrømmen til klienten. */

            sendLine( writer, "You are connected" );
            /* Dette kalder sendLine()-metoden for at sende en besked til klienten. */
            sendLine( writer, "to the server" );
            sendLine( writer, "<end>" );

            writer.close();
            /* Dette lukker BufferedWriter-objektet og dermed forbindelsen til klienten. */
            System.out.println( "[ClientWorker] Connection closed" );
            /* Dette udskriver en besked i konsollen for at angive, at forbindelsen til klienten er blevet lukket. */
        }
        catch ( IOException e ) {
            /* Dette er en blok, der fanger eventuelle I/O-fejl, der kan opstå under kørslen, og udskriver en fejlmeddelelse, hvis det sker. */
            System.out.println( "[ClientWorker] I/O error" );
        }
    }

    private void sendLine( BufferedWriter writer, String line )
            /* Dette er en hjælpefunktion, der bruges til at sende en linjebesked til klienten. */
            throws IOException
    {
        System.out.println( "[ClientWorker] sending line: " + line );
        /* Dette udskriver beskeden, der skal sendes til klienten, til konsollen. */

        writer.write( line );
        /* Dette skriver linjen til klientens outputstrøm. */

        writer.newLine();
        /* Dette tilføjer en ny linje efter linjen, da det er almindeligt i tekstbaseret kommunikation. */

        writer.flush();
        /* Dette sørger for, at alle data sendes til klienten ved at tømme bufferen. */

        Sleeper.nap();
        /* Dette kan introducere en kort forsinkelse mellem beskederne ved at kalde en funktion ved navn nap(). Denne forsinkelse kan bruges til at simulere en mere realistisk datatransmissionshastighed. */
    }
}