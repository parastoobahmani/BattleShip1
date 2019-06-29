package Model;

import java.util.ArrayList;
import java.util.Random;

public class Ship {

    private boolean isSet = false;
    private int size;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private int hits;
    private boolean destroyed;

    /**
     * Creates a Ship object
     *
     * @param size the number of spaces the ship takes up
     * @param board the gameBoard that the ship will be placed on
     */
    public Ship(int size, ArrayList<ArrayList<Space>> board) {
        this.size = size;
        this.hits = 0;
        this.destroyed = false;
        this.generateCoords(board);
    }

    /**
     * Creates a Ship object
     *
     * @param size the number of spaces the ship takes
     * @param startX the startX coordinate of the ship
     * @param startY the startY coordinate of the ship
     * @param endX the endX coordinate of the ship
     * @param endY the endY coordinate of the ship
     */
    public Ship(int size, int startX, int startY, int endX, int endY) {
        this.size = size;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.hits = 0;
        this.destroyed = false;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the startX
     */
    public int getStartX() {
        return startX;
    }

    /**
     * @param startX the startX to set
     */
    public void setStartX(int startX) {
        this.startX = startX;
    }

    /**
     * @return the startY
     */
    public int getStartY() {
        return startY;
    }

    /**
     * @param startY the startY to set
     */
    public void setStartY(int startY) {
        this.startY = startY;
    }

    public void setIsSet(boolean isSet) {
        this.isSet = isSet;
    }

    public boolean getIsSet() {
        return isSet;
    }

    /**
     * @return the endX
     */
    public int getEndX() {
        return endX;
    }

    /**
     * @param endX the endX to set
     */
    public void setEndX(int endX) {
        this.endX = endX;
    }

    /**
     * @return the endY
     */
    public int getEndY() {
        return endY;
    }

    /**
     * @param endY the endY to set
     */
    public void setEndY(int endY) {
        this.endY = endY;
    }

    /**
     * Hits a ship
     *
     * @return true if ship was destroyed
     */
    public boolean hit() {
        this.hits++;
        if (this.hits >= this.size) {
            this.destroyed = true;
        }

        return this.destroyed;
    }

    /**
     *
     * @return true if this ship has had hits==size
     */
    public boolean isDestroyed() {
        return this.destroyed;
    }

    /**
     * Checks if you are able to place a ship here
     *
     * @param board
     * @return true if you can place a ship here, false otherwise
     */
    public boolean checkCoords(ArrayList<ArrayList<Space>> board) {
        for (int x = this.startX; x <= this.endX; x++) {
            for (int y = this.startY; y <= this.endY; y++) {
                if (x < 0 || y < 0 || x > 9 || y > 9 || (board.get(x).get(y).getShip() != null)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Generates two valid coordinates to place the ship
     *
     * @param board
     */
    public void generateCoords(ArrayList<ArrayList<Space>> board) {
        Random gen = new Random();
        this.startX = gen.nextInt(10);
        this.startY = gen.nextInt(10);
        int dir = gen.nextInt(4);
        boolean i = true;
        while (i) {
            //Go North
            if (dir == 0) {
                this.endX = this.startX;
                this.endY = this.startY;
                this.startY = this.startY - (this.size - 1);
                if (this.checkCoords(board)) {

                    i = false;
                } else {
                    this.startX = this.endX;    //Put starting positions back at beginning
                    this.startY = this.endY;    //Put starting positions back at beginning
                    dir++;
                }
            }

            //Go East
            if (dir == 1) {
                this.endX = this.startX + (this.size - 1);
                this.endY = this.startY;
                if (this.checkCoords(board)) {
                    i = false;
                } else {
                    dir++;
                }
            }

            //Go South
            if (dir == 2) {
                this.endX = this.startX;
                this.endY = this.startY + (this.size - 1);
                if (this.checkCoords(board)) {
                    i = false;
                } else {
                    dir++;
                }
            }

            //Go West
            if (dir == 3) {
                this.endX = this.startX;
                this.endY = this.startY;
                this.startX = this.startX - (this.size - 1);
                if (this.checkCoords(board)) {
                    i = false;
                }
            }

            //If no room available at this spot, try another
            if (i) {
                this.startX = gen.nextInt(10);
                this.startY = gen.nextInt(10);
                dir = gen.nextInt(4);
            }

        }
    }

}

