package fr.lernejo.navy_battle.game.boats;
import fr.lernejo.navy_battle.game.board.Cell;
import fr.lernejo.navy_battle.game.board.Ocean;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Boat {

    private final Ocean container;
    private final HashSet<Cell> position = new HashSet<>();
    private final AtomicReference<Long> size = new AtomicReference<>();

    public Boat( Ocean container ) { this.container = container; }

    public boolean isAlive() {
        boolean isAlive = false;
        for ( Cell cell: this.position ) {
            if( cell.getCurrentState().equals( Cell.states.OCCUPIED.toString() ) ) {
                isAlive = true;
                break;
            }
        }
        return isAlive;
    }

    public HashSet<Cell> getPosition() { return this.position; }

    public Long getSize() { return size.get(); }

    protected void setSize( Long size ) { this.size.set( size ); }

    public void setup( Cell startingCell, String direction ) {
        this.position.clear();
        switch ( direction ) {
            case "up" -> setupUp( startingCell );
            case "right" -> setupRight( startingCell );
            case "down" -> setupDown( startingCell );
            case "left" -> setupLeft( startingCell );
        }
        if ( Math.toIntExact( this.getSize() ) != this.position.size() )
            this.position.clear();
    }

    public void setupUp( Cell startingCell ) {
        Cell currentCell = startingCell;
        while ( this.position.size() != this.getSize() && currentCell.getPosition().up().getRow() > 0 ){
            this.position.add( currentCell );
            currentCell.setCurrentState( Cell.states.OCCUPIED.toString() );
            currentCell = container.getCell( currentCell.getPosition().up() );
        }
    }

    public void setupRight( Cell startingCell ) {
        Cell currentCell = startingCell;
        while ( this.position.size() != this.getSize() && currentCell.getPosition().right().getCol() < this.container.getBoard().length ){
            this.position.add( currentCell );
            currentCell.setCurrentState( Cell.states.OCCUPIED.toString() );
            currentCell = container.getCell( currentCell.getPosition().right() );
        }
    }

    public void setupDown( Cell startingCell ) {
        Cell currentCell = startingCell;
        while ( this.position.size() != this.getSize() && currentCell.getPosition().down().getRow() < this.container.getBoard()[0].length ){
            this.position.add( currentCell );
            currentCell.setCurrentState( Cell.states.OCCUPIED.toString() );
            currentCell = container.getCell( currentCell.getPosition().down() );
        }
    }

    public void setupLeft( Cell startingCell ) {
        Cell currentCell = startingCell;
        while ( this.position.size() != this.getSize() && currentCell.getPosition().left().getCol() > 0 ){
            this.position.add( currentCell );
            currentCell.setCurrentState( Cell.states.OCCUPIED.toString() );
            currentCell = container.getCell( currentCell.getPosition().left() );
        }
    }
}
