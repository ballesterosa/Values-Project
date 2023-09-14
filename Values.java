import java.util.*;
import java.io.*;

public class Values {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Map<String, Integer> valueToRating = new HashMap<>();

        putValuesInMap(valueToRating);

        Scanner input = new Scanner(System.in);
        startVoteRound(valueToRating, input);

        List<String> organizedValues = organizeValues(valueToRating);


        writeOutput(valueToRating, organizedValues);
    }

    public static List<String> organizeValues(Map<String, Integer> map) {
        int size = map.size();
        int currSize = 0;
        int currScore = 10;

        List<String> list = new ArrayList<>();

        while (currSize < size) {
            for (String value : map.keySet()) {
                if (map.get(value) == currScore) {
                    list.add(value);
                    currSize++;
                }
            }
            currScore--;
        }

        return list;
    }

    public static void putValuesInMap(Map<String, Integer> map) throws FileNotFoundException {
        File file = new File("values.txt");
        Scanner input = new Scanner(file);
        while (input.hasNextLine()) {
            map.put(input.nextLine(), 0);
        }
    }

    public static void writeOutput(Map<String, Integer> map, List<String> list) throws FileNotFoundException, IOException {
        FileWriter outputFile = new FileWriter("results.txt");
        for (String value : list) {
            outputFile.write(value + " : ");

            outputFile.write(map.get(value) + "\r");
        }
        outputFile.close();
    }

    public static void startVoteRound(Map<String, Integer> map, Scanner input) {
        System.out.println("Give each value a rating from 1-10: ");

        for (String value : map.keySet()) {
            System.out.print(value + ": ");
            int rating = input.nextInt();
            if (rating > 10 || rating < 1) {
                System.out.println("That's not between 1 and 10. Try again: ");
                rating = input.nextInt();
            }
            map.put(value, rating);
            System.out.println();
        }
    }
}
