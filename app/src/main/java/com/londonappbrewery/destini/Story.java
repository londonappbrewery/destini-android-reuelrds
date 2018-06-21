package com.londonappbrewery.destini;

public class Story {
    private int mStoryIndex;
    private int mAnswerAIndex;
    private int mAnswerBIndex;

    Story(int storyIndex, int answer_1, int answer_2){
        mStoryIndex = storyIndex;
        mAnswerAIndex = answer_1;
        mAnswerBIndex = answer_2;

    }

    public int getStoryIndex() {
        return mStoryIndex;
    }

    public int getAnswerAIndex() {
        return mAnswerAIndex;
    }

    public int getAnswerBIndex() {
        return mAnswerBIndex;
    }
}
