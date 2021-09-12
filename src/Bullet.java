import bagel.util.Point;

/**
 * Bullet class - Each Player has 1 bullet, bullet is not deleted and reassigned it is just set as non visible or visible
 * A bullet shot from a Player to a Zombie
 * Contains shoot method which allows a bullet to be shot from a start position to a target
 * Contains bullet move method, which has functionality to set a zombie to dead
 */
public class Bullet extends MoveableSprite{

    private static final int BULLETSTEP = 25;
    private static final int BULLETMEET = 25;

    /**
     * Bullet constructor, for initial creation of bullet
     */
    public Bullet() {
        super(0, 0, "res/images/shot.png", BULLETSTEP); // Calls moveableSprite constructor, (0,0) is default position.
        setAlive(false); // Bullet initially not alive
    }

    /**
     * Sets start position and direction of bullet
     * @param start = Start point of bullet - Always a player
     * @param end = End point of bullet - Always a zombie
     */
    public void shoot(Point start, Point end) {
        setPos(start.x, start.y); // Set start pos
        pointTo(end); // Point to end pos
        setAlive(true); // bullet alive
    }

    /**
     * If bullet isAlive updates bullet position and appends output csv
     * @param targetZombie = zombie sprite that is the current target of bullet
     * @param output = CsvWriter class to append bullet location to outputCSV
     */
    public void move(Sprite targetZombie, CsvWriter output) {

        // if Bullet alive, move bullet and write bullet location
        if (isAlive()) {
            move();
            output.write(getPos());

            // if zombie shot dead, set bullet and zombie dead
            if (meet(targetZombie, BULLETMEET)) {
                setAlive(false);
                targetZombie.setAlive(false);
            }

        }
    }

}
