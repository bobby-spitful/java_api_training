package fr.lernejo.navy_battle.web_server.api;
import com.sun.net.httpserver.HttpExchange;
import fr.lernejo.navy_battle.client.NavyClient;
import fr.lernejo.navy_battle.game.board.Position;
import fr.lernejo.navy_battle.transactions.JSONFire;
import fr.lernejo.navy_battle.web_server.CallHandler;
import fr.lernejo.navy_battle.web_server.NavyWebServer;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class FireHandler implements CallHandler {

    private final NavyWebServer source;

    public FireHandler( NavyWebServer source ) { this.source = source; }

    @Override
    public String getAssignedPath() { return "/api/game/fire"; }

    @Override
    public String[] allowedRequestMethods() { return new String[]{"GET"}; }

    @Override
    public boolean isMethodAllowed( String method ) {
        return Arrays.stream( this.allowedRequestMethods() ).toList().contains( method );
    }

    @Override
    public void handle( HttpExchange exchange ) throws IOException {
        int responseCode = 404;
        String body = "Not found";
        JSONFire consequence = null;
        if ( this.isMethodAllowed( exchange.getRequestMethod() ) ) {
            responseCode = 200;
            Position incomingFire = new Position( exchange.getRequestURI().getQuery().split( "=" )[1] ); // here we should only have cell=??
            consequence = this.source.getOcean().hit( incomingFire );
            body = consequence.getJSONString();
            exchange.getResponseHeaders().set( "Content-Type", "application/json" );
            System.out.println( "Fire received: " + incomingFire.getColLetter() + incomingFire.getRow() + " -> " + body );
        }
        exchange.sendResponseHeaders( responseCode, body.length() );
        try ( OutputStream os = exchange.getResponseBody() ) { os.write( body.getBytes() ); }
        assert consequence != null;
        if ( consequence.shipLeft() ) { this.source.getNavyClient().get().nextMove(); }
    }
}
