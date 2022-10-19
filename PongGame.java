
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import javax.swing.Timer;

/**
 *
 * @author nafea8846
 */
public class PongGame extends JComponent implements ActionListener {

    // Height and Width of our game
    static final int WIDTH = 800;
    static final int HEIGHT = 600;

    //Title of the window
    String title = "Pong";

    // sets the framerate and delay for our game
    // this calculates the number of milliseconds per frame
    // you just need to select an approproate framerate
    int desiredFPS = 60;
    int desiredTime = Math.round((1000 / desiredFPS));

    // timer used to run the game loop
    // this is what keeps our time running smoothly :)
    Timer gameTimer;

    // YOUR GAME VARIABLES WOULD GO HERE
    Rectangle ball = new Rectangle(390, 290, 20, 20);
    int ballSpeed = 5;
    int ballH = 1;
    int ballV = 1;

    Rectangle paddle1 = new Rectangle(10, 240, 10, 40);
    Rectangle paddle2 = new Rectangle(780, 240, 10, 40);
    int paddleSpeed = 5;
    boolean up1 = false;
    boolean down1 = false;
    boolean up2 = false;
    boolean down2 = false;
    
    int score1 = 0;
    int score2 = 0;
    Font biggerFont = new Font("arial", Font.BOLD, 72);
    // GAME VARIABLES END HERE    

    // Constructor to create the Frame and place the panel in
    // You will learn more about this in Grade 12 :)
    public PongGame() {
        // creates a windows to show my game
        JFrame frame = new JFrame(title);

        // sets the size of my game
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // adds the game to the window
        frame.add(this);

        // sets some options and size of the window automatically
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        // shows the window to the user
        frame.setVisible(true);

        // add listeners for keyboard and mouse
        frame.addKeyListener(new Keyboard());
        Mouse m = new Mouse();
        this.addMouseMotionListener(m);
        this.addMouseWheelListener(m);
        this.addMouseListener(m);

        // Set things up for the game at startup
        setup();

        // Start the game loop
        gameTimer = new Timer(desiredTime, this);
        gameTimer.setRepeats(true);
        gameTimer.start();
    }

    // drawing of the game happens in here
    // we use the Graphics object, g, to perform the drawing
    // NOTE: This is already double buffered!(helps with framerate/speed)
    @Override
    public void paintComponent(Graphics g) {
        // always clear the screen first!
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // GAME DRAWING GOES HERE
        Graphics2D g2d = (Graphics2D) g;
        g2d.fill(ball);
        g2d.fill(paddle1);
        g2d.fill(paddle2);
        g.setFont(biggerFont);
        g.drawString("" + score1, 350, 100);
        g.drawString("" + score2, 430, 100);
// GAME DRAWING ENDS HERE
    }

    // This method is used to do any pre-setup you might need to do
    // This is run before the game loop begins!
    public void setup() {
        // Any of your pre setup before the loop starts should go here

    }

    // The main game loop
    // In here is where all the logic for my game will go
    public void loop() {
        ball.x = ball.x + ballH * ballSpeed;
        ball.y = ball.y + ballV * ballSpeed;
        if (ball.x + ball.height > WIDTH) {
            ballH = -1;
            score1++;
            ball.x = (WIDTH - ball.width)/2;
            ball.y = (HEIGHT - ball.height)/2;

        } else if (ball.x < 0) {
            ballH = 1;
            score2++;
            ball.x = (WIDTH - ball.width)/2;
            ball.y = (HEIGHT - ball.height)/2;
        }
        if (ball.y + ball.height > HEIGHT) {
            ballV = -1;
        } else if (ball.y < 0) {
            ballV = 1;
        }
        if (paddle1.y <= HEIGHT - paddle1.height && paddle1.y >= 0) {
            if (up1) {
                paddle1.y = paddle1.y - paddleSpeed;
            } else if (down1) {
                paddle1.y = paddle1.y + paddleSpeed;
            }
        } else {
            if (paddle1.y < 0) {
                paddle1.y = 0;
                up1 = false;
            } else if (paddle1.y > HEIGHT - paddle1.height) {
                paddle1.y = HEIGHT - paddle1.height;
                down1 = false;
            }

        }

        if (paddle2.y <= HEIGHT - paddle2.height && paddle2.y >= 0) {
            if (up2) {
                paddle2.y = paddle2.y - paddleSpeed;
            } else if (down2) {
                paddle2.y = paddle2.y + paddleSpeed;
            }
        } else {
            if (paddle2.y < 0) {
                paddle2.y = 0;
                up2 = false;
            } else if (paddle2.y > HEIGHT - paddle2.height) {
                paddle2.y = HEIGHT - paddle2.height;
                down2 = false;
            }
        }
        if (ball.intersects(paddle1)) {
            ballH = 1;
        }
        else if (ball.intersects(paddle2)) {
            ballH = -1;
        }
    }

    // Used to implement any of the Mouse Actions
    private class Mouse extends MouseAdapter {

        // if a mouse button has been pressed down
        @Override
        public void mousePressed(MouseEvent e) {

        }

        // if a mouse button has been released
        @Override
        public void mouseReleased(MouseEvent e) {

        }

        // if the scroll wheel has been moved
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {

        }

        // if the mouse has moved positions
        @Override
        public void mouseMoved(MouseEvent e) {

        }
    }

    // Used to implements any of the Keyboard Actions
    private class Keyboard extends KeyAdapter {

        // if a key has been pressed down
        @Override
        public void keyPressed(KeyEvent e) {
            int k = e.getKeyCode();
            if (k == KeyEvent.VK_W) {
                up1 = true;
            } else if (k == KeyEvent.VK_S) {
                down1 = true;
            }
            if (k == KeyEvent.VK_UP) {
                up2 = true;
            } else if (k == KeyEvent.VK_DOWN) {
                down2 = true;
            }
        }

        // if a key has been released
        @Override
        public void keyReleased(KeyEvent e) {
int k = e.getKeyCode();
            if (k == KeyEvent.VK_W) {
                up1 = false;
            } else if (k == KeyEvent.VK_S) {
                down1 = false;
            }
            if (k == KeyEvent.VK_UP) {
                up2 = false;
            } else if (k == KeyEvent.VK_DOWN) {
                down2 = false;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        loop();
        repaint();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // creates an instance of my game
        PongGame game = new PongGame();
    }
}
