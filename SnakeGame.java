import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

//SnakeGame is the main class of the game
 
public class SnakeGame extends GameEngine {
    // Game window width
    private static int w = 500;
    // Game window height
    private static int h = 500;
    // Cell size
    private static int cs = 25;
    // Grid width
    private static int gw = w / cs;
    // Grid height
    private static int gh = h / cs;
    // Maximum snake length
    private static int maxLen = 20;
    // Initial snake length
    private static int initLen = 3;
    // Initial lives
    private static int initLives = 1;
    // Initial move delay
    private static double initDelay = 0.5;
    // Minimum move delay
    private static double minDelay = 0.05;
    // Minimum heart item occur delay
    private static double minHeartDelay = 5.0;
    // Maximum heart item occur delay
    private static double maxHeartDelay = 15.0;
    
    // Snake object
    private Snake s;
    // Food object
    private Food f;
    // Game board
    private GameBoard b;
    // Game state
    private GameState st;
    // Move timer
    private double mt;
    
    @Override
    public void init() {
        // Initialize game components
        s = new Snake(maxLen, initLen, 
                     initDelay, minDelay);
        f = new Food(gw, gh, 
                   minHeartDelay, maxHeartDelay);
        b = new GameBoard(w, h, cs, this);
        st = new GameState(initLives);
        
        // Set initial position of the snake
        s.setInitialPosition(gw / 4, gh / 2);
        
        // Place the first apple
        f.placeApple(s);
        
        // Set window title
        if (mFrame != null) {
            mFrame.setTitle("Snake Game");
        }
    }
    
    @Override
    public void update(double dt) {
        if (st.isGameOver() || !st.isGameStarted()) {
            return;
        }
        
        // Update move timer
        mt += dt;
        
        // Update food status
        f.update(dt, s);
        
        // Update game state when timer exceeds move delay
        if (mt >= s.getMoveDelay()) {
            mt = 0;
            updateGameState();
        }
    }
    
  
    private void updateGameState() {
        // Move the snake
        s.move();
        
        // Check collision
        if (checkCollision()) {
            return;
        }
        
        // Check if food is eaten
        if (f.checkAppleCollision(s)) {
            s.grow();
            st.increaseScore();
            f.placeApple(s);
        }
        
        // Check if heart item is eaten
        if (f.checkHeartCollision(s)) {
            st.increaseLives();
        }
        
        // Check if bonus item is eaten
        if (f.checkBonusCollision(s)) {
            st.increaseBonusScore();
        }
    }
    
    private boolean checkCollision() {
        // Check if hit the wall
        if (s.getHeadX() < 0 || s.getHeadX() >= gw || 
            s.getHeadY() < 0 || s.getHeadY() >= gh) {
            handleCollision();
            return true;
        }
        
        // Check if hit itself (starting from the second body part)
        for (int i = 1; i < s.getLength(); i++) {
            if (s.getHeadX() == s.getX(i) && 
                s.getHeadY() == s.getY(i)) {
                handleCollision();
                return true;
            }
        }
        
        return false;
    }
    
    private void handleCollision() {
        if (st.handleCollision()) {
            return; // Game over
        }
        
        // Reset snake position
        s.setInitialPosition(gw / 4, gh / 2);
    }
    
    @Override
    public void paintComponent() {
        if (!st.isGameStarted()) {
            b.drawStartScreen();
            return;
        }
        
        if (st.isGameOver()) {
            b.drawGameOverScreen(st.getScore());
            return;
        }
        
        b.drawGame(s, f, st.getScore(), st.getLives());
    }
    
    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();
        
        // Space key to start/restart game
        if (key == KeyEvent.VK_SPACE) {
            if (st.isGameOver()) {
                st.reset();
                init();
            }
            st.startGame();
            return;
        }
        
        // If game hasn't started or is over, ignore direction keys
        if (!st.isGameStarted() || st.isGameOver()) {
            return;
        }
        
        // Direction keys control
        switch (key) {
            case KeyEvent.VK_UP:
                s.setDirection(Snake.UP);
                break;
            case KeyEvent.VK_RIGHT:
                s.setDirection(Snake.RIGHT);
                break;
            case KeyEvent.VK_DOWN:
                s.setDirection(Snake.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                s.setDirection(Snake.LEFT);
                break;
        }
    }
    
    @Override
    public void mousePressed(MouseEvent event) {
        if (!st.isGameStarted() && !st.isGameOver()) {
            // Check if rules button is clicked
        if (b.isClickOnRulesButton(event.getX(), event.getY())) {
                b.toggleRules();
            // Check if return button is clicked
        } else if (b.isClickOnReturnButton(event.getX(), event.getY())) {
                b.toggleRules();
            }
        }
    }

    public static void main(String[] args) {
        createGame(new SnakeGame(), 60, w, h);
    }
}