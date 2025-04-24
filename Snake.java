import java.util.ArrayList;


public class Snake {
    // Direction constants
    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    
    private ArrayList<Integer> snakeX; // Snake body X coordinates
    private ArrayList<Integer> snakeY; // Snake body Y coordinates
    private int direction; // Snake direction
    private int length; // Snake length
    private final int maxLength; 
    private final int initialLength; 
    private double moveDelay; 
    private final double initialMoveDelay; 
    private final double minMoveDelay; 
    
    
     // Constructor
    
    public Snake(int maxLength, int initialLength, double initialMoveDelay, double minMoveDelay) {
        this.maxLength = maxLength;
        this.initialLength = initialLength;
        this.initialMoveDelay = initialMoveDelay;
        this.minMoveDelay = minMoveDelay;
        init();
    }
 
    public void init() {
        snakeX = new ArrayList<>();
        snakeY = new ArrayList<>();
        length = initialLength;
        direction = RIGHT;
        moveDelay = initialMoveDelay;
    }
    
    // Set initial position of the snake
     
    public void setInitialPosition(int startX, int startY) {
        snakeX.clear();
        snakeY.clear();
        
        // Add snake head
        snakeX.add(startX);
        snakeY.add(startY);
        
        // Add snake body
        for (int i = 1; i < length; i++) {
            snakeX.add(startX - i);
            snakeY.add(startY);
        }
    }
    
    //move
    public void move() {
        // move boddy
        for (int i = length - 1; i > 0; i--) {
            snakeX.set(i, snakeX.get(i - 1));
            snakeY.set(i, snakeY.get(i - 1));
        }
        
        
        switch (direction) {
            case UP:
                snakeY.set(0, snakeY.get(0) - 1);
                break;
            case RIGHT:
                snakeX.set(0, snakeX.get(0) + 1);
                break;
            case DOWN:
                snakeY.set(0, snakeY.get(0) + 1);
                break;
            case LEFT:
                snakeX.set(0, snakeX.get(0) - 1);
                break;
        }
    }
    
    
     // Increase snake length
     
    public void grow() {
        if (length < maxLength) {
            length++;
            snakeX.add(snakeX.get(length - 2));
            snakeY.add(snakeY.get(length - 2));
            updateSpeed();
        }
    }
    
    
     // Update move speed
     
    private void updateSpeed() {
        double speedIncrease = (length - initialLength) * 0.05;
        moveDelay = Math.max(minMoveDelay, initialMoveDelay - speedIncrease);
    }
    
    
     // Check collision with coordinates
     
    public boolean checkCollision(int x, int y) {
        for (int i = 0; i < length; i++) {
            if (snakeX.get(i) == x && snakeY.get(i) == y) {
                return true;
            }
        }
        return false;
    }
    
   
    public int getHeadX() { return snakeX.get(0); }
    public int getHeadY() { return snakeY.get(0); }
    public int getX(int index) { return snakeX.get(index); }
    public int getY(int index) { return snakeY.get(index); }
    public int getLength() { return length; }
    public int getDirection() { return direction; }
    public double getMoveDelay() { return moveDelay; }
    
    public void setDirection(int newDirection) {
        // Prevent 180-degree turn
        if ((direction == UP && newDirection != DOWN) ||
            (direction == DOWN && newDirection != UP) ||
            (direction == LEFT && newDirection != RIGHT) ||
            (direction == RIGHT && newDirection != LEFT)) {
            direction = newDirection;
        }
    }
}