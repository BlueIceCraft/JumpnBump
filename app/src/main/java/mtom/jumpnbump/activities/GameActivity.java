package mtom.jumpnbump.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.admin.jumpnbump.R;

import mtom.jumpnbump.utilities.GameFinishedListener;
import mtom.jumpnbump.game.GameSurface;
import mtom.jumpnbump.objects.Highscore;
import mtom.jumpnbump.utilities.SaveFileUtils;

import java.util.List;

public class GameActivity extends Activity implements GameFinishedListener {

    private List<Highscore> highscoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        highscoreList = SaveFileUtils.readScoresFromFile(this);

        GameSurface gameSurface = new GameSurface(this);
        gameSurface.setGameFinishedListener(this);

        setContentView(gameSurface);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent mainIntent = new Intent(GameActivity.this, MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(mainIntent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onGameFinished(Highscore highscore) {
        int highestScore = 0;
        int score = highscore.getScore();
        for(Highscore hs : highscoreList) {
            if(hs.getScore() > highestScore) {
                highestScore = hs.getScore();
            }
        }
        if(score > highestScore) {
            highscoreList.add(highscore);
            SaveFileUtils.writeScoresToFile(getBaseContext(), highscoreList);
        }

        Intent gameOverIntent = new Intent(GameActivity.this, GameOverActivity.class);
        gameOverIntent.putExtra("score", score);
        gameOverIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(gameOverIntent);
    }
}
