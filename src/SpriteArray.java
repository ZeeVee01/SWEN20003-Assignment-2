import java.util.ArrayList;

/**
 * Abstract SpriteArray class
 * Contains an ArrayList to store sprites, and the image of sprite
 * Has methods to add Sprite to the array, to find closest sprite in the array to a Player, and to draw sprites in array
 */
public class SpriteArray {

    private final ArrayList<Sprite> array = new ArrayList<>();
    private final String spriteImage;

    /**
     * Default constructor
     * @param spriteImage = location of this sprite image
     */
    public SpriteArray(String spriteImage){
        this.spriteImage = spriteImage;
    }

    /**
     * Adds new sprite to spriteArray
     * @param x = new Sprites x location
     * @param y = new Sprites y location
     */
    public void addSprite(double x, double y) {

        // Creates new sprite location and image
        Sprite sprite = new Sprite(x, y, spriteImage);

        // Adds sprite to array
        array.add(sprite);
    }

    /**
     * Iterates through spriteArray and calls draw method on each sprite
     */
    public void draw() {
        for(Sprite s: array) {
            s.draw();
        }
    }

    /**
     * takes any ArrayList<Sprite> and returns closest sprite of that type to player
     * @param player = player to find closest object to
     * @return closestSprite = closestSprite to player or null if no sprite exists
     */
    public Sprite getClosestSprite(Player player) {
        double minDistance = Double.POSITIVE_INFINITY; // infinity value
        Sprite closestSprite = null;

        // for sprite in array
        for(Sprite s: array) {

            // if distance from player to sprite is < min distance and sprite alive
            if (minDistance > player.distanceTo(s.getPos()) && s.isAlive()) {

                // reassign min distance and closest sprite
                minDistance = player.distanceTo(s.getPos());
                closestSprite = s;
            }
        }

        return closestSprite;
    }

}
