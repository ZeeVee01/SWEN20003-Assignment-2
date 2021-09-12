import bagel.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 * ShadowTreasure bagel game
 */
public class ShadowTreasure extends AbstractGame {

    // Environment and output locations
    private static final String ENVIRONMENT = "res/IO/environment.csv";
    private static final String OUTPUT = "res/IO/output.csv";

    // Background image
    private final Image background = new Image("res/images/background.png");

    // list of singular entities
    private Player player;
    private Sprite treasure;

    // Creates empty zombie and sandwich array
    private final SpriteArray zArray = new SpriteArray("res/images/zombie.png");
    private final SpriteArray sArray = new SpriteArray("res/images/sandwich.png");

    // Game Variables
    private static final int TPU = 10; // ticks per update
    private int tick; // int for counting ticks
    private boolean endOfGame; // end of game indicator

    // CsvWriter class
    private final CsvWriter outputCsv = new CsvWriter();


    /**
     * Getter for outputCsv to store bullet locations
     * @return outputCsv = CsvWriters class to write to outputCsv
     */
    public CsvWriter getOutputCsv() {
        return outputCsv;
    }

    /** Initializes ShadowTreasure variables and environment */
    public ShadowTreasure() throws IOException {
        loadEnvironment(ENVIRONMENT);
        tick = 0;
        endOfGame = false;
    }

    /** Treasure object getter
     * @return treasure object
     */
    public Sprite getTreasure() {
        return treasure;
    }

    /**
     * Returns closest zombie, else returns null if no zombie exist
     * @return closest zombie to player
     */
    public Sprite getZombie() {
        return zArray.getClosestSprite(player);
    }

    /**
     * Returns closest sandwich, else returns null if no sandwich exist
     * @return closest sandwich to player
     */
    public Sprite getSandwich() {
        return sArray.getClosestSprite(player);
    }

    /**
     * Sets end of game to true
     */
    public void setEndOfGame() {
        endOfGame = true;
    }

    /**
     * Load from input file
     */
    private void loadEnvironment(String filename) {
        String row;

        // try method, as code would not compile otherwise, expect every input to be valid, hence empty catch
        try (BufferedReader csvReader = new BufferedReader(new FileReader(filename))){
            while ((row = csvReader.readLine()) != null) {

                // Stores csv line in data and removes special characters
                String[] data = row.split(",");
                data[0] = data[0].replaceAll("[^\\w\\s]", "");
                double x = Double.parseDouble(data[1]);
                double y = Double.parseDouble(data[2]);

                // Takes entity type and sets initial values from csv
                switch (data[0]) {

                    // Singular object initializers
                    case "Player" -> this.player = new Player(x, y, Integer.parseInt(data[3]));
                    case "Treasure" -> this.treasure = new Sprite(x, y, "res/images/treasure.png");

                    // Array object Initializers
                    case "Zombie" -> zArray.addSprite(x, y);
                    case "Sandwich" -> sArray.addSprite(x,y);

                }
            }
        }
        catch (IOException e) {
            // No error expected
        }

    }

    /**
     * Performs a state update.
     * @param input = keyboard input
     */
    @Override
    public void update(Input input) {

        // end game if true or escape pressed and export csv
        if (endOfGame || input.wasPressed(Keys.ESCAPE)) {
            outputCsv.export(OUTPUT);
            Window.close();
        }
        else {

            // if update tick
            if (tick % TPU == 0) {

                // do update
                player.update(this);
            }
            tick++;

            // draw all objects
            background.drawFromTopLeft(0,0);
            player.draw();
            treasure.draw();
            sArray.draw();
            zArray.draw();

        }
    }

    /**
     * The entry point for the program
     */
    public static void main(String[] args) throws IOException {
        ShadowTreasure game = new ShadowTreasure();
        game.run();
    }

}