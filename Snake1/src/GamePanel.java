import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import java.util.Random;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 500;
    static final int SCREEN_HEIGHT = 500;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
    static final int DELAY = 100;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 3;
    int appleEaten;
    int appleX;
    int appleY;
    char direction = 'D';
    boolean running = false;
    Timer timer;
    Random random;
    
    
    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        
        startGame();
    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    
    public void draw(Graphics g) {
        if (running){
            //Draw Grid
            g.setColor(Color.GRAY);
            for(int i=0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
            }
            for(int i=0; i < SCREEN_WIDTH / UNIT_SIZE; i++) {
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
            //Draw Apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            //Draw Snake
            for(int i = 0; i < bodyParts; i++){
                if(i == 0){
                    g.setColor(Color.yellow);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                } 
                else {
                    g.setColor(new Color(180, 180, 180));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.green);
            g.setFont( new Font("Cambria",Font.BOLD, 40)); 
            FontMetrics font = getFontMetrics(g.getFont());
            g.drawString("Score: " + appleEaten, (SCREEN_WIDTH - font.stringWidth("Score: "+appleEaten))/2, 35);        
        }
        else{
            gameOver(g);
        }  
    }
    
    public void newApple(){
        appleX = random.nextInt((int) SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt((int) SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        
    }
    
    public void move() {
        for(int i=bodyParts; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        
        switch(direction){
            case 'U':
                y[0] -= UNIT_SIZE;
                break;
            case 'D':
                y[0] += UNIT_SIZE;
                break;
            case 'L':
                x[0] -= UNIT_SIZE;
                break;
            case 'R':
                x[0] += UNIT_SIZE;
                break;
        }  
    }
    
    public void checkApple() {
        if((x[0] == appleX) && (y[0] == appleY)){
            bodyParts++;
            appleEaten++;
            newApple();
        }
    }
    
    public void checkCollisions() {
        // check if head colides with the body
        for(int i = bodyParts; i > 0; i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
                break;
            }
        }
        // check head touch boders
        //left
        if(x[0] < 0) running = false;
        //right
        if(x[0] > SCREEN_WIDTH) running = false;
        //top
        if(y[0] < 0) running = false;
        //bottom
        if(y[0] > SCREEN_HEIGHT) running = false; 
        
        if(!running) timer.stop();
    }
    
    public void gameOver(Graphics g) {
        //SoDiem
	g.setColor(Color.green);
	g.setFont( new Font("Cambria",Font.BOLD, 40)); 
	FontMetrics font = getFontMetrics(g.getFont());
	g.drawString("Score: "+appleEaten, (SCREEN_WIDTH - font.stringWidth("Score: "+appleEaten))/2,35);
        
        //GameOver        
	g.setColor(Color.red);
	g.setFont( new Font("Ink free",Font.BOLD, 90));
	FontMetrics font2 = getFontMetrics(g.getFont());
	g.drawString("Game Over", (SCREEN_WIDTH - font2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }
    
    public class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') direction = 'L';
                    break;
                
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') direction = 'R';
                    break;
                    
                case KeyEvent.VK_UP:
                    if(direction != 'D') direction = 'U';
                    break;
                    
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') direction = 'D';
                    break;    
            }
        }
        
    }
}
