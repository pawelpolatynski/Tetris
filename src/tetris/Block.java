package tetris;

import java.awt.*;
import java.util.Random;

public class Block {

    public Square[][][] Blocks = {
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
    public Color[] colorsTable = {Color.blue, Color.CYAN, Color.GREEN, Color.MAGENTA};
    public Color blocksColor;
    public Square[] shape;
    public static Random random = new Random();
    private int posX, posY;
    private int type, rotation;

    public Block(int i1, int i2, int x, int y) {
        this.type = i1;
        this.rotation = i2;
        blocksColor = colorsTable[random.nextInt(4)];
        shape = Blocks[type][rotation];
        posX = x;
        posY = y;
    }

    public static Block getNewBlock(Color[][] field, int x, int y) {
        Block currentBlock = new Block(random.nextInt(7), random.nextInt(4), x, y);
        for (int i = 0; i < 4; i++) {
            field[currentBlock.posY + currentBlock.shape[i].getY()][currentBlock.posX + currentBlock.shape[i].getX()] = currentBlock.blocksColor;
        }
        return currentBlock;

    }

    public void moveTheBlockDown(Color[][] colorField) {
        this.colorField(colorField, Color.gray);

        this.posY += 1;

        this.colorField(colorField, this.blocksColor);
    }

    public void moveTheBlockSide(Color[][] colorField, int direction) {
        this.colorField(colorField, Color.gray);

        this.posX += direction;

        this.colorField(colorField, this.blocksColor);
    }

    public void colorField(Color[][] field, Color color) {
        for (int i = 0; i < 4; i++) {
            field[this.posY + this.shape[i].getY()][this.posX + this.shape[i].getX()] = color;
        }
    }

    public boolean canMoveSide(int direction, Color[][] colorField){
        for (int i = 0; i < 4; i++) {
            if(this.shape[i].isNextToASquare(direction, shape)) {
                continue;
            }
            if ((direction == 1 && this.posX + this.shape[i].getX() == 9) ||
                    (direction == -1 && this.posX + this.shape[i].getX() == 0)) {
                return false;
            } else if (direction != 0 && colorField[this.posY + this.shape[i].getY()][this.posX + this.shape[i].getX() + direction] != Color.gray) {
                return false;
            } else if (direction == 0 && ((this.posY + this.shape[i].getY() > 18) ||
                    (colorField[this.posY + this.shape[i].getY() + 1][this.posX + this.shape[i].getX()] !=
                            Color.gray))) {
                return false;

            }
        }
        return true;
    }

    public void rotate(Color[][] field) {
        this.colorField(field, Color.gray);
        if(this.rotation < 3) {
            this.rotation += 1;
        } else {
            this.rotation = 0;
        }
        this.shape = Blocks[this.type][this.rotation];
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

    public void drop (Color[][] field){
        this.colorField(field, Color.gray);
        while(this.canMoveSide(0, field)) {
            this.posY += 1;
        }
        this.colorField(field, this.blocksColor);
    }

    public void switchBlocks(Block block) {
        this.type = block.type;
        this.rotation = block.rotation;
        this.blocksColor = block.blocksColor;
        this.shape = block.shape;
        this.posX = 4;
        this.posY = 0;
    }



}
