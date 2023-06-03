package com.example.mycreation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

public class FlappySmileActivity extends Activity{

    GameView gameView;

    int money = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(gameView = new GameView(this));
    }

    @Override
    public void onBackPressed() {
        gameView.exit_from_game();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("money", gameView.points);
        System.out.println("SavedInstate");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        gameView.points = savedInstanceState.getInt("money");
        System.out.println("RestoredInstance");
    }
}