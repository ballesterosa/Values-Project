// Antonio Ballesteros
// 9/14/2023
// Values Project

import java.util.*;
import java.io.*;

// This program provides a list of values to the user. The user then rates each
// value on a scale of 1-10. Those ratings are then outputted into a result file.
public class Values {

    // Main class that gets user input to map values to ratings and then organizes
    // the values in descending order.
    // Throws FileNotFoundException if file "values.txt" or "results.txt" is not
    // found. Throws IOException if the result.txt file cannot be written to.
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Map<String, Integer> valueToRating = new HashMap<>();

        putValuesInMap(valueToRating);

        Scanner input = new Scanner(System.in);

        System.out.println("Hello! Welcome to Value Rater." + System.lineSeparator() +
                           "Choose from the following options to get started");

        String textInput = "blah";

        while(!textInput.equalsIgnoreCase("q")) {
            System.out.println("Press \"q\" to quit" + System.lineSeparator() +
                           "Press \"s\" to start voting" + System.lineSeparator() +
                           "Press \"w\" to write the values and ratings to the \"results.txt\" file"
                           + System.lineSeparator() +
                           "Press \"l\" to load from the \"results.txt\" file");
            System.out.print("Enter: ");
            textInput = input.nextLine();
            if (textInput.equalsIgnoreCase("s")) {
                startVoteRound(valueToRating, input);
            } else if (textInput.equalsIgnoreCase("w")) {
                List<String> organizedValues = organizeValues(valueToRating);
                writeOutput(valueToRating, organizedValues);
            } else if (textInput.equalsIgnoreCase("l")) {
                loadFromFile(valueToRating);
            }
        }
    }

    // Private helper method that loads the values and ratings from the results file.
    // This is for when the results file is only partially complete.
    // Parameter:
    //  - Map<String, Integer> valueToRating - every value matched to it's rating
    // Throws FileNotFoundException if file"results.txt" is not found.
    private static void loadFromFile(Map<String, Integer> valueToRating)
        throws FileNotFoundException {

        File inputFile = new File("results.txt");
        Scanner input = new Scanner(inputFile);

        String[] parts = new String[2];
        while (input.hasNextLine()) {
            parts = input.nextLine().split(" : ");
            parts[0] = parts[0].trim();
            parts[1] = parts[1].trim();
            valueToRating.put(parts[0], Integer.parseInt(parts[1]));
        }
    }

    // Private helper method to organize the rated values from highest rating to
    // lowest rating.
    // Returns a list of the values that are organized.
    // Parameter:
    //  - Map<String, Integer> valueToRating - every value matched to it's rating
    private static List<String> organizeValues(Map<String, Integer> valueToRating) {
        int size = valueToRating.size();
        int currSize = 0;
        int currScore = 10;

        List<String> values = new ArrayList<>();

        while (currSize < size) {
            for (String value : valueToRating.keySet()) {
                if (valueToRating.get(value) == currScore) {
                    values.add(value);
                    currSize++;
                }
            }
            currScore--;
        }

        return values;
    }

    // Private helper method to put all the values from the values text file into
    // the map.
    // Parameter:
    //  - Map<String, Integer> valueToRating - every value matched to it's rating
    // Throws FileNotFoundException if file "values.txt" is not found.
    private static void putValuesInMap(Map<String, Integer> valueToRating)
        throws FileNotFoundException {

        File inputFile = new File("values.txt");
        Scanner input = new Scanner(inputFile);

        while (input.hasNextLine()) {
            valueToRating.put(input.nextLine().trim(), 0);
        }
    }

    // Private helper method that writes the current values and rating to the result
    // text file.
    // Parameters:
    //  - Map<String, Integer> valueToRating - every value matched to it's rating
    //  - List<String> values - organized list of values
    // Throws FileNotFoundException if file "results.txt" is not
    // found. Throws IOException if the "result.txt" file cannot be written to.
    private static void writeOutput(Map<String, Integer> valueToRating, List<String> values)
        throws FileNotFoundException, IOException {

        FileWriter outputFile = new FileWriter("results.txt");

        for (String value : values) {
            outputFile.write(value + " : ");

            outputFile.write(valueToRating.get(value) + System.lineSeparator());
        }

        outputFile.close();
    }

    // Private helper method that starts a round of voting in which the user rates
    // all the values and saves them.
    // Parameters:
    //  - Map<String, Integer> valueToRating - every value matched to it's rating
    //  - Scanner input - gets user input for rating each value
    private static void startVoteRound(Map<String, Integer> valueToRating, Scanner input) {
        System.out.println("Give each value a rating from 1-10: ");
        System.out.println("Type \"q\" to quit");

        for (String value : valueToRating.keySet()) {
            if (valueToRating.get(value) != 0) {
                System.out.print(value + ": ");
                String textInput = input.nextLine();
                if (textInput.equals("q")) {
                    return;
                }
                int rating = Integer.parseInt(textInput);
                if (rating > 10 || rating < 1) {
                    System.out.println("That's not between 1 and 10. Try again: ");
                    rating = input.nextInt();
                }
                valueToRating.put(value, rating);
                System.out.println();
            }
        }
    }
}
