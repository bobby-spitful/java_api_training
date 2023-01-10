package fr.lernejo.navy_battle.web_server;
import com.sun.net.httpserver.HttpHandler;

public interface CallHandler extends HttpHandler {
    String getAssignedPath();
    String[] allowedRequestMethods();
    boolean isMethodAllowed( String method );
}
