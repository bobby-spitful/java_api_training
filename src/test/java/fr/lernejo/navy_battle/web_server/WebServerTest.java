package fr.lernejo.navy_battle.web_server;

import fr.lernejo.navy_battle.client.NavyClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpResponse;

class WebServerTest {

    PingHandler myTestPingHandler = new PingHandler();
    NavyWebServer myTestNavyWebServer;
    NavyClient myTestHttpClient;
    int testPort = 11000;

    @BeforeEach
    void setup_web_server() throws IOException {
        myTestNavyWebServer = new NavyWebServer(testPort);
    }
    @BeforeEach
    void setup_client() throws IOException {
        myTestHttpClient = new NavyClient((testPort+1), "http://localhost:" + testPort );
    }

    @AfterEach
    void close_web_servers() {
        myTestNavyWebServer.stop();
        myTestHttpClient.stop();
    }

    @AfterEach
    void increment_test_port() {
        // Leave a 10 port range for test purposes
        testPort += 10;
    }

    @Test
    void ping_assigned_path_should_be_ping() {
        Assertions.assertEquals("/ping", myTestPingHandler.getAssignedPath());
    }

    @Test
    void ping_should_return_status_code_200_and_body_OK() {
        myTestNavyWebServer.createContext( myTestPingHandler.getAssignedPath(), myTestPingHandler );
        HttpResponse<String> response = myTestHttpClient.ping();
        Assertions.assertEquals( 200, response.statusCode() );
        Assertions.assertEquals( "OK", response.body() );
    }

    @Test
    void setupContexts_should_setup_all_contexts() {
        myTestNavyWebServer.setupContexts();
        HttpResponse<String> response = myTestHttpClient.ping();
        Assertions.assertEquals( 200, response.statusCode() );
        Assertions.assertEquals( "OK", response.body() );
        //Add content to check that all contexts are set
    }

    @Test
    void unknown_context_should_return_status_code_404() {
        myTestNavyWebServer.setupContexts();
        HttpResponse<String> response = myTestHttpClient.sendGETRequest( "/unknown/path" );
        Assertions.assertEquals( 404, response.statusCode() );
    }

    @Test
    void sending_request_to_dead_server_should_not_throw() {
        myTestNavyWebServer.stop();
        Assertions.assertDoesNotThrow( () -> {
            myTestHttpClient.sendGETRequest( "/ping" );
        });
    }

    @Test
    void sending_request_to_dead_server_should_return_null() {
        myTestNavyWebServer.stop();
        Assertions.assertNull( myTestHttpClient.sendGETRequest( "/ping" ));
    }
}
