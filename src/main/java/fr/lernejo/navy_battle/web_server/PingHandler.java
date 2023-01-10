package fr.lernejo.navy_battle.web_server;
import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class PingHandler implements CallHandler {

    @Override
    public String getAssignedPath() { return "/ping"; }

    @Override
    public String[] allowedRequestMethods() { return new String[]{"GET"}; }

    @Override
    public boolean isMethodAllowed( String method ) { return Arrays.stream( this.allowedRequestMethods() ).toList().contains( method ); }

    @Override
    public void handle( HttpExchange exchange ) throws IOException {
        int responseCode = 404;
        String body = "Not found";
        if ( this.isMethodAllowed( exchange.getRequestMethod() ) ) {
            responseCode = 200;
            body = "OK";
        }
        exchange.sendResponseHeaders( responseCode, body.length() );
        try ( OutputStream os = exchange.getResponseBody() ) { os.write( body.getBytes() ); }
    }
}
