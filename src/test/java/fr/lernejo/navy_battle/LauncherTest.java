package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.web_server.NavyWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LauncherTest {

    // increment testPort between each step to avoid unwanted errors
    private static int testPort = 10000;

    @AfterEach
    public void increment_testPort() {
        testPort++;
    }

    @Test
    public void main_with_wrong_port_arg_should_throw() {
        Assertions.assertThrows( Exception.class, () -> Launcher.main(new String[] {"123456789"}));
    }

    @Test
    public void main_with_wrong_type_port_arg_should_throw() {
        Assertions.assertThrows( Exception.class, () -> Launcher.main(new String[] {"thisIsNotAPort"}));
    }

    @Test
    void launching_with_already_bound_port_should_throw_IOException() throws IOException {
        new NavyWebServer(testPort);
        Assertions.assertThrows( IOException.class, () -> Launcher.main(new String[] { Integer.toString(testPort) }));
    }

    @Test
    public void main_should_not_throw_when_giving_correct_port() {
        Assertions.assertDoesNotThrow( () -> Launcher.main(new String[] { Integer.toString(testPort) }));
    }

    @Test
    public void main_should_not_throw_when_giving_correct_port_and_address() {
        Assertions.assertDoesNotThrow( () -> Launcher.main(new String[] { Integer.toString(testPort++) }));
        Assertions.assertDoesNotThrow( () -> Launcher.main(new String[] { Integer.toString(testPort), "http://localhost:"+(testPort-1) }));
    }

    @Test
    public void main_should_not_throw_otherwise() {
        Assertions.assertDoesNotThrow( () -> Launcher.main(new String[] {}));
    }
}
