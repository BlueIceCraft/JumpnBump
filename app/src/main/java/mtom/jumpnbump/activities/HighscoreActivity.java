package mtom.jumpnbump.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;

import mtom.jumpnbump.objects.Highscore;
import mtom.jumpnbump.utilities.HighscoreAdapter;
import com.example.admin.jumpnbump.R;
import mtom.jumpnbump.utilities.SaveFileUtils;

import java.util.List;

public class HighscoreActivity extends Activity {

    private ListView mainListView;
    private List<Highscore> highscoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        mainListView = findViewById(R.id.mainListView);
        highscoreList = SaveFileUtils.readScoresFromFile(this);
        mainListView.setAdapter(new HighscoreAdapter(this, highscoreList));
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent mainIntent = new Intent(HighscoreActivity.this, MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(mainIntent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
