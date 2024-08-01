package Unit3.Animation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Pong {
    JFrame frame; 
    DrawingPanel drawPanel;
    Timer timer; 
    Ball ball; 
    int score = 0; 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Pong();
            }
        });
    }
    
    public Pong(){
        ball = new Ball(500, 300, 25); 
        frame = new JFrame("Pong");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 600);
        drawPanel = new DrawingPanel();

        drawPanel.addMouseMotionListener(new MouseListener());
        frame.addKeyListener(new KeyListener());

        frame.add(drawPanel);
        frame.pack();
        frame.setVisible(true);
        timer = new Timer(10, new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ball.checkCollision();
                ball.move();
                drawPanel.repaint();
            }
        });
        timer.start();
    }

    private class DrawingPanel extends JPanel{
        Platform p1, p2;
        public DrawingPanel(){
            this.setPreferredSize(new Dimension(1000,600));
            this.setBackground(Color.DARK_GRAY);
            p1 = new Platform(10, 0, 20, 100);
            p2 = new Platform(970, 0, 20, 100);
        }
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fill(p1);
            g2.fill(p2);
            g2.fillOval(ball.x, ball.y, ball.r, ball.r);
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString("Score: " + score, getWidth()/2-20, 20);
        }

    }

    private class Platform extends Rectangle{ 
        public Platform(int x, int y, int w, int h){
            super(x, y, w, h);
        }
    }

    private class MouseListener extends MouseAdapter{
        @Override
        public void mouseMoved(MouseEvent e){
            drawPanel.p1.y = e.getY();
        }
    }

    private class KeyListener extends KeyAdapter{
        @Override 
        public void keyPressed(KeyEvent e){
            if (e.getKeyCode() == KeyEvent.VK_UP){
                drawPanel.p2.y -= 13;
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN){
                drawPanel.p2.y += 13;
            }
        }
    }

    private class Ball extends Rectangle{
        int vx, vy, r;
        public Ball(int x, int y, int r){
            super(x, y, r, r);
            this.r = r; 
            vx = 4; 
            vy = 4; 
        }

        public void move(){
            super.x += vx;
            super.y += vy;
            if (x < 0 ){
                JOptionPane.showMessageDialog(null, "Game Over! Score: " + score);
                System.exit(0);
            } else if (x + r >= 1000){
                score++; 
                super.x = 500;
                super.y = 300;
                this.vx *= -1;
            }
            if (y < 0){
                this.vy *= -1;
            } else if (y + r >= 600){
                this.vy *= -1;
            }
        }

        public void checkCollision(){
            if (this.intersects(drawPanel.p1) || this.intersects(drawPanel.p2)){
                this.vx *= -1; 
            }
        }
    }
}
