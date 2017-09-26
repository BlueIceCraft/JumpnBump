package com.example.admin.jumpnbump;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private Button startGameButton;
    private Button highscoresButton;
    private List<Highscore> highscores;
    private String filename = "highscores.json";
    private File saveFile;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        saveFile = new File(getApplicationContext().getFilesDir(), filename);
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
                //Start Game
                Intent gameIntent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(gameIntent);
            }
        });

        highscoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Show Highscore-View
                Intent highscoreIntent = new Intent(MainActivity.this, HighscoreActivity.class);
                startActivity(highscoreIntent);
            }
        });
    }
}
