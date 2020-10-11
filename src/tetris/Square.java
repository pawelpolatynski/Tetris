package tetris;

import java.util.Objects;

public class Square {

    private int x,y;

    public Square (int x, int y) {
        this.x = x;
        this.y = y;
    }

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

    public boolean isNextToASquare(int direction, Square[] squaresTable){
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
