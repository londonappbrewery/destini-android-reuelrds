package com.londonappbrewery.destini;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // Declare member variables
    private TextView mTextView;
    private Button mTopButton;
    private Button mBottomButton;

    // Setting Up database
    private final Story[] mStories = {
            new Story(R.string.T1_Story, R.string.T1_Ans1, R.string.T1_Ans2),
            new Story(R.string.T2_Story, R.string.T2_Ans1, R.string.T2_Ans2),
            new Story(R.string.T3_Story, R.string.T3_Ans1, R.string.T3_Ans2)
    };

    // Hash Tables for storing Next Stories and the Resource id of Answer Strings
    @SuppressLint("UseSparseArrays")
    Map<Integer, Integer> mNextStories = new HashMap<>();
    Map<String, Integer> mStringToResourceId = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking the Views
        mTextView = findViewById(R.id.storyTextView);
        mTopButton = findViewById(R.id.buttonTop);
        mBottomButton = findViewById(R.id.buttonBottom);

        //Creating HashTables
        linkNextStories();
        linkStringAndResourceId();


        // Set up initial story
        mTextView.setText(mStories[0].getStoryIndex());
        mTopButton.setText(mStories[0].getAnswerAIndex());
        mBottomButton.setText(mStories[0].getAnswerBIndex());


        // Set Listener on the Top Button
        mTopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the text on the Button
                Button b = (Button)v;
                String x = b.getText().toString();

                // Get the Resource ID of the text in prev step and pass it to get ID of next Story
                int nextStoryId = mStringToResourceId.get(x);
                drawStory(nextStoryId);
            }
        });

        // Set Listener on the Bottom Button
        mBottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the text on the Button
                Button b = (Button)v;
                String x = b.getText().toString();

                // Get the Resource ID of the text in prev step and pass it to get ID of next Story
                int nextStoryId = mStringToResourceId.get(x);
                drawStory(nextStoryId);
            }
        });
    }


    private void linkNextStories(){

        // Creating HashTable of Stories related to the respective Answers
        mNextStories.put(R.string.T1_Ans1, R.string.T3_Story);
        mNextStories.put(R.string.T1_Ans2, R.string.T2_Story);
        mNextStories.put(R.string.T2_Ans1, R.string.T3_Story);
        mNextStories.put(R.string.T2_Ans2, R.string.T4_End);
        mNextStories.put(R.string.T3_Ans1, R.string.T6_End);
        mNextStories.put(R.string.T3_Ans2, R.string.T5_End);
    }


    private void linkStringAndResourceId(){

        // Creating a HashTable of String on the Buttons and their Resource ID's
        mStringToResourceId.put(getResources().getString(R.string.T1_Ans1), R.string.T1_Ans1);
        mStringToResourceId.put(getResources().getString(R.string.T1_Ans2), R.string.T1_Ans2);
        mStringToResourceId.put(getResources().getString(R.string.T2_Ans1), R.string.T2_Ans1);
        mStringToResourceId.put(getResources().getString(R.string.T2_Ans2), R.string.T2_Ans2);
        mStringToResourceId.put(getResources().getString(R.string.T3_Ans1), R.string.T3_Ans1);
        mStringToResourceId.put(getResources().getString(R.string.T3_Ans2), R.string.T3_Ans2);

    }

    private void drawStory(int answerID){

        // Get Resource ID of next Story
        int id = mNextStories.get(answerID);

        // Find the index of the next story
        int index = 0;
        int i;
        for(i = 0; i<mStories.length; i=i+1){
            if(mStories[i].getStoryIndex() == id) {
                index = i;
                break;
            }
        }

        // if not found Display the end Story and then display Alert Dialog Box also set the two Button's invisible
        if (index == 0){
            mTextView.setText(getResources().getString(id));
            mTopButton.setVisibility(View.INVISIBLE);
            mBottomButton.setVisibility(View.INVISIBLE);
            Handler handler=new Handler();
            Runnable r=new Runnable() {
                public void run() {
                    //what ever you do here will be done after 3 seconds delay.
                    createAlertDialog();
                }
            };
            handler.postDelayed(r, 2000);

        } else {
            mTextView.setText(mStories[index].getStoryIndex());
            mTopButton.setText(mStories[index].getAnswerAIndex());
            mBottomButton.setText(mStories[index].getAnswerBIndex());
        }
    }

    private void createAlertDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Game Over");
        alert.setCancelable(false);
        alert.setMessage("You have Reached the End!");
        alert.setPositiveButton("Play Again!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Reload the app from the beginning
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        alert.setNegativeButton("Close Application", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // close the app
                finish();
            }
        });
        alert.show();
    }
}
