package src;

import java.util.Scanner;

class Score {
    private int noOfSentences = 0;
    private int noOfWords = 0;
    private int noOfCharacters = 0;
    private int noOfSyllables = 0;
    private int noOfPolysyllablesWords = 0;
    private String fileName = "";

    Score(final java.io.File file) {
        try {
            fileName = file.getName();
            parseFile(new Scanner(file));
            printResults();
        } catch (final Exception e) {
            System.out.println("ERROR: Unable to open file : " + fileName);
        }
    }

    private void parseFile(final Scanner scanner) {
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine().trim();
            final String[] sentences = line.split("[.!?]+");
            final String[] words = String.join("", sentences).split("[\\s]+");
            final String[] characters = String.join("", words).split("");
            noOfCharacters += characters.length;
            noOfWords += words.length;
            noOfSentences += sentences.length;
            for (String word : words) {
                int noOfSyllablesInThisWord = countSyllables(word);
                noOfSyllables += noOfSyllablesInThisWord;
                if (noOfSyllablesInThisWord > 2)
                    noOfPolysyllablesWords += 1;
            }
        }
    }

    private int countSyllables(String phrase) {
        StringBuilder word = new StringBuilder(phrase.toLowerCase());
        int noOfVowels = 0;
        if (word.length() == 0)
            return 0;
        while (word.charAt(word.length() - 1) == 'e')
            word.deleteCharAt(word.length() - 1);
        for (int i = 0; i < word.length(); i++)
            if (isVowel(word.charAt(i)))
                noOfVowels++;
        for (int i = 0; i < word.length() - 1; i++)
            if (isVowel(word.charAt(i)) && isVowel(word.charAt(i + 1)))
                noOfVowels--;
        if (noOfVowels == 0)
            noOfVowels = 1;
        return noOfVowels;
    }

    private boolean isVowel(char ch) {
        switch (ch) {
            case 'a':
            case 'e':
            case 'i':
            case 'o':
            case 'u':
                return true;
            default:
                return false;
        }
    }

    private double calcScoreARI() {
        int words = noOfWords - noOfSentences;
        return 4.71 * noOfCharacters / words + 0.5 * words / noOfSentences - 21.43;
    }

    private double calcScoreFK() {
        return 0.39 * noOfWords / noOfSentences + 11.8 * noOfSyllables / noOfWords - 15.59;
    }

    private double calcScoreSMOG() {
        return 1.043 * Math.sqrt((double) noOfPolysyllablesWords * 30 / noOfSentences) + 3.1291;
    }

    private double calcScoreCL() {
        return 5.88 * noOfCharacters / noOfWords - 29.6 * noOfSentences / noOfWords - 15.8;
    }

    private void printResults() {
        double scoreCL = calcScoreCL();
        double scoreARI = calcScoreARI();
        double scoreSMOG = calcScoreSMOG();
        double scoreFK = calcScoreFK();
        int scoreAVG = (int) Math.round((scoreCL + scoreARI + scoreSMOG + scoreFK) / 4);
        System.out.println("File: " + fileName);
        System.out.println("Words: " + noOfWords);
        System.out.println("Sentences: " + noOfSentences);
        System.out.println("Characters: " + noOfCharacters);
        System.out.println("Syllables: " + noOfSyllables);
        System.out.println("Polysyllables: " + noOfPolysyllablesWords);
        System.out.printf("Coleman–Liau index: %.2f \n", scoreCL);
        System.out.printf("Automated Readability Index: %.2f \n", scoreARI);
        System.out.printf("Simple Measure of Gobbledygook: %.2f \n", scoreSMOG);
        System.out.printf("Flesch–Kincaid readability tests: %.2f \n", scoreFK);
        if (scoreAVG <= 0)
            System.out.println("It can be understood by a Kindergarten Child");
        else if (scoreAVG >= 14)
            System.out.println("It can be understood by a Professor");
        else if (scoreAVG == 13)
            System.out.println("It can be understood by a College Student");
        else
            System.out.println("It can be understood by a Student of class " + scoreAVG);
        System.out.println();
    }
}
