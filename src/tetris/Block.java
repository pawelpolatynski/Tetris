package tetris;

import java.awt.*;
import java.util.Random;

public class Block {
    /*
    Parameter Blocks stores all posible configurations of 4 Square objects that make up a  tetris block.
     */
    private final Square[][][] Blocks = {
            // T-shaped block
            {
                    {new Square(0, 0), new Square(1, 0), new Square(0, 1), new Square(-1, 0)},
                    {new Square(1, 0), new Square(1, 1), new Square(0, 1), new Square(1, 2)},
                    {new Square(0, 0), new Square(0, 1), new Square(1, 1), new Square(-1, 1)},
                    {new Square(0, 0), new Square(0, 1), new Square(0, 2), new Square(1, 1)}
            },
            // S-shaped block
            {
                    {new Square(0, 0), new Square(1, 0), new Square(0, 1), new Square(-1, 1)},
                    {new Square(-1, 0), new Square(-1, 1), new Square(0, 1), new Square(0, 2)},
                    {new Square(0, 0), new Square(1, 0), new Square(0, 1), new Square(-1, 1)},
                    {new Square(-1, 0), new Square(-1, 1), new Square(0, 1), new Square(0, 2)}
            },
            // Z-shaped block
            {
                    {new Square(0, 0), new Square(-1, 0), new Square(0, 1), new Square(1, 1)},
                    {new Square(1, 0), new Square(1, 1), new Square(0, 1), new Square(0, 2)},
                    {new Square(0, 0), new Square(-1, 0), new Square(0, 1), new Square(1, 1)},
                    {new Square(1, 0), new Square(1, 1), new Square(0, 1), new Square(0, 2)}
            },
            // square-shaped block
            {
                    {new Square(0, 0), new Square(1, 0), new Square(0, 1), new Square(1, 1)},
                    {new Square(0, 0), new Square(1, 0), new Square(0, 1), new Square(1, 1)},
                    {new Square(0, 0), new Square(1, 0), new Square(0, 1), new Square(1, 1)},
                    {new Square(0, 0), new Square(1, 0), new Square(0, 1), new Square(1, 1)}
            },
            // mirrored L-shaped block
            {
                    {new Square(0, 0), new Square(1, 0), new Square(0, 1), new Square(0, 2)},
                    {new Square(0, 0), new Square(-1, 0), new Square(1, 0), new Square(1, 1)},
                    {new Square(1, 0), new Square(1, 1), new Square(0, 2), new Square(1, 2)},
                    {new Square(-1, 0), new Square(-1, 1), new Square(0, 1), new Square(1, 1)}
            },
            // L-shaped block
            {
                    {new Square(0, 0), new Square(1, 0), new Square(1, 1), new Square(1, 2)},
                    {new Square(1, 0), new Square(1, 1), new Square(0, 1), new Square(-1, 1)},
                    {new Square(0, 0), new Square(0, 1), new Square(0, 2), new Square(1, 2)},
                    {new Square(-1, 0), new Square(-1, 1), new Square(0, 0), new Square(1, 0)}
            },
            // I-shaped block
            {
                    {new Square(-1, 0), new Square(0, 0), new Square(1, 0), new Square(2, 0)},
                    {new Square(0, 0), new Square(0, 1), new Square(0, 2), new Square(0, 3)},
                    {new Square(-1, 0), new Square(0, 0), new Square(1, 0), new Square(2, 0)},
                    {new Square(0, 0), new Square(0, 1), new Square(0, 2), new Square(0, 3)}
            }
    };

    // Array colorsTable contains all possible colors a block can have.
    private final Color[] colorsTable = {Color.blue, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.pink, Color.YELLOW, Color.ORANGE};

    // blocksColor stores the color of the block.
    Color blocksColor;
    // shape is an array of Square objects storing information about placement of 4 Square objects that make up a block.
    private Square[] shape;
    // Variable random helps randmoly choose the next block to appear in the game.
    private static Random random = new Random();
    // Variable posX and posY determine where the block should be rendered on the screen.
    private int posX, posY;
    // Variable type stores int value between 0 and 6 to determine the shape of the block.
    // Variable rotation stores int value between 0 and 3 to determine the rotation of the block.
    private int type, rotation;

    // Class constructor
    public Block(int type, int rotation, int x, int y) {
        this.type = type;
        this.rotation = rotation;
        this.blocksColor = colorsTable[random.nextInt(7)];
        this.shape = Blocks[this.type][this.rotation];
        this.posX = x;
        this.posY = y;
    }

    // Method creates new block and marks its position in an array.
    public static Block getNewBlock(Color[][] field, int x, int y) {
        Block currentBlock = new Block(random.nextInt(7), random.nextInt(4), x, y);
        for (int i = 0; i < 4; i++) {
            field[currentBlock.posY + currentBlock.shape[i].getY()][currentBlock.posX + currentBlock.shape[i].getX()] = currentBlock.blocksColor;
        }
        return currentBlock;

    }

    // Method changes colors of an array cells, in which the block was stored, to grey.
    // Then it moves the block down by one row and changes color of array cells to the color of the block.
    public void moveTheBlockDown(Color[][] colorField) {
        this.colorField(colorField, Color.gray);

        this.posY += 1;

        this.colorField(colorField, this.blocksColor);
    }

    // Method changes colors of an array cells, in which the block was stored, to grey.
    // Then it moves the block to the side by one column and changes color of array cells to the color of the block.
    // If the value of direction parameter, the method moves the block to the right. If the value of direction parameter
    // is -1, it moves the block to left.
    public void moveTheBlockSide(Color[][] colorField, int direction) {
        this.colorField(colorField, Color.gray);

        this.posX += direction;

        this.colorField(colorField, this.blocksColor);
    }

    // Method changes the value stored in selected cells (corresponding to location of the block) of a selected array.
    public void colorField(Color[][] field, Color color) {
        for (int i = 0; i < 4; i++) {
            field[this.posY + this.shape[i].getY()][this.posX + this.shape[i].getX()] = color;
        }
    }

    // Method checks, whether the block can be moved in selected direction.
    public boolean canMoveSide(int direction, Color[][] colorField){
        for (int i = 0; i < 4; i++) {

            // IF a cell in selected direction corresponds to a location of another Square of this block
            // THEN continue.
            if(this.shape[i].isNextToASquare(direction, shape)) {
                continue;
            }

            // IF direction is 1 and the block is at the right end of the array
            // OR direction is -1 and the block is at the left end of the array THEN
            // RETURN false
            if ((direction == 1 && this.posX + this.shape[i].getX() == 9) ||
                    (direction == -1 && this.posX + this.shape[i].getX() == 0)) {
                return false;

            // ELSE IF direction isn't 0 and the color of a cell in selected direction isn,t gray THEN
            // RETURN false
            } else if (direction != 0 &&
                    colorField[this.posY + this.shape[i].getY()][this.posX + this.shape[i].getX() + direction]
                            != Color.gray) {
                return false;
            // ELSE IF direction is 0 AND EITHER the block is at the bottom of the array (in row 19)
            // OR a cell beneath the block is not gray
            // RETURN false
            } else if (direction == 0 && ((this.posY + this.shape[i].getY() == 19) ||
                    (colorField[this.posY + this.shape[i].getY() + 1][this.posX + this.shape[i].getX()] !=
                            Color.gray))) {
                return false;

            }
        }
        // OTHERWISE RETURN true
        return true;
    }

    // Method rotates the block and checks if it's new position is allowed.
    // If it isn't then the method reverses the rotation.
    public void rotate(Color[][] field) {
        this.colorField(field, Color.gray);
        if(this.rotation < 3) {
            this.rotation += 1;
        } else {
            this.rotation = 0;
        }
        this.shape = Blocks[this.type][this.rotation];

        // Checking whether after the rotation every Square of the Block is inside the array and doesnt cover
        // other Square.
        for(Square sq : this.shape) {
            if (this.posY + sq.getY() > 19 || this.posX + sq.getX() < 0 || this.posX + sq.getX() > 9 ||
                    field[this.posY + sq.getY()][this.posX + sq.getX()] != Color.gray) {
                if (this.rotation > 0) {
                    this.rotation -= 1;
                } else {
                    this.rotation = 3;
                }
                this.shape = Blocks[this.type][this.rotation];
                break;
            }
        }
        this.colorField(field, this.blocksColor);
    }

    // Method moves the Block to as low as possible in the array.
    public void drop (Color[][] field){
        this.colorField(field, Color.gray);
        while(this.canMoveSide(0, field)) {
            this.posY += 1;
        }
        this.colorField(field, this.blocksColor);
    }

    // Method passes values of parameters of one Block to another. It is
    public void switchBlocks(Block block, Color[][] colorField) {
        this.type = block.type;
        this.rotation = block.rotation;
        this.blocksColor = block.blocksColor;
        this.shape = block.shape;
        this.posX = 4;
        this.posY = 0;
        for (int i = 0; i < 4; i++) {
            colorField[this.posY + this.shape[i].getY()][this.posX + this.shape[i].getX()] = this.blocksColor;
        }
    }



}
