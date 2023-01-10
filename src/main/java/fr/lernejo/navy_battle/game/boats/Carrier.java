package fr.lernejo.navy_battle.game.boats;
import fr.lernejo.navy_battle.game.board.Ocean;

public class Carrier extends Boat {

    public Carrier( Ocean myOcean ) {
        super( myOcean );
        this.setSize( 5L );
    }
}
