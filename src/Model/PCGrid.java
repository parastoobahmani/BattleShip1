package Model;


import java.util.ArrayList;
import javafx.scene.layout.GridPane;

public class PCGrid extends Grid{

    public PCGrid(GridPane gp) {
        super(gp);
        this.mode = 0;
    }

    /**
     * Clears PC game board, adds ships to it
     */
    void setPCBoard(){
        this.ships = new ArrayList<Ship>(10);
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                this.gameBoard.get(x).get(y).setShip(null);
                this.gameBoard.get(x).get(y).setPressed(false);
            }
        }

        Ship s1 = new Ship(1, this.gameBoard);
        this.addShip(s1, false);
        this.ships.add(s1);

        Ship s2 = new Ship(1, this.gameBoard);
        this.addShip(s2, false);
        this.ships.add(s2);

        Ship s3 = new Ship(1, this.gameBoard);
        this.addShip(s3, false);
        this.ships.add(s3);

        Ship s4 = new Ship(1, this.gameBoard);
        this.addShip(s4, false);
        this.ships.add(s4);

        Ship s5 = new Ship(2, this.gameBoard);
        this.addShip(s5, false);
        this.ships.add(s5);

        Ship s6 = new Ship(2, this.gameBoard);
        this.addShip(s6, false);
        this.ships.add(s6);

        Ship s7 = new Ship(2, this.gameBoard);
        this.addShip(s7, false);
        this.ships.add(s7);

        Ship s8 = new Ship(3, this.gameBoard);
        this.addShip(s8, false);
        this.ships.add(s8);

        Ship s9 = new Ship(3, this.gameBoard);
        this.addShip(s9, false);
        this.ships.add(s9);

        Ship s10 = new Ship(4, this.gameBoard);
        this.addShip(s10, false);
        this.ships.add(s10);
    }

    @Override
    public void initGrid() {
        for (int x = 0; x < 10; x++) {
            ArrayList<Space> row = new ArrayList(10);
            for (int y = 0; y < 10; y++) {
                row.add(new Space());
            }
            gameBoard.add(row);
        }

        //Press ALL the buttons
        //It's a hacky way to make the PC grid not do anything when game hasn't started
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                gameBoard.get(x).get(y).setPressed(true);
            }
        }

        this.clearDisplayBoard();
        this.setPCBoard();
    }


}
