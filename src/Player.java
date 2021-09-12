import bagel.DrawOptions;
import bagel.Font;
import bagel.util.Colour;

/**
 * Player class, extends MoveableSprite as player is moveable
 * Algorithm 1 is contained within this class, along with its methods
 */
public class Player extends MoveableSprite{

    // Player interaction and move constants
    private static final int PLAYERSTEP = 10;
    private static final int PLAYERMEET = 50;
    private static final int PLAYERSHOOT = 150;

    // Interaction costs
    private static final int BULLETCOST = 3;
    private static final int SANDWICHENERGY = 5;

    // For drawing energy on screen
    private final Font FONT = new Font("res/font/DejaVuSans-Bold.ttf", 20);
    private final DrawOptions OPT = new DrawOptions();

    // Player energy attribute
    private int energy;

    // Initializes bullet and target zombie
    private final Bullet bullet;
    private Sprite targetZombie;


    /**
     * Player constructor
     * @param x = initial x location
     * @param y = initial y location
     * @param energy = initial energy
     */
    public Player(double x, double y, int energy) {
        super(x, y, "res/images/player.png", PLAYERSTEP); // Calls to MoveableSprite constructor
        this.energy = energy;

        // Creates new bullet, each player can only have 1 bullet
        bullet = new Bullet();
    }


    /**
     * Draw method for player,
     * method also includes bullet draw, as bullet is only accessible through player
     */
    public void draw() {
        super.draw(); // Calls Sprite draw method
        bullet.draw(); // Draws bullet

        // Draws energy level on screen
        FONT.drawString("energy: "+ energy,20,760, OPT.setBlendColour(Colour.BLACK));
    }


    /**
     * Algorithm 1 as in specification
     * Takes current location of all Sprites and decides where to move and shoot
     * @param tomb = ShadowTreasure tomb which contains methods to get treasure, sandwich, zombie and end conditions
     */
    public void update(ShadowTreasure tomb) {

        // If end conditions met end game
        if (checkEndConditions(tomb)) {
            endGame(tomb);
        }

        else {

            /* Player interacts with entities */

            // if Player meet sandwich, eat sandwich
            if (meet(tomb.getSandwich(), PLAYERMEET)) {
                eatSandwich(tomb.getSandwich());
            }
            // if Player within shooting range of zombie, and bullet is not alive, make zombie target and shoot
            else if (meet(tomb.getZombie(), PLAYERSHOOT)) {
                shootZombie(tomb.getZombie());
            }

            /* player sets moving direction */

            // if no zombies exist, point towards treasure
            if (tomb.getZombie() == null) {
                pointTo(tomb.getTreasure().getPos());
            }
            // if energy >= 3 point towards closest zombie
            else if (energy >= BULLETCOST) {
                pointTo(tomb.getZombie().getPos());
            }
            // else point to sandwich, if sandwich exists
            else if (tomb.getSandwich() != null) {
                pointTo(tomb.getSandwich().getPos());
            }

            // Moves player
            move();

            // Moves bullet, Shot dead is resolved inside bulletMove, and writes to outputCsv inside bullet
            bullet.move(targetZombie, tomb.getOutputCsv());

        }
    }

    /**
     * Sets specified sandwich as dead, and appends player energy
     * @param sandwich = sandwich to be eaten
     */
    private void eatSandwich(Sprite sandwich) {
        sandwich.setAlive(false);
        energy += SANDWICHENERGY;
    }

    /**
     * Sets specified zombie as target
     * Calls shoot method on bullet to set bullet in motion
     * @param zombie = zombie to be shot
     */
    private void shootZombie(Sprite zombie) {

        // If bullet not currently in motion
        if (!bullet.isAlive()) {

            // Set target zombie, set start and end location of bullet, decrease energy
            targetZombie = zombie;
            bullet.shoot(getPos(), targetZombie.getPos());
            energy -= BULLETCOST;
        }
    }

    /**
     * Checks shadowTreasure tomb for if end conditions are met
     * @param tomb = ShadowTreasure tomb so all sprites can be checked for end condition criteria
     * @return boolean of if end conditions are met
     */
    private boolean checkEndConditions(ShadowTreasure tomb) {

        // If treasure meet and all zombies dead or energy < 3, zombie exist, no sandwich, and no bullet in motion
        return (meet(tomb.getTreasure(), PLAYERMEET) && tomb.getZombie() == null) || ((energy < BULLETCOST) && tomb.getZombie() != null && tomb.getSandwich() == null && !bullet.isAlive());
    }

    /**
     * Prints end of game output and sets end of game to true
     * @param tomb = ShadowTreasure tomb so setEndOfGame method can be accessed
     */
    private void endGame(ShadowTreasure tomb) {
        System.out.print(energy);

        // If game ends because treasure is met, print success
        if (meet(tomb.getTreasure(), PLAYERMEET)) {
            System.out.print(",success!");
        }

        tomb.setEndOfGame();

    }

}
