package fr.lernejo.navy_battle.game.boats;
import fr.lernejo.navy_battle.game.board.Ocean;

public class Torpedo extends Boat {

    public Torpedo( Ocean myOcean ) {
        super( myOcean );
        this.setSize( 2L );
    }
}
