package fr.lernejo.navy_battle.game.board;
import fr.lernejo.navy_battle.game.boats.Boat;
import fr.lernejo.navy_battle.transactions.JSONFire;
import java.util.*;
import java.util.stream.Collectors;

public class Ocean {

    private final Cell[][] board = new Cell[10][10];
    private final HashSet<Boat> boats = new HashSet<>();

    public Ocean() { this( Cell.states.EMPTY.toString() ); }

    public Ocean( String initialState ) {
        for ( int row=0; row < 10; row++ ) {
            for ( int column=0; column < 10; column++ ) {
                board[row][column] = new Cell( this, new Position( row, column ), initialState );
            }
        }
    }

    public Cell getCell( Position targetCell ) {
        if ( ! cellExists( targetCell ) ) {
            return null;
        }
        return board[targetCell.getRow()][targetCell.getCol()];
    }

    public boolean cellExists( Position targetCell ) {
        return ( 0 <= targetCell.getRow() && targetCell.getRow() <= board.length && 0 <= targetCell.getCol() && targetCell.getCol() <= board[0].length );
    }

    public JSONFire hit( Position targetCell ) { return this.getCell( targetCell ).hit(); }

    public Cell[][] getBoard() { return this.board; }

    public HashSet<Boat> getBoats() { return this.boats; }

    public boolean boatsLeft() { return this.countAliveBoats() > 0; }

    public int countAliveBoats() { return this.boats.stream().collect( Collectors.filtering( Boat::isAlive, Collectors.counting() ) ).intValue(); }

    public Boat findBoat( Position targetCell ) {
        for ( Boat boat : boats ) {
            if ( boat.getPosition().contains( this.getCell( targetCell ) ) ) {
                return boat;
            }
        }
        return null;
    }

    public void setupBoatsRandomly( HashSet<Boat> boats ) {
        this.boats.clear();
        this.boats.addAll( boats );
        for ( Boat boat : boats ) {
            do {
                Cell randomCell = this.getRandomCell();
                List<String> directions = new ArrayList<>( List.of( "up", "right", "down", "left" ) );
                while ( directions.size() > 0 && !boat.isAlive() ) {
                    String direction = directions.remove( new Random().nextInt( directions.size() ) );
                    boat.setup( randomCell, direction );
                }
            } while ( !boat.isAlive() );
        }
    }

    public Cell getRandomCell() {
        int randomRow = new Random().nextInt( this.board.length );
        int randomCol = new Random().nextInt( this.board[0].length );
        return this.board[randomRow][randomCol];
    }
}
