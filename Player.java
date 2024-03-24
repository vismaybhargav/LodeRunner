import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)


/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player extends Actor {
    private int pWidth;
    private int pHeight;
    private static final int GRID_SIZE = 24;
    private PlayerState state = PlayerState.STAND;
    private boolean debug;
    private int dx;
    private int dy;
    private int frameCount = 0;
    /** Counts the current index for the running animation */
    private int runAnimIdx = 0;
    public Player(boolean debug) {
        pWidth = getImage().getWidth();
        pHeight = getImage().getHeight();
        this.debug = debug;
    }

    /**
     * Act - do whatever the Player wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        // initialize frame code
        dx = 0;
        dy = 0;

        if(onPlatform()) {
            state = PlayerState.STAND;
            if(Greenfoot.isKeyDown("left")) {
                dx = -3;
                dy = 0;
                state = PlayerState.GROUND;
            }

            if(Greenfoot.isKeyDown("right")) {
                dx = 3;
                dy = 0;
                state = PlayerState.GROUND;
            }
        } else {
            state = PlayerState.FALL;
            dx = 0;
            dy = 3;
        }

        updateImage();
        if (debug) drawDebugSquare();
        checkBoundsAABB();
        setLocation(getX() + dx, getY() + dy);
    }

    private void checkBoundsAABB() {
        // Check to make sure that the AABB doesn't go out of bounds or encounters an object, if it does, set the dx to 0
        if((dx > 0 && (getObjectR(Wall.class) != null || getX() > getWorld().getWidth() - GRID_SIZE / 4))
                || (dx < 0 && (getObjectL(Wall.class) != null || getX() < GRID_SIZE / 4)))
        {
            dx = 0;
        }

        // TODO: Setup y-axis constraints
    }

    private boolean onPlatform() {
        return getObjectB(Wall.class) != null || getObjectBR(Wall.class) != null || getObjectBL(Wall.class) != null;
    }

    private void updateImage() {
        switch (state) {
            case STAND -> setImage("player_stand.png");
            case FALL -> setImage("player_fall.png");
            case GROUND -> {
                /*
                    If you want a number incrementing and going back down do the following
                    number = (number + n) % <max_value + n>
                    in this case our max value is 3, so I input 4 for <max_value + n>
                    and I make (number + n) -> frameCount + 1... Cool trick right? Thanks Cannon295!
                 */
                frameCount = (frameCount + 1) % 4;
                if(frameCount == 3) animateRunning();
            }
        }

        pWidth = getImage().getWidth();
        pHeight = getImage().getHeight();
    }

    private void animateRunning() {
        GreenfootImage image = new GreenfootImage("player_run_0" + runAnimIdx + ".png");
        if(dx < 0) image.mirrorHorizontally();
        setImage(image);
        runAnimIdx = (runAnimIdx + 1) % 4; // Magic Number (4) is total amount of running images
    }

    private void drawDebugSquare() {
        GreenfootImage gImg = new GreenfootImage(getImage());
        gImg.setColor(Color.YELLOW);
        gImg.drawRect(0, 0, pWidth - 1, pHeight - 1);
        setImage(gImg);
    }

    @SuppressWarnings("unchecked")
    private <A extends Actor> A getObjectB(Class<A> cls) {
        return (A) getOneObjectAtOffset(0, GRID_SIZE / 2, cls);
    }
    @SuppressWarnings("unchecked")
    private <A extends Actor> A getObjectBR(Class<A> cls) {
        return (A) getOneObjectAtOffset(GRID_SIZE / 4, GRID_SIZE / 2, cls);
    }

    @SuppressWarnings("unchecked")
    private <A extends Actor> A getObjectBL(Class<A> cls) {
        return (A) getOneObjectAtOffset(-GRID_SIZE / 4, GRID_SIZE / 2, cls);
    }

    @SuppressWarnings("unchecked")
    private <A extends Actor> A getObjectT(Class<A> cls) {
        return (A) getOneObjectAtOffset(0, -GRID_SIZE / 2, cls);
    }

    @SuppressWarnings("unchecked")
    private <A extends Actor> A getObjectTR(Class<A> cls) {
        return (A) getOneObjectAtOffset(GRID_SIZE / 4, -GRID_SIZE / 2, cls);
    }

    @SuppressWarnings("unchecked")
    private <A extends Actor> A getObjectTL(Class<A> cls) {
        return (A) getOneObjectAtOffset(-GRID_SIZE / 4, -GRID_SIZE / 2, cls);
    }

    @SuppressWarnings("unchecked")
    private <A extends Actor> A getObjectL(Class<A> cls) {
        // Not sure why but we have to subtract 1 to make collisions look correct
        return (A) getOneObjectAtOffset(-GRID_SIZE / 4 - 1, 0, cls);
    }
    @SuppressWarnings("unchecked")
    private <A extends Actor> A getObjectR(Class<A> cls) {
        return (A) getOneObjectAtOffset(GRID_SIZE / 4, 0, cls);
    }
}
