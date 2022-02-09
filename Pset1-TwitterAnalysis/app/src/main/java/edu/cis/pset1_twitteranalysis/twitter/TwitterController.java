package edu.cis.pset1_twitteranalysis.twitter;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.io.PrintStream;

public class TwitterController {
    private Twitter twitter;
    private ArrayList<Status> statuses;
    private ArrayList<String> tokens;
    private HashMap<String, Integer> wordCounts;
    ArrayList<String> commonWords;
    private String popularWord;
    private int frequencyMax;
    Context context;

    public TwitterController(Context currContext) {
        context = currContext;

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("BiDgxTunoY7GLqS86f62Q6w1K")
                .setOAuthConsumerSecret("HmqXiuTccATao2QebrFgjYln7XN5XzHaIxCPY3tGBvvHQTDxy8")
                .setOAuthAccessToken("1042321525901152256-xbiLUxES1MDY3NLlV1z9etYGHkLSHn")
                .setOAuthAccessTokenSecret("LTNR4Pl3gkONuCaocGMinpB4GVIeoQFD7Es6DcAZQyNnT");
        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();

        statuses = new ArrayList<Status>();
        tokens = new ArrayList<String>();
        wordCounts = new HashMap<>();
        commonWords = new ArrayList<String>();

        getCommonWords();
        fetchTweets("KingJames");
    }

    /********** PART 1 *********/

    //can be used to get common words from the commonWords txt file
    public void getCommonWords() {

        try {
            AssetManager am = context.getAssets();

            //this file can be found in src/main/assets
            InputStream myFile = am.open("commonWords.txt");
            Scanner sc = new Scanner(myFile);
            while (sc.hasNextLine()) {
                commonWords.add(sc.nextLine());
            }
        } catch (Exception err) {
            Log.d("COMMON_WORDS", err.toString());
        }
    }

    public String postTweet(String tweetText) {
        String statusTextToReturn = "";
        try {
            Status status = twitter.updateStatus(tweetText);
            statusTextToReturn = status.getText();
        } catch (TwitterException e) {
            System.out.println(e.getErrorMessage());
        }
        return statusTextToReturn;
    }

    // Example query with paging and file output.
    private void fetchTweets(String handle) {


        //Create a twitter paging object that will start at page 1 and hole 200 entries per page.
        Paging page = new Paging(1, 200);

        //Use a for loop to set the pages and get the necessary tweets.
        for (int i = 1; i <= 10; i++) {
            page.setPage(i);

            /* Ask for the tweets from twitter and add them all to the statuses ArrayList.
            Because we set the page to receive 200 tweets per page, this should return
            200 tweets every request. */
            try {
                statuses.addAll(twitter.getUserTimeline(handle, page));
            } catch (Exception err) {
                Log.d("fetchTweets", "could not get user timeline");
            }
        }

        //Write to the file a header message. Useful for debugging.
        int numberOfTweetsFound = statuses.size();
        System.out.println("Number of Tweets Found: " + numberOfTweetsFound);

        //Use enhanced for loop to print all the tweets found.
        int count = 1;
        for (Status tweet : statuses) {
//            System.out.println(count + ". " + tweet.getText());
            count++;
        }
    }

    /********** PART 2 *********/

    /*
     * TODO 2: this method splits a whole status into different words. Each word
     * is considered a token. Store each token in the "tokens" arrayList
     * provided. Loop through the "statuses" ArrayList.
     */
    public void splitIntoWords() {
        for (Status status : statuses)
        {
            String result = status.getText();
            String[] allTokens = result.split(" ");

            for (String w : allTokens)
            {
                tokens.add(w);
            }
        }

        /*check if words are stored in tokens*/
//        System.out.println("RESULT IS: " + tokens);
    }


    /*
     * TODO 3: return a word after removing any punctuation and turn to lowercase from it.
     * If the word is "Edwin!!", this method should return "edwin".
     * We'll need this method later on.
     * If the word is a common word, return null
     */
    @SuppressWarnings("unchecked")
    private String cleanOneWord(String word)
    {
        String newWord = word.replaceAll("[^a-zA-Z ]", "");
        newWord = newWord.toLowerCase();
        newWord = newWord.trim();

        if (commonWords.contains(newWord) || newWord.isEmpty())
        {
            return null;
        }
        return newWord;
    }


    /*
     * TODO 4: loop through each word, get a clean version of each word
     * and save the list with only clean words.
     */
    @SuppressWarnings("unchecked")
    private void createListOfCleanWords()
    {
        ArrayList<String> cleanList = new ArrayList<>();

        for (String currWord : tokens)
        {
            String cleanWord = cleanOneWord(currWord);

            if (cleanWord != null)
            {
                cleanList.add(cleanWord);
            }
        }
        tokens = cleanList;
    }

    /*
     * TODO 5: count each clean word using. Use the frequentWords Hashmap.
     */
    @SuppressWarnings("unchecked")
    private void countAllWords()
    {
            for (String wor : tokens)
            {
                /*check whether words in token are clean words*/
//                System.out.println("Tokens: " + wor );

                for (String comWord:commonWords)
                {
                    if (wor.equals(comWord))
                    {
                        wor = null;
                    }
                }

                if (wor != null)
                {
                    if (wordCounts.containsKey(wor))
                    {
                        wordCounts.put(wor, wordCounts.get(wor) + 1);
                    }
                    else
                    {
                        wordCounts.put(wor, 1);
                    }
                }
            }
        }


    //TODO 6: return the most frequent word's string in any appropriate format
    @SuppressWarnings("unchecked")
    public String getTopWord()
    {
        Map.Entry<String, Integer> maxEntry = null;

        System.out.println("IS there something in wordcounts?");

        for(Map.Entry<String, Integer> entry : wordCounts.entrySet())
        {
            /*check count for each word*/
//            System.out.println("COUNT: " + entry);

            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }
        popularWord = maxEntry.getKey();

        return popularWord;
    }

    //TODO 7: return the most frequent word's count as an integer.
    @SuppressWarnings("unchecked")
    public int getTopWordCount()
    {
        Map.Entry<String, Integer> maxEntry = null;

        for(Map.Entry<String, Integer> entry : wordCounts.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
            {
                maxEntry = entry;
            }
        }

        frequencyMax = maxEntry.getValue();

        return frequencyMax;
    }


    public String findUserStats(String handle)
    {
        /*
         * TODO 8: you put it all together here. Call the functions you
         * finished in TODO's 2-7. They have to be in the correct order for the
         * program to work.
         * Remember to use .clear() method on collections so that
         * consecutive requests don't count words from previous requests.
         */
        fetchTweets(handle);
        splitIntoWords();
        createListOfCleanWords();
        countAllWords();

        /*print the most popular word and count*/
//        System.out.println("MOST POPULAR AND COUNT:" + getTopWord() + " " + getTopWordCount());

        return "The top word is " + getTopWord() + " and it was used " + getTopWordCount() + " times";
    }

    /*********** PART 3 **********/

    //TODO 9: Create your own method that recommends possible teaching candidates.

    // Example: A method that returns 100 tweets from keyword(s).
    public List<Status> searchKeywords(String keywords)
    {
        //Use the Query object from Twitter
        Query query = new Query(keywords);
        query.setCount(100);
        query.setSince("2015-12-1");

        //create an ArrayList to store results, which will be of type Status
        List<Status> searchResults = new ArrayList<>();
        try
        {
            //we try to get the results from twitter
            QueryResult result = twitter.search(query);
            searchResults = result.getTweets();
        }
        catch (TwitterException e)
        {
            //if an error happens, like the connection is interrupted,
            //we print the error and return an empty ArrayList
            e.printStackTrace();
        }
        return searchResults;
    }
}