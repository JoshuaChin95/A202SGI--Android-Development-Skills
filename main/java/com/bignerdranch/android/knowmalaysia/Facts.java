package com.bignerdranch.android.knowmalaysia;

public class Facts {
    private int mFactResId;
    private boolean mTrueAnswer;

    public Facts (int FactResId, boolean trueAnswer){
        mFactResId = FactResId;
        mTrueAnswer = trueAnswer;
    }

    public int getFactResId() {
        return mFactResId;
    }

    public void setFactResId(int factResId) {
        mFactResId = factResId;
    }

    public boolean isTrueAnswer() {
        return mTrueAnswer;
    }

    public void setTrueAnswer(boolean trueAnswer) {
        mTrueAnswer = trueAnswer;
    }
}
