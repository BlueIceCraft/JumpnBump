package com.example.admin.jumpnbump;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.List;

public class HighscoreActivity extends Activity {

    private List<Highscore> highscoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        highscoreList = SaveFileUtils.readScoresFromFile(this);
        ListView mainListView = (ListView) findViewById(R.id.mainListView);
        HighscoreAdapter adapter = new HighscoreAdapter(this, highscoreList);
        mainListView.setAdapter(adapter);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            Intent mainIntent = new Intent(HighscoreActivity.this, MainActivity.class);
            startActivity(mainIntent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
