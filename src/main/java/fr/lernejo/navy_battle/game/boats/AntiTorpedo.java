package fr.lernejo.navy_battle.game.boats;
import fr.lernejo.navy_battle.game.board.Ocean;

public class AntiTorpedo extends Boat {

    public AntiTorpedo( Ocean myOcean ) {
        super( myOcean );
        this.setSize( 3L );
    }
}
