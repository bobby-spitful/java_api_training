package fr.lernejo.navy_battle;
import fr.lernejo.navy_battle.client.NavyClient;
import fr.lernejo.navy_battle.web_server.NavyWebServer;
import java.io.IOException;

public class Launcher {
    public static void main( String[] args ) throws IOException {
        if ( args.length == 1 ) {
            int givenPort = Integer.parseInt( args[0] );
            new NavyWebServer( givenPort );
        } else if ( args.length == 2 ) {
            int givenPort = Integer.parseInt( args[0] );
            String targetURL = args[1];
            NavyClient myNavyClient = new NavyClient( givenPort, targetURL );
            myNavyClient.engage();
        } else {
            System.out.println( "You launched the server without providing a port." );
        }
    }
}
