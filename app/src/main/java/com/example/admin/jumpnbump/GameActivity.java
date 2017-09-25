package com.example.admin.jumpnbump;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

public class GameActivity extends Activity {

    private GameSurface gameSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameSurface = new GameSurface(this);
        setContentView(gameSurface);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            Intent mainIntent = new Intent(GameActivity.this, MainActivity.class);
            startActivity(mainIntent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
