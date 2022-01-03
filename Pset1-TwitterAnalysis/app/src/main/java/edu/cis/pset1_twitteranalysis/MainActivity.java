package edu.cis.pset1_twitteranalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import edu.cis.pset1_twitteranalysis.twitter.TwitterController;

public class MainActivity extends AppCompatActivity
{
    TextView outputText;
    EditText inputText ;
    EditText inputTweet;
    TwitterController myC;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This is not great, for extra credit you can fix this so that network calls happen on a different thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        outputText = (TextView) this.findViewById(R.id.outputText);
        inputText = (EditText) this.findViewById(R.id.inputText);
        inputTweet = (EditText) this.findViewById(R.id.inputTweet);
        myC = new TwitterController(this);
    }

    public void testingMethod(View mainScreen)
    {
        String inputVal = inputText.getText().toString();
        result = myC.findUserStats(inputVal);
        System.out.println(result);
        outputText.setText(result);
    }

    public void postingTweet(View mainScreen)
    {
        String tweetText = inputTweet.getText().toString();

        //TODO 1: Tweet something!
        myC.postTweet(tweetText); //this will tweet to your account
        outputText.setText("Done!");
    }
}