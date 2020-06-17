package src;

import java.util.Scanner;

class Score {
    private int noOfSentences = 0;
    private int noOfWords = 0;
    private int noOfCharacters = 0;
    private int score = 0;
    private String fileName = "";

    Score(final java.io.File file) {
        try {
            fileName = file.getName();
            parseFile(new Scanner(file));
            calcScore();
            printResults();
        } catch (final Exception e) {
            System.out.println("ERROR: Unable to open file : " + fileName);
        }
    }

    private void parseFile(final Scanner scanner) {
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

    private void calcScore() {
        score = (int) Math.ceil(4.71 * noOfCharacters / noOfWords + 0.5 * noOfWords / noOfSentences - 21.43);
    }

    private void printResults() {
        System.out.println();
        System.out.println("File Name: " + fileName);
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
        System.out.println();
    }
}
