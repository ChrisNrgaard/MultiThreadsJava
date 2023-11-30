public class Main {

    public static void main( String[] argv ) {
        ClientManager manager = new ClientManager( 6010 );
        /* Her oprettes en instans af ClientManager-klassen med portnummeret 6010 som argument. Dette er den server, der vil lytte efter klientforbindelser. */

        manager.start();
        /* Dette starter ClientManager-tråden, så den begynder at lytte efter klientforbindelser på den specificerede port. */

        new SimpleClient().start();
        /* Dette opretter en ny instans af SimpleClient-klassen og starter den som en tråd. Dette repræsenterer en klient, der forsøger at oprette forbindelse til serveren på port 6010. */

        Sleeper.sleep( 3 );
        /* Dette kalder Sleeper.sleep(3), hvilket sandsynligvis er en funktion, der introducerer en 3-sekunders forsinkelse i programmet. Dette giver tid til, at klienten og serveren kan interagere og kommunikere. */

        manager.shutdown();
        /* Dette kalder shutdown()-metoden på ClientManager-objektet, hvilket vil stoppe serveren. Dette er en måde at afslutte programmet på, når interaktionen mellem klienten og serveren er færdig. */
    }
}