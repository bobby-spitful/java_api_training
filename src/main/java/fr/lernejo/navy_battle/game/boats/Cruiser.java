package fr.lernejo.navy_battle.game.boats;
import fr.lernejo.navy_battle.game.board.Ocean;

public class Cruiser extends Boat {

    public Cruiser( Ocean myOcean ) {
        super( myOcean );
        this.setSize( 4L );
    }
}
