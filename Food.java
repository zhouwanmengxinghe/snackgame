import java.util.Random;

// Food class
public class Food {
    private int appleX, appleY; // Apple position
    private int heartX, heartY; // Heart item position
    private boolean heartActive; // Whether the heart item is active
    private double heartTimer; // Heart item timer
    private double nextHeartSpawnDelay; // Next heart item spawn delay
    private double minHeartSpawnDelay; // Minimum heart item spawn delay
    private double maxHeartSpawnDelay; // Maximum heart item spawn delay
    private int bonusX, bonusY; // Bonus item position
    private boolean bonusActive; // Whether the bonus item is active
    private double bonusTimer; // Bonus item timer
    private double nextBonusSpawnDelay; // Next bonus item spawn delay
    private double minBonusSpawnDelay = 10.0; // Minimum bonus item spawn delay
    private double maxBonusSpawnDelay = 20.0; // Maximum bonus item spawn delay
    private int gridWidth; // Grid width
    private int gridHeight; // Grid height
    
    // Constructor
    public Food(int gridWidth, int gridHeight, double minHeartSpawnDelay, double maxHeartSpawnDelay) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.minHeartSpawnDelay = minHeartSpawnDelay;
        this.maxHeartSpawnDelay = maxHeartSpawnDelay;
        this.heartTimer = 0;
        this.heartActive = false;
        this.bonusTimer = 0;
        this.bonusActive = false;
        setNextHeartSpawnDelay();
        setNextBonusSpawnDelay();
    }
    
    // Place apple
    public void placeApple(Snake snake) {
        Random random = new Random();
        boolean validPosition = false;
        
        while (!validPosition) {
            appleX = random.nextInt(gridWidth);
            appleY = random.nextInt(gridHeight);
            
            // Ensure apple doesn't appear on snake
            validPosition = !snake.checkCollision(appleX, appleY);
        }
    }
    
    // Set next heart item spawn delay
    private void setNextHeartSpawnDelay() {
        Random random = new Random();
        nextHeartSpawnDelay = minHeartSpawnDelay + 
            random.nextDouble() * (maxHeartSpawnDelay - minHeartSpawnDelay);
    }
    
    // Place heart item
    private void placeHeart(Snake snake) {
        Random random = new Random();
        boolean validPosition = false;
        
        while (!validPosition) {
            heartX = random.nextInt(gridWidth);
            heartY = random.nextInt(gridHeight);
            
            // Ensure heart item doesn't appear on snake or apple
            validPosition = !(heartX == appleX && heartY == appleY) && 
                           !snake.checkCollision(heartX, heartY);
        }
        heartActive = true;
    }
    
    // Update game state
    public void update(double dt, Snake snake) {
        if (!heartActive) {
            heartTimer += dt;
            if (heartTimer >= nextHeartSpawnDelay) {
                heartTimer = 0;
                placeHeart(snake);
                setNextHeartSpawnDelay();
            }
        }
        if (!bonusActive) {
            bonusTimer += dt;
            if (bonusTimer >= nextBonusSpawnDelay) {
                bonusTimer = 0;
                placeBonus(snake);
                setNextBonusSpawnDelay();
            }
        }
    }
    
    // Check if apple is eaten
    public boolean checkAppleCollision(Snake snake) {
        return snake.getHeadX() == appleX && snake.getHeadY() == appleY;
    }
    
    // Check if heart item is eaten
    public boolean checkHeartCollision(Snake snake) {
        if (heartActive && snake.getHeadX() == heartX && snake.getHeadY() == heartY) {
            heartActive = false;
            heartTimer = 0;
            return true;
        }
        return false;
    }
    
  
    public int getAppleX() { return appleX; }
    public int getAppleY() { return appleY; }
    public int getHeartX() { return heartX; }
    public int getHeartY() { return heartY; }
    public boolean isHeartActive() { return heartActive; }
    
    // Set next bonus item spawn delay
    private void setNextBonusSpawnDelay() {
        Random random = new Random();
        nextBonusSpawnDelay = minBonusSpawnDelay + 
            random.nextDouble() * (maxBonusSpawnDelay - minBonusSpawnDelay);
    }
    
    // Set next bonus item spawn delay
    private void placeBonus(Snake snake) {
        Random random = new Random();
        boolean validPosition = false;
        
        while (!validPosition) {
            bonusX = random.nextInt(gridWidth);
            bonusY = random.nextInt(gridHeight);
            
            // Ensure bonus item doesn't appear on snake, apple or heart item
            validPosition = !(bonusX == appleX && bonusY == appleY) && 
                           !(bonusX == heartX && bonusY == heartY && heartActive) && 
                           !snake.checkCollision(bonusX, bonusY);
        }
        bonusActive = true;
    }
    
    // Check if bonus item is eaten
    public boolean checkBonusCollision(Snake snake) {
        if (bonusActive && snake.getHeadX() == bonusX && snake.getHeadY() == bonusY) {
            bonusActive = false;
            bonusTimer = 0;
            return true;
        }
        return false;
    }
    
    public int getBonusX() { return bonusX; }
    public int getBonusY() { return bonusY; }
    public boolean isBonusActive() { return bonusActive; }
}