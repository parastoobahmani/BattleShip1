package Model;


import java.util.ArrayList;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public abstract class Grid {
    GridPane displayBoard;
    int mode;
    public ArrayList<ArrayList<Space>> gameBoard;
    public ArrayList<Ship> ships;

    /**
     *
     * @param gp
     */
    public Grid(GridPane gp){
        this.displayBoard = gp;
        gameBoard = new ArrayList<>(10);
        ships = new ArrayList<>(10);
    }

    /**
     * Adds a ship to a game board
     *
     * @param ship the ship to be added
     * @param isRotated whether or not the ship to be added is rotated
     */
    public void addShip(Ship ship, boolean isRotated) {

        //Do NOT add ship if it's not valid. (only applies to player game board)
        if (mode == 1) {
            if (!this.isValid(ship.getStartX(), ship.getStartY(), ship.getSize(), isRotated)) {
                return;
            }
        }
        for (int x = ship.getStartX(); x <= ship.getEndX(); x++) {
            for (int y = ship.getStartY(); y <= ship.getEndY(); y++) {
                //Ship oldShip = this.gameBoard.get(x).get(y).getShip();
                this.gameBoard.get(x).get(y).setShip(ship);
            }
        }
    }


    /**
     * Wipes a display board by setting all cells to black
     */
    void clearDisplayBoard() {
        GridPane board = this.displayBoard;
        for (Node node : board.getChildren()) {
            Integer x = GridPane.getColumnIndex(node);
            Integer y = GridPane.getRowIndex(node);


            //For some reason, SceneBuilder doesn't automatically initialize
            //cols and rows at index 0. Must do it manually.
            if (x == null) {
                GridPane.setColumnIndex(node, 0);
            }

            if (y == null) {
                GridPane.setRowIndex(node, 0);
            }
            Rectangle rect = (Rectangle) node;
            rect.setFill(Color.BLACK);
            rect.setStroke(Color.WHITE);

        }
    }

    /**
     *
     * @return true if all ships are destroyed, false otherwise
     */
    public boolean checkWin() {
        for (Ship ship : ships) {
            if (ship.isDestroyed() == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * Game method that processes a guess on a game board
     *
     * @param col the col index to be guessed
     * @param row the row index to be guessed
     * @param ai the Artificial Intelligence used to guess next spot
     */
    public void guess(int col, int row, AI ai) {
        //For convenience-----------------------
        GridPane gp = this.displayBoard;
        ArrayList<ArrayList<Space>> board = this.gameBoard;
        //--------------------------------------

        Ship s = null;
        try {
            s = board.get(col).get(row).getShip();
        } catch (Exception e) {
            System.out.println("FAIL");
            System.out.println("Col: " + col + "\nRow: " + row);
        }
        Node n = this.getNode(col, row);
        Rectangle r = (Rectangle) n;
        if (s != null) {
            r.setFill(Color.PURPLE);
            if (s.hit()) {
                for (int x = s.getStartX(); x <= s.getEndX(); x++) {
                    for (int y = s.getStartY(); y <= s.getEndY(); y++) {
                        Node paintNode = this.getNode(x, y);
                        ((Rectangle) paintNode).setFill(Color.GREEN);
                    }

                }

                //If PC is guessing, provide feedback to the AI
                if (mode == 1) {   //if PC is guessing AND ship is destroyed
                    ai.feedback(true, true);
                }
            } else {
                if (mode == 1) {   //if PC is guessing and ship is hit but not destroyed
                    ai.feedback(true, false);
                }
            }
        } else {    //If PC is guessing and ship is not hit and not destroyed
            r.setFill(Color.WHITE);
            r.setStroke(Color.BLACK);
            if (mode == 1) {
                ai.feedback(false, false);
            }
        }
        board.get(col).get(row).setPressed(true);
    }

    /**
     * GridPane's have no default way to retrieve a node by column and row indices.
     * This method adds that functionality
     *
     * @param col the column index of the node
     * @param row the row index of the node
     * @return the node we are looking for, or null if node is not found
     */
    public Node getNode(int col, int row) {
        GridPane gridPane = this.displayBoard;
        int i = 0;
        for (Node node : gridPane.getChildren()) {
            if (node == null) {
                return null;
            }
            if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }

    /**
     * Checks if boat placement is valid or not
     *
     * @param x the x index of the boat to place
     * @param y the y index of the boat to place
     * @param size the size of the boat to place
     * @return true if placement is a valid spot, false otherwise
     */
    public Boolean isValid(int x, int y, int size, boolean isRotated) {
        //Check if it's too big horizontal...
        if (isRotated && ((y + size - 1 > 9))) {
            return false;
        }

        //...Or vertical to fit on grid
        if (!isRotated && (x + size - 1 > 9)) {
            return false;
        }

        //if space is already occupied by boat
        if (isRotated) {
            for (int i = 0; i < size && i <= 9; i++) {
                Node n = this.getNode(x, y + i);
                Rectangle r = (Rectangle) n;
                if (r.getFill().equals(Color.GREEN)) //Already occupied
                    return false;
            }
        }

        if (!isRotated) {
            for (int i = 0; i < size && i <= 9; i++) {
                Node n = this.getNode(x + i, y);
                Rectangle r = (Rectangle) n;
                if (r.getFill().equals(Color.GREEN)) //Already occupied
                    return false;
            }
        }

        return true;
    }

    boolean shipOnSpace(int x, int y, int size, boolean isRotated) {
        if (isRotated) {
            for (int i = 0; i < size && i <= 9; i++) {
                Node n = this.getNode(x, y + i);
                Rectangle r = (Rectangle) n;
                if (r.getFill().equals(Color.GREEN)) //Already occupied
                    return false;
            }
        }

        if (!isRotated) {
            for (int i = 0; i < size && i <= 9; i++) {
                Node n = this.getNode(x + i, y);
                Rectangle r = (Rectangle) n;
                if (r.getFill().equals(Color.GREEN)) //Already occupied
                    return false;
            }
        }
        return true;
    }

    /**
     * Shortcut to run the 2 needed methods to set up game and display boards
     */
    abstract void initGrid();

}
