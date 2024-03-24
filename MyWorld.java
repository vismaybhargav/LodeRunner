import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */
    public static final int GRID_SIZE = 24;
    public MyWorld()
    {    
        super(600, 480, 1);

        GreenfootImage bg = new GreenfootImage(600, 480);
        bg.setColor(Color.BLACK);
        bg.fill();

        if(false) {
            bg.setColor(Color.GREEN);
            for(int i = 0; i < getWidth() / GRID_SIZE; i++) {
                for(int j = 0; j < getHeight() / GRID_SIZE; j++) {
                    bg.drawLine(0, j * GRID_SIZE, getWidth(), j * GRID_SIZE);
                    bg.drawLine(i * GRID_SIZE, 0, i * GRID_SIZE, getHeight());
                }
            }
        }
        setBackground(bg);

        drawRowOfWalls(25, 0, getHeight() - GRID_SIZE);
        drawGridOfWalls(3, 3, 0, getHeight() - GRID_SIZE * 4);
        drawGridOfWalls(3, 3, getWidth() - GRID_SIZE * 3, getHeight() - GRID_SIZE * 4);
        drawRowOfWalls(12, 0, getHeight() - GRID_SIZE * 5);
        drawRowOfWalls(3, GRID_SIZE * 5, getHeight() - GRID_SIZE * 10);
        drawRowOfWalls(6, 0, getHeight() - GRID_SIZE * 17);
        
        Player player = new Player(true);
        addObject(player, getWidth() / 2, getHeight() / 2);
    }
    public void drawRowOfWalls(int width, int startX, int startY) {
        int runningX = startX + GRID_SIZE / 2;

        for(int i = 0; i < width; i++){
            addObject(new Wall(), runningX, startY + GRID_SIZE  / 2);
            runningX += GRID_SIZE;
        }
    }

    public void drawGridOfWalls(int rows, int cols, int startX, int startY){
        int runningX = startX + GRID_SIZE / 2;
        int runningY = startY + GRID_SIZE / 2;

        for(int i = 0; i < cols; i++){
            for(int j = 0; j < rows; j++){
                addObject(new Wall(), runningX, runningY);
                runningX += GRID_SIZE;
            }

            runningY += GRID_SIZE;
            runningX = startX + GRID_SIZE / 2;
        }
    }
}
