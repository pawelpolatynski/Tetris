package tetris;

import java.util.Objects;

public class Square {

    // Fields storing information about location of a Square.
    private int x,y;

    // Class constructor
    public Square (int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Getters and setters of private fields
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    // Method checks if the Square is next to another Squere from an array of Squares in a chosen direction.
    public boolean isNextToASquare(int direction, Square[] squaresTable){

        // If direction is equal to 0, it means that method checks if any Square is below the Square.
        if (direction == 0) {
            for (Square sq : squaresTable) {
                if (this.x == sq.x && this.y + 1 == sq.y) {
                    return true;
                }
            }
        } else {
            for (Square sq : squaresTable) {
                if (this.x +  direction == sq.x && this.y == sq.y) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return x == square.x &&
                y == square.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
