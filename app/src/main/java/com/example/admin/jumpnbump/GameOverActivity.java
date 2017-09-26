package com.example.admin.jumpnbump;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameOverActivity extends Activity {

    private Button mainmenuButton;
    private Button restartButton;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        textView = (TextView) findViewById(R.id.textView);
        mainmenuButton = (Button) findViewById(R.id.mainMenuButton);
        restartButton = (Button) findViewById(R.id.restartButton);

        int score = getIntent().getExtras().getInt("score");
        textView.setText(getString(R.string.score ) + " " + score);

        mainmenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuButton = new Intent(GameOverActivity.this, MainActivity.class);
                startActivity(mainMenuButton);
            }
        });

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainMenuButton = new Intent(GameOverActivity.this, GameActivity.class);
                startActivity(mainMenuButton);
            }
        });
    }
}
