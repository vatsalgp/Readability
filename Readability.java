import java.util.Scanner;
import java.io.File;

public class Readability {
    private static int noOfSentences = 0;
    private static int noOfWords = 0;
    private static int noOfCharacters = 0;
    private static int score = 0;

    public static void main(final String[] args) {
        if (args.length == 0)
            System.out.println("ERROR: No File Path added");
        else {
            File file = new File(args[0]);
            workOnFile(file);
        }
    }

    private static void workOnFile(final File file) {
        try {
            final Scanner scanner = new Scanner(file);
            parseFile(scanner);
            calcScore();
            printResults();
        } catch (final Exception e) {
            System.out.println("ERROR: Unable to open file : " + file.getName());
        }
    }

    private static void parseFile(final Scanner scanner) {
        while (scanner.hasNext()) {
            final String line = scanner.nextLine().trim();
            final String[] sentences = line.split("[.!?]");
            final String[] words = String.join("", sentences).split("\\s");
            final String[] characters = String.join("", words).split("");
            noOfCharacters += characters.length;
            noOfWords += words.length;
            noOfSentences += sentences.length;
        }
        noOfWords -= noOfSentences;
    }

    private static void calcScore() {
        score = (int) Math.ceil(4.71 * noOfCharacters / noOfWords + 0.5 * noOfWords / noOfSentences - 21.43);
    }

    private static void printResults() {
        System.out.println("Words: " + noOfWords);
        System.out.println("Sentences: " + noOfSentences);
        System.out.println("Characters: " + noOfCharacters);
        if (score <= 0)
            System.out.println("It can be understood by a Kindergarten Child");
        else if (score >= 14)
            System.out.println("It can be understood by a Professor");
        else if (score == 13)
            System.out.println("It can be understood by a College Student");
        else
            System.out.println("It can be understood by a Student of class " + score);
    }
}
