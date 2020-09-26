
import java.io.IOException;
import java.util.Scanner;

public class TweetGame {
    public static void main(String[] args) throws IOException {
        Scanner scnr = new Scanner(System.in);

        //allows user to put in any two handles
        System.out.println("Pick two users from Twitter to play the game with! The first user's " +
                "twitter handle is: ");
        String user1 = scnr.nextLine();

        System.out.println("The second user's twitter handle is: ");
        String user2 = scnr.nextLine();

        //gets responses of each twitter user
        StringBuffer firstResponse = TweetGenerator.getTweets(user1);
        StringBuffer secondResponse = TweetGenerator.getTweets(user2);

        //gets a string of all the tweets
        String allTweets =
                TweetGenerator.toString(firstResponse) + TweetGenerator.toString(secondResponse);

        //prints out directions and filtered tweets
        System.out.println("\nDirections: \nBelow are a list of tweets from Elon Musk and " +
                        "Kanye West. A random tweet from the list will be generated, " +
                        "and your job is to determine who tweeted each tweet.\nHere is the list " +
                "of tweets:\n" + allTweets);

        //calls method to play the game
        playGame(allTweets, scnr, user1, user2);
    }

    public static void playGame (String allTweets, Scanner scnr, String user1, String user2)
            throws IOException {
        boolean doAgain = true; //check to see if user would like to play again
        int numCorrectGuesses = 0; //number of correct guesses
        int totalGuesses = 0; //total guesses

        //if the user wants to play again, go through the game again
        while (doAgain) {
            //generate a random tweet
            String randomTweet = TweetGenerator.getRandomTweet(allTweets);
            System.out.println("Here is a random tweet: " + randomTweet);

            //guess and increment total guesses
            System.out.println("Enter the twitter handle you guess: ");
            String userGuess = scnr.nextLine();
            totalGuesses++;

            //if the user guessed correctly, increment the number of correct guesses
            if (TweetGenerator.guessedCorrectly(userGuess, randomTweet, user1, user2)) {
                numCorrectGuesses++;
            }

            //ask to play tha game again
            System.out.println("Would you like to play the game again (y/n)");
            String playAgain = scnr.nextLine();

            doAgain = playAgain.equalsIgnoreCase("Y");
        }

        //get the game statistics
        getGameStatistics(numCorrectGuesses, totalGuesses);
    }

    public static void getGameStatistics (int numCorrectGuesses, int totalGuesses) {
        //calculates the game statistics
        double gameStats = ((double) numCorrectGuesses / totalGuesses) * 100;
        System.out.println("\nGame Statistics:\nYou got " + gameStats + "%!");
    }
}
