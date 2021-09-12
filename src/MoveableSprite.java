import bagel.util.Point;

/**
 * Abstract class MoveableSprite
 * Extends sprite class, adds target setting and moving functionality to a sprite, and to check if another sprite is met
 * pointTo, normalize and meet adapted from project 1 sample solution
 */
abstract public class MoveableSprite extends Sprite {

    private final int stepSize;

    private double directionX;
    private double directionY;


    /**
     * Constructor which calls constructor in parent class
     * @param x = x coordinate of sprite
     * @param y = y coordinate of sprite
     * @param image = location image of Sprite is stored
     * @param stepSize = StepSize of this moveable sprite
     */
    public MoveableSprite(double x, double y, String image, int stepSize) {
        super(x, y, image);
        this.stepSize = stepSize;
    }


    /**
     * Returns distance between this and pos1
     * @param pos1 = target to find distance to
     * @return = distance to pos1
     */
    public double distanceTo(Point pos1) {
        return Math.sqrt(Math.pow(getPos().x - pos1.x, 2) + Math.pow(getPos().y - pos1.y, 2));
    }

    /**
     * Sets new location of Sprite based on step size and direction
     */
    public void move(){
        setPos(getPos().x + stepSize * directionX, getPos().y + stepSize * directionY);
    }

    /**
     * Checks for meet condition between this and sprite
     * @param sprite = Sprite to check for meet
     * @param meetCondition = distance that classifies as a meet
     * @return hasMet = boolean of whether or not Sprite is met
     */
    public boolean meet(Sprite sprite, int meetCondition) {
        boolean hasMet = false;

        // If valid sprite given
        if (sprite != null) {

            // if meetCondition distance is < distance to object. meet = true
            double distanceToObject = distanceTo(sprite.getPos());
            if (distanceToObject < meetCondition) {
                hasMet = true;
            }
        }

        return hasMet;

    }

    /**
     * Changes direction of this to new target
     * @param dest = object in which we are targeting
     */
    public void pointTo(Point dest) {

        // Sets direction to dest
        directionX = dest.x-this.getPos().x;
        directionY = dest.y-this.getPos().y;

        // Makes direction X and Y a unit vector and updates direction
        normalizeD();
    }

    /**
     * Normalizes the direction to be a unit vector
     */
    private void normalizeD() {
        double len = Math.sqrt(Math.pow(directionX,2)+Math.pow(directionY,2));
        directionX /= len;
        directionY /= len;
    }

}
