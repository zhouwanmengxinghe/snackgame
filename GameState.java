//GameState class manages game state, score and lives
 
public class GameState {
    private boolean gameOver; // Whether the game is over
    private boolean gameStarted; // Whether the game has started
    private int lives; // Lives count
    private int score; // Score
    private final int initialLives; // Initial lives count
  
    public GameState(int initialLives) {
        this.initialLives = initialLives;
        reset();
    }
    
    
     // Reset game state
     
    public void reset() {
        gameOver = false;
        gameStarted = false;
        lives = initialLives;
        score = 0;
    }
    
    
     // Start the game
     
    public void startGame() {
        if (!gameStarted) {
            gameStarted = true;
        }
    }
    
    
     // Handle collision
     
    public boolean handleCollision() {
        lives--;
        if (lives <= 0) {
            gameOver = true;
            return true;
        }
        return false;
    }
    
    // Increase score
    public void increaseScore() {
        score++;
    }
    
    // Increase bonus score
    public void increaseBonusScore() {
        score += 2;
    }
    
    // Increase lives
    public void increaseLives() {
        lives++;
    }
    
    
    public boolean isGameOver() { return gameOver; }
    public boolean isGameStarted() { return gameStarted; }
    public int getLives() { return lives; }
    public int getScore() { return score; }
    
    
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
}