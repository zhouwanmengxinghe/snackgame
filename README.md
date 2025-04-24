# Snake Game


## Compile and Run
```bash
javac *.java
java SnakeGame
```
SnackeGame.java is the main class

## Additional Features
1. Score system - Real-time display of current score
2. Life system - Initial 1 life, can be replenished by red heart prop
3. Double score prop - Yellow heart gives 2 points
4. Dynamic speed mechanism - Snake's speed increases as its body grows
5. Start/End menu - Includes game rules, displays score after game over, press space to restart
6. Mouse interaction - Click "Rules" to view game rules

## Controls
- Direction keys: Control snake movement
- Space: Start game/Restart
- Mouse click: Click "Rules" in menu to view rules

## Special Props
- Green apple: +1 point
- Red heart: +1 life
- Yellow heart: +2 points

## Game Mechanics
- Colliding with walls or self consumes life
- Game ends when life runs out
- Snake moves faster as its body grows, maximum length is 20