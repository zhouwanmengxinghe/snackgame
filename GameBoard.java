import java.awt.Color;
import java.awt.Image;


 //GameBoard class is responsible for managing the game interface rendering
 
public class GameBoard {
    private final int width; // Game width
    private final int height; // Game height
    private final int cellSize; // Cell size
    private final GameEngine engine; // Game engine
    private final Image headImage; // Snake head image
    private final Image dotImage; // Snake body image
    private final Image appleImage; // Apple image
    private boolean showRules = false; // Whether to show rules
    
    // Constructor
    public GameBoard(int width, int height, int cellSize, GameEngine engine) {
        this.width = width;
        this.height = height;
        this.cellSize = cellSize;
        this.engine = engine;
        
        // Load images
        this.headImage = engine.loadImage("head.png");
        this.dotImage = engine.loadImage("dot.png");
        this.appleImage = engine.loadImage("apple.png");
    }
    
    // Draw game start screen
    public void drawStartScreen() {
        engine.changeColor(Color.BLACK);
        engine.drawText(150, 250, "Press SPACE to Start", "Arial", 20);
        
        // Draw rules button
        
        engine.changeColor(Color.BLACK);
        engine.drawText(220, 320, "Rules", "Arial", 16);
        
        // Display rules
        if (showRules) {
            engine.changeColor(new Color(0, 0, 0, 200));
            engine.drawSolidRectangle(100, 100, 300, 200);
            engine.changeColor(Color.WHITE);
            engine.drawText(120, 130, "Game Rules:", "Arial", 18);
            engine.drawText(120, 160, "1. Green Apple: +1 point", "Arial", 16);
            engine.drawText(120, 190, "2. Red Heart: +1 life", "Arial", 16);
            engine.drawText(120, 220, "3. Yellow Heart: +2 points", "Arial", 16);
            engine.drawText(120, 250, "Game Over: Hit wall or self", "Arial", 16);
            
            // Draw return button
            engine.changeColor(Color.WHITE);
            engine.drawText(320, 120, "Return", "Arial", 16);
        }
    }

    public boolean isClickOnReturnButton(int x, int y) {
        return x >= 300 && x <= 400 && y >= 100 && y <= 120;
    }
    
    public void toggleRules() {
        showRules = !showRules;
    }
    
    public boolean isClickOnRulesButton(int x, int y) {
        return x >= 200 && x <= 300 && y >= 300 && y <= 330;
    }
    
    // Draw game over screen
    public void drawGameOverScreen(int score) {
        engine.changeColor(Color.RED);
        engine.drawText(180, 230, "Game Over!", "Arial", 30);
        engine.changeColor(Color.WHITE);
        engine.drawText(140, 270, "Score: " + score, "Arial", 20);
        engine.drawText(120, 310, "Press SPACE to Restart", "Arial", 20);
    }
    
    // Draw game interface
    public void drawGame(Snake snake, Food food, int score, int lives) {
        // Clear background
        engine.changeBackgroundColor(Color.BLACK);
        engine.clearBackground(width, height);
        
        // Draw grid
        drawGrid();
        
        // Draw apple
        engine.drawImage(appleImage, food.getAppleX() * cellSize, 
                        food.getAppleY() * cellSize, cellSize, cellSize);
        
        // Draw life item
        if (food.isHeartActive()) {
            drawHeart(food.getHeartX(), food.getHeartY());
        }
        
        // Draw bonus item
        if (food.isBonusActive()) {
            drawBonus(food.getBonusX(), food.getBonusY());
        }
        
        // Draw snake
        for (int i = 0; i < snake.getLength(); i++) {
            if (i == 0) {
                // Draw snake头
                engine.drawImage(headImage, snake.getX(i) * cellSize, 
                                snake.getY(i) * cellSize, cellSize, cellSize);
            } else {
                // Draw snake身
                engine.drawImage(dotImage, snake.getX(i) * cellSize, 
                                snake.getY(i) * cellSize, cellSize, cellSize);
            }
        }
        
        // Display score and lives
        engine.changeColor(Color.WHITE);
        engine.drawText(10, 20, "Score: " + score, "Arial", 16);
        engine.drawText(width - 100, 20, "Lives: " + lives, "Arial", 16);
    }
    
    // Draw grid
    private void drawGrid() {
        engine.changeColor(Color.DARK_GRAY);
        
        // Draw vertical lines
        for (int x = 0; x <= width / cellSize; x++) {
            engine.drawLine(x * cellSize, 0, x * cellSize, height, 1);
        }
        
        // Draw horizontal lines
        for (int y = 0; y <= height / cellSize; y++) {
            engine.drawLine(0, y * cellSize, width, y * cellSize, 1);
        }
    }
    
    // Draw life item
    private void drawHeart(int x, int y) {
        engine.changeColor(Color.RED);
        int pixelX = x * cellSize;
        int pixelY = y * cellSize;
        int pixelSize = cellSize / 5;
        
        // Draw upper part of heart
        engine.drawSolidRectangle(pixelX + pixelSize, pixelY + pixelSize, pixelSize * 2, pixelSize);
        engine.drawSolidRectangle(pixelX + pixelSize * 2, pixelY + pixelSize, pixelSize * 2, pixelSize);
        engine.drawSolidRectangle(pixelX, pixelY + pixelSize * 2, pixelSize, pixelSize);
        engine.drawSolidRectangle(pixelX + pixelSize * 4, pixelY + pixelSize * 2, pixelSize, pixelSize);
        
        // Draw lower part of heart
        for (int i = 0; i < 3; i++) {
            engine.drawSolidRectangle(pixelX + pixelSize * (i + 1), 
                                    pixelY + pixelSize * 3, pixelSize, pixelSize);
        }
        engine.drawSolidRectangle(pixelX + pixelSize * 2, pixelY + pixelSize * 4, pixelSize, pixelSize);
    }
    
    // Draw bonus item
    private void drawBonus(int x, int y) {
        engine.changeColor(Color.YELLOW);
        int pixelX = x * cellSize;
        int pixelY = y * cellSize;
        int pixelSize = cellSize / 5;
        
        // Draw upper part of heart
        engine.drawSolidRectangle(pixelX + pixelSize, pixelY + pixelSize, pixelSize * 2, pixelSize);
        engine.drawSolidRectangle(pixelX + pixelSize * 2, pixelY + pixelSize, pixelSize * 2, pixelSize);
        engine.drawSolidRectangle(pixelX, pixelY + pixelSize * 2, pixelSize, pixelSize);
        engine.drawSolidRectangle(pixelX + pixelSize * 4, pixelY + pixelSize * 2, pixelSize, pixelSize);
        
        // Draw lower part of heart
        for (int i = 0; i < 3; i++) {
            engine.drawSolidRectangle(pixelX + pixelSize * (i + 1), 
                                    pixelY + pixelSize * 3, pixelSize, pixelSize);
        }
        engine.drawSolidRectangle(pixelX + pixelSize * 2, pixelY + pixelSize * 4, pixelSize, pixelSize);
    }
}