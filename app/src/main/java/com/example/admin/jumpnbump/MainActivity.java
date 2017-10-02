package com.example.admin.jumpnbump;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private Button startGameButton;
    private Button highscoresButton;
    private List<Highscore> highscores;
    private File saveFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saveFile = SaveFileUtils.getSaveFile(getApplicationContext());
        highscores = new ArrayList<>();

        startGameButton = (Button) findViewById(R.id.startGameButton);
        highscoresButton = (Button) findViewById(R.id.highscoresButton);

        if(saveFile.exists()) {
            highscores = SaveFileUtils.readScoresFromFile(this);
        } else {
            SaveFileUtils.writeScoresToFile(this, highscores);
        }

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(gameIntent);
            }
        });

        highscoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent highscoreIntent = new Intent(MainActivity.this, HighscoreActivity.class);
                startActivity(highscoreIntent);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishAndRemoveTask();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
