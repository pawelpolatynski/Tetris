package tetris;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

public class Tetris implements ActionListener, KeyListener {

    static Tetris tetris;
    // Each cell of "field" array stores color of corresponding squares on the tetris board
    private Color[][] field;
    // Each cell of this array stores color of one of the squares making up area which presents the next block
    private Color[][] waitingField;
    // Object rendering shapes
    private final Renderer renderer;
    // Values of width and height of game's window
    private final int width = 320, height = 750;
    // Field score stores current player's score
    private int score;
    // Object moving in the array "field" from top to the bottom that imitating movement of falling tetris blocks.
    private Block fallingBlock;
    // Object representing shape and color of the next falling block
    private Block waitingBlock;
    // Boolean fields that help to represent state of keys.
    private boolean x, space, left, right = false;
    // Counter allows changing the games speed.
    private int counter = 0;
    // GameStatus allows us to control what is rendered in the screen. gameStatus equal to 0 means the game is running
    // and therefor game board and falling objects should be rendered. gameStatus equal to 1 means the game is over
    // and game-over screen should be rendered.
    private int gameStatus = 0;
    private LinkedList<Record> records;

    // Class constructor
    public Tetris() {
        Timer timer = new Timer(50, this);
        JFrame jframe = new JFrame("Tetris");
        this.records = new LinkedList<Record>();
        Record.loadTypes(records);
        renderer = new Renderer();

        field = new Color[20][10];

        // Drawing empty game board
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = Color.gray;
            }
        }

        // Drawing empty field containing next Block
        waitingField = new Color[6][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                waitingField[i][j] = Color.white;
            }
        }
        score = 0;
        jframe.setSize(width, height);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.addKeyListener(this);
        jframe.add(renderer);

        // Creating first falling Block and the first waiting Block
        fallingBlock = Block.getNewBlock(this.field, 4, 0);
        waitingBlock = Block.getNewBlock(this.waitingField, 2, 1);
        timer.start();

    }

    public void render(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, width, height );
        // If game runs
        if (gameStatus == 0) {
            g.setColor(Color.white);
            g.fillRect(25 * 6, 22 * 25, 5 * 25, 5 * 25);
            g.setColor(Color.gray);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Score:", 25, 23 * 25);
            g.drawString(String.valueOf(this.score), 25, 24 * 25);
            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 10; j++) {
                    g.setColor(field[i][j]);
                    g.fillRect(j * 25 + 25, i * 25 + 25, 25, 25);
                }
            }
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    g.setColor(waitingField[i][j]);
                    g.fillRect((j + 5) * 25, (i + 22) * 25, 25, 25);
                }
            }
            g.setColor(Color.lightGray);
            for (int i = 0; i < 21; i++) {
                g.drawLine(25, i * 25 + 25, 275, i * 25 + 25);
            }
            for (int i = 0; i < 11; i++) {
                g.drawLine(i * 25 + 25, 25, i * 25 + 25, 525);
            }
            // If it's stopped
        } else {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over", 20, this.height / 2);
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Your score is ", this.width / 6, this.height * 5 / 8);
            g.drawString(String.valueOf(this.score), this.width / 2 - 25, this.height * 5 / 8 + 50);
            g.setFont(new Font("Arial", Font.BOLD, 15));
            g.drawString("Write down your name and press Enter ", this.width / 20, this.height * 3 / 4);

        }

    }
    public void update() {

        // If game is running...
        if (gameStatus == 0) {

            // If the right key is pressed and block can move to the left then move the block to the left.
            if (left && this.fallingBlock.canMoveSide(-1, this.field)) {
                this.fallingBlock.moveTheBlockSide(this.field, -1);
                left = false;
            }

            // If the right key is pressed and block can move to the right then move the block to the right.
            if (right && this.fallingBlock.canMoveSide(1, this.field)) {
                this.fallingBlock.moveTheBlockSide(this.field, 1);
                right = false;
            }

            // If the right key is pressed, rotate the block.
            if (x) {
                this.fallingBlock.rotate(this.field);
                x = false;
            }

            // If the right key is pressed, drop the block.
            if (space) {
                this.fallingBlock.drop(this.field);
                space = false;
            }

            // If the block can't move any lower, check if any row can be erased and do it.
            if (!this.fallingBlock.canMoveSide(0, this.field)) {
                this.checkRows();
            }

            // Variable counter regulates games speed. The higher the value that resets the counter is the slower will
            // blocks fall down the board.
            this.counter++;
            if (this.counter == 10) {
                this.counter = 0;
                this.fallingBlock.moveTheBlockDown(this.field);
            }
        }




    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();

        renderer.repaint();
    }


    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int id = e.getKeyCode();

        if (id == KeyEvent.VK_LEFT) {
            left = true;
        }

        if (id == KeyEvent.VK_RIGHT ) {
            right = true;
        }

        if (id == KeyEvent.VK_SPACE ) {
            space = true;
        }

        if (id == KeyEvent.VK_X ) {
            x = true;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        int id = e.getKeyCode();

        if (id == KeyEvent.VK_SPACE ) {
            space = false;
        }

        if (id == KeyEvent.VK_LEFT) {
            left = false;
        }

        if (id == KeyEvent.VK_RIGHT ) {
            right = false;
        }

        if (id == KeyEvent.VK_X ) {
            x = false;
        }

    }

    // Method checks if any row qualifies for erasing and if so calls a method that erases the row.
    // After that it calculates and updates player's score.
    // The method also checks whether the player can continue the game or not.
    public void checkRows() {
        double pointsCounter = 0;
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                if (this.field[i][j] == Color.gray) {
                    break;
                }
                if (j == 9) {
                    pointsCounter++;
                    this.clearRow(i);
                }
            }
        }
        // Counting player's score
        this.score += pointsCounter * (8 + Math.pow(2,  pointsCounter));
        this.waitingBlock.colorField(this.waitingField, Color.white);
        this.fallingBlock.switchBlocks(waitingBlock, this.field);
        this.waitingBlock = Block.getNewBlock(this.waitingField, 2, 1);

        // If just after placing new falling Block, the Block can't move down, it means the player lost.
        if (!this.fallingBlock.canMoveSide(0, this.field)) {
            this.gameStatus = 1;
        }


    }

    // Method clears a row, and copies all rows above the deleted row and pastes them a row lower.
    private void clearRow(int i) {
        for (int k = i; k > 0; k--) {
            System.arraycopy(this.field[k - 1], 0, this.field[k], 0, 10);
        }
        for (int l = 0; l < 10; l++) {
            this.field[0][l] = Color.gray;
        }
    }

    // Main method of application
    public static void main(String[] args) {
        tetris = new Tetris();
    }

}
