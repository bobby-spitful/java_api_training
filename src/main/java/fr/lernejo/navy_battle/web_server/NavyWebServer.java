package fr.lernejo.navy_battle.web_server;
import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.client.NavyClient;
import fr.lernejo.navy_battle.game.board.Ocean;
import fr.lernejo.navy_battle.game.boats.*;
import fr.lernejo.navy_battle.web_server.api.FireHandler;
import fr.lernejo.navy_battle.web_server.api.GameStartHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class NavyWebServer {

    protected final int givenPort;
    private final HttpServer myHttpServer;
    private final AtomicReference<NavyClient> myNavyClient = new AtomicReference<>();
    private final List<CallHandler> contexts = new ArrayList<>();
    private final Ocean myOcean = new Ocean();
    private final Ocean enemyOcean = new Ocean();

    public NavyWebServer( int port, NavyClient client ) throws IOException {
        this(port);
        this.getNavyClient().set(client);
    }

    public NavyWebServer( int port ) throws IOException {
        this.givenPort = port;
        this.myHttpServer = HttpServer.create( new InetSocketAddress( this.givenPort ), 0 );
        this.myHttpServer.setExecutor( Executors.newFixedThreadPool( 1 ) );
        this.contexts.add( new PingHandler() );
        this.contexts.add( new GameStartHandler( this ) );
        this.contexts.add( new FireHandler( this ) );
        this.setupContexts();
        this.myHttpServer.start();
        this.setupBoats();
    }

    public void createContext( String path, CallHandler handler ) { this.myHttpServer.createContext( path, handler ); }

    public void stop() { this.myHttpServer.stop( 0 ); }

    public Ocean getOcean() { return this.myOcean; }

    public Ocean getEnemyOcean() { return this.enemyOcean; }

    public int getPort() { return this.givenPort; }

    public AtomicReference<NavyClient> getNavyClient() { return this.myNavyClient; }

    public void setupContexts() {
        contexts.forEach(
            context -> this.createContext( context.getAssignedPath(), context )
        );
    }

    public void setupBoats() {
        HashSet<Boat> boats = new HashSet<>();
        boats.add( new Carrier( this.myOcean ) );
        boats.add( new Cruiser( this.myOcean ) );
        boats.add( new AntiTorpedo( this.myOcean ) );
        boats.add( new AntiTorpedo( this.myOcean ) );
        boats.add( new Torpedo( this.myOcean ) );
        this.myOcean.setupBoatsRandomly( boats );
    }
}
