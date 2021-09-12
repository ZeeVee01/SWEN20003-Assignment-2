import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import bagel.util.Point;

/**
 * CsvWriter class to write bullet location to csv
 */
public class CsvWriter {

    private final ArrayList<Point> csvArray;

    // for rounding double number; use this to print the location of the player
    private static final DecimalFormat df = new DecimalFormat("0.00");

    /**
     * Constructor to initialize csvArray
     */
    public CsvWriter(){
        csvArray = new ArrayList<>();
    }

    /**
     * Adds the given point to csvArray
     * @param point = point to add to csvArray
     */
    public void write(Point point) {
        csvArray.add(point);
    }

    /**
     * Writes csvArray to output file
     * @param filename = location of output csv
     */
    public void export(String filename) {

        // Try and create new file
        try (FileWriter output = new FileWriter(filename)) {

            // for point in csvArray write to output
            for (Point point : csvArray) {
                output.write(df.format(point.x) + "," + df.format(point.y) + "\n");
            }

        }
        catch (IOException e) {
            // No error expected
        }
    }



}
