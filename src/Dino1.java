import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Dino1 extends JPanel implements ActionListener, KeyListener {

    private int dinoX = 50;
    private int dinoY = 200;
    private int dinoWidth = 80;
    private int dinoHeight = 80;
    private boolean jumping = false;
    private int jumpSpeed = 0;
    private int groundY = 200;

    private int cactusX = 800;
    private int cactusY = 200;
    private int cactusWidth = 80;
    private int cactusHeight = 90;

    private Timer timer;
    private Random rand;

    private Image dinoImage;
    private Image cactusImage;

    private boolean gameOver = false;

    public Dino1() {
        this.setPreferredSize(new Dimension(800, 400));
        this.setBackground(new Color(255, 239, 130));
        this.setFocusable(true);
        this.addKeyListener(this);

        // Load images
        dinoImage = new ImageIcon(getClass().getResource("/dino.png")).getImage();
        cactusImage = new ImageIcon(getClass().getResource("/m.png")).getImage();

        timer = new Timer(20, this);
        timer.start();

        rand = new Random();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        g.setColor(Color.GRAY);
        g.fillRect(0, groundY + dinoHeight, getWidth(), 10);

        if (!gameOver) {

            g.drawImage(dinoImage, dinoX, dinoY, dinoWidth, dinoHeight, null);


            g.drawImage(cactusImage, cactusX, cactusY, cactusWidth, cactusHeight, null);
        } else {

            g.setColor(Color.BLUE);
            g.setFont(new Font("Arial", Font.BOLD, 40));

            g.drawString("بازی شما تمام شد !", 200, 100);
            g.drawString("سازنده : امیررضا رشیدی", 300, 200);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            cactusX -= 9;

            // Dino jump logic
            if (jumping) {
                dinoY += jumpSpeed;
                jumpSpeed += 1;
                if (dinoY >= groundY) {
                    dinoY = groundY;
                    jumping = false;
                }
            }


            if (cactusX < -cactusWidth) {
                cactusX = 350 + rand.nextInt(300) + 200;
            }


            Rectangle dinoRect = new Rectangle(dinoX, dinoY, dinoWidth, dinoHeight);
            Rectangle cactusRect = new Rectangle(cactusX, cactusY, cactusWidth, cactusHeight);
            if (dinoRect.intersects(cactusRect)) {
                gameOver = true;
                timer.stop();
            }
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!jumping && e.getKeyCode() == KeyEvent.VK_SPACE) {
            jumping = true;
            jumpSpeed = -19;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}


    public static void main(String[] args) {
        JFrame frame = new JFrame("Dino Game");
        Dino1 gamePanel = new Dino1();
        frame.add(gamePanel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
