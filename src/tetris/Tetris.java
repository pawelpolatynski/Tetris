package tetris;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Tetris implements ActionListener, KeyListener {

    public static Tetris tetris;
    public Color[][] field;
    public Color[][] waitingField;
    public Renderer renderer;
    public int width = 320, height = 750;
    public int score;
    public Block fallingBlock;
    private Block waitingBlock;
    private boolean d, x, space, left, right = false;
    private int counter = 0;
    private int gameStatus = 0;


    public Tetris() {
        Timer timer = new Timer(50, this);
        JFrame jframe = new JFrame("Tetris");

        renderer = new Renderer();

        field = new Color[20][10];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = Color.gray;
            }
        }

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

        fallingBlock = Block.getNewBlock(this.field, 4, 0);
        waitingBlock = Block.getNewBlock(this.waitingField, 2, 1);
        timer.start();

    }

    public void render(Graphics2D g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, width, height );
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
        } else {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("Game Over", 20, this.height / 2);
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Your score is ", this.width / 6, this.height * 3 / 4);
            g.drawString(String.valueOf(this.score), this.width / 2 - 20, this.height * 3 / 4 + 50);

        }

    }
    public void update() {
        if (gameStatus == 0) {
            if (left && this.fallingBlock.canMoveSide(-1, this.field)) {
                this.fallingBlock.moveTheBlockSide(this.field, -1);
                left = false;
            }

            if (right && this.fallingBlock.canMoveSide(1, this.field)) {
                this.fallingBlock.moveTheBlockSide(this.field, 1);
                right = false;
            }

            if (x) {
                this.fallingBlock.rotate(this.field);
                x = false;
            }

            if (space) {
                this.fallingBlock.drop(this.field);
                space = false;
            }

            if (!this.fallingBlock.canMoveSide(0, this.field)) {
                this.checkRows();
            }
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

        if (id == KeyEvent.VK_D ) {
            d = true;
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

        if (id == KeyEvent.VK_D ) {
            d = false;
        }

    }

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
        this.score += pointsCounter * (8 + Math.pow(2,  pointsCounter));
        this.fallingBlock.switchBlocks(waitingBlock);
        this.waitingBlock = Block.getNewBlock(this.waitingField, 2, 1);
        if (!this.fallingBlock.canMoveSide(0, this.field)) {
            this.gameStatus = 1;
        }


    }

    private void clearRow(int i) {
        for (int k = i; k > 0; k--) {
            System.arraycopy(this.field[k - 1], 0, this.field[k], 0, 10);
        }
        for (int l = 0; l < 10; l++) {
            this.field[0][l] = Color.gray;
        }
    }

    public static void main(String[] args) {
        tetris = new Tetris();
    }

}
