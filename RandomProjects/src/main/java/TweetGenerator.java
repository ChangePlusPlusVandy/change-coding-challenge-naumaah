//had to import these packages to utilize JSON
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import java.util.regex.Pattern;

public class TweetGenerator extends TweetGame{

    public static final int MAX_TWEETS = 3200; //variables I use throughout the program
    public static int numTweets = 0;

    //default constructor
    TweetGenerator() {numTweets = 0;}

    /*
    This method accepts the twitter handle as a parameter and makes the JSON response
    for that user's tweets.
     */
    public static StringBuffer getTweets (String screenName) throws IOException {
        // Sending get request to get all the tweets up to 3200
        URL url = new URL("https://api.twitter.com/1.1/statuses/user_timeline" +
                ".json?screen_name=" + screenName + "&include_rts=false" +
                "&exclude_replies=true&count=3200");

        /*
        The following code is to enter my bearer token for my twitter account and allow me
        to load tweets on this IDE. A lot of research went into finding out how this
        worked with Java, and I had to use Maaven, as well.
         */
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("Authorization", "Bearer " +
                "AAAAAAAAAAAAAAAAAAAAAKCuHwEAAAAAe4R2oSIm8ZEGCZiUur%2BqGobEZQs%3DaimHjPc8vRUfm0Mn" +
                "FRDtnzW4ies7tjrvjSMZjsdz9LVyBxybgp");

        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod("GET");

        /*
        This was also code I found to allow me to append every JSON and make it into
        one response for that user
         */
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String output;

        StringBuffer response = new StringBuffer();
        while ((output = in.readLine()) != null) {
            response.append(output); //appends everything to one response
        }

        //frees up the memory
        in.close();

        //returns that response
        return response;
    }

    public static String toString (StringBuffer response) {
        //makes a JSON array with the response from the previous method
        JSONArray arrOfTweets = new JSONArray(response.toString());

        String tweetText = ""; //initializes to empty string

        for (int i = 0; i < arrOfTweets.length(); i++) {
            //defines each index of array to the tweet's JSON
            JSONObject eachTweetObject = new JSONObject(arrOfTweets.get(i).toString());

            //if the tweet is not a link and there are less than 3200 tweets, adds tweet to string
            if (!eachTweetObject.get("text").toString().contains("http") && numTweets <=
                    MAX_TWEETS) {
                numTweets++; //increments the number of tweets
                tweetText = tweetText + eachTweetObject.get("text") + "\n"; //concatenates tweets
            }
        }

        //returns a string of all the tweets
        return tweetText;
    }

    public static String getRandomTweet (String tweets) {
        String[] tweetArr;
        //using java.util.regex Pattern
        Pattern pattern = Pattern.compile("\n");
        tweetArr = pattern.split(tweets);

        Random randTweet = new Random();
        int n = randTweet.nextInt(numTweets);

        return tweetArr[n];
    }

    public static boolean guessedCorrectly (String userGuess, String tweetKey) throws IOException {
        boolean guess = false;
        StringBuffer response = TweetGenerator.getTweets("elonmusk");
        JSONArray elonTweets = new JSONArray(response.toString());
        for (int i = 0; i < elonTweets.length(); i++) {
            JSONObject singleTweet = new JSONObject(elonTweets.get(i).toString());
            if (singleTweet.get("text").toString().equals(tweetKey)) {
                guess = userGuess.equalsIgnoreCase("Elon Musk");
            }
        }

        if (!guess) {
            if (userGuess.equalsIgnoreCase("Kanye West")) {
                guess = true;
            }
        }
        return guess;
    }
}