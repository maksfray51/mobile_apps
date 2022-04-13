package ru.mirea.poltavets.loadermanager;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MyLoader extends AsyncTaskLoader<String> {
    private String editText;
    public static final String ARG_WORD = "word";

    public MyLoader(@NonNull Context context, Bundle args) {
        super(context);
        if (args != null)
            editText = args.getString(ARG_WORD);
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
    @Override
    public String loadInBackground() {
        StringBuilder newLine = new StringBuilder();
        ArrayList<String> wordInChars = new ArrayList<>(Arrays.asList(editText.split("")));

        for (int i = 0; i < editText.length(); i++) {
            int k = new Random().nextInt(wordInChars.size());
            if (k == 0) k ++;
            newLine.append(wordInChars.get(k));
            wordInChars.remove(k);
        }

        return newLine.toString();
    }
}
