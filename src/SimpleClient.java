import java.net.*;
import java.io.*;

public class SimpleClient extends Thread {

    public void run() {
        try {
            Sleeper.nap(); // være sikker på at serveren er online

            Socket connection = new Socket( "127.0.0.1", 6010 );
            System.out.println( "[Client] Connection open" );

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream() ) );

            String line = "";
            while ( !line.equals( "<end>" ) ) {
                line = reader.readLine();

                System.out.println( "[Client] received line: " + line );
            }

            reader.close();
            System.out.println( "[Client] Connection closed" );
        }
        catch ( IOException e ) {
            System.out.println( "[Client] Connection failed" );
        }
    }
}