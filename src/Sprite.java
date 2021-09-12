import bagel.Image;
import bagel.util.Point;

/**
 * Basic Sprite class - non Abstract as Zombie and Sandwich are implemented as purely sprites in an array
 * Contains getters and setters for position, aliveStatus and spriteImage
 * Contains draw method for drawing Sprite
 */
public class Sprite {

    private Point pos;
    private boolean aliveStatus;
    private Image spriteImage;

    /**
     * Constructor, for creating sprite without image, used in moveable entity case
     * @param x = initial x location of sprite
     * @param y = initial y location of sprite
     */
    public Sprite(double x, double y) {
        pos = new Point(x,y);
        aliveStatus = true;
    }

    /**
     * Constructor for creating sprite with image
     * @param x = initial x location of sprite
     * @param y = initial y location of sprite
     * @param spriteImage = location of image for sprite
     */
    public Sprite(double x, double y, String spriteImage) {
        this(x, y);
        this.spriteImage = new Image(spriteImage);
    }

    /**
     * Getter for current position of sprite
     * @return pos = current Point of sprite
     */
    public Point getPos(){
        return pos;
    }

    /**
     * Set new position of sprite
     * @param x = new x position of sprite
     * @param y = new y position of sprite
     */
    public void setPos(double x, double y) {
        pos = new Point(x, y);
    }

    /**
     * Getter for alive status of sprite
     * @return aliveStatus = boolean of if sprite alive
     */
    public boolean isAlive(){
        return aliveStatus;
    }

    /**
     * Setter for alive status of sprite
     * @param aliveStatus = boolean of if sprite alive
     */
    public void setAlive(boolean aliveStatus) {
        this.aliveStatus = aliveStatus;
    }

    /**
     * Draw method, which draws sprite if aliveStatus is true
     */
    public void draw() {
        if (aliveStatus) {
            spriteImage.drawFromTopLeft(pos.x, pos.y);
        }
    }

}