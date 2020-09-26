// **********************************************************************************************
// Program: SMSTranslator.java
// Name: Naumaan Hussain
// VUnetID: hussainm
// Email: naumaan.m.hussain@vaderbilt.edu
// Class: CS 1101 - Vanderbilt University
// Section: 002 MWF 2:10-3:00
// Date: 02.4.2020
// Honor statement: I attest that I understand the honor code for this class and have neither  
//                  given nor received any unauthorized aid on this assignment.
//
// Program Description:
//    The program accepts a certain month and day from the user, determines if it is a valid 
//    date, and then determines what season the inputted date is in. 
//
// **********************************************************************************************

import java.io.IOException;
import java.util.Scanner;

public class TweetGame {
    public static void main(String[] args) throws IOException {
        Scanner scnr = new Scanner(System.in);

        System.out.println("Pick two users from Twitter to play the game with! The first user's " +
                "twitter handle is: ");
        String user1 = scnr.nextLine();
        System.out.println("The second user's twitter handle is: ");
        String user2 = scnr.nextLine();
        StringBuffer elonResponse = TweetGenerator.getTweets(user1);
        StringBuffer kanyeResponse = TweetGenerator.getTweets(user2);

        String allTweets =
                TweetGenerator.toString(elonResponse) + TweetGenerator.toString(kanyeResponse);

        System.out.println("\nDirections: \nBelow are a list of tweets from Elon Musk and " +
                        "Kanye West. A random tweet from the list will be generated, " +
                        "and your job is to determine who tweeted each tweet.\nHere is the list " +
                "of tweets:\n" + allTweets);
        playGame(allTweets, scnr);
    }

    public static void playGame (String allTweets, Scanner scnr) throws IOException {
        boolean doAgain = true;
        int numCorrectGuesses = 0;
        int totalGuesses = 0;

        while (doAgain) {
            String randomTweet = TweetGenerator.getRandomTweet(allTweets);
            System.out.println("Here is a random tweet: " + randomTweet);

            System.out.println("Enter your guess: ");
            String userGuess = scnr.nextLine();
            totalGuesses++;

            if (TweetGenerator.guessedCorrectly(userGuess, randomTweet)) {
                numCorrectGuesses++;
            }

            System.out.println("Would you like to play the game again (y/n)");
            String playAgain = scnr.nextLine();

            doAgain = playAgain.equalsIgnoreCase("Y");
        }

        getGameStatistics(numCorrectGuesses, totalGuesses);
    }

    public static void getGameStatistics (int numCorrectGuesses, int totalGuesses) {
        double gameStats = ((double) numCorrectGuesses / totalGuesses) * 100;
        System.out.println("\nGame Statistics:\nYou got " + gameStats + "%!");
    }
}
