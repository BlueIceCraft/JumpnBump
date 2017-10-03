package mtom.jumpnbump.utilities;

import mtom.jumpnbump.objects.Highscore;

import java.util.List;

public class Highscores {

    private List<Highscore> highscoreList;

    public Highscores(List<Highscore> highscoreList) {
        this.highscoreList = highscoreList;
    }

    public List<Highscore> getHighscoreList() {
        return highscoreList;
    }

    public void setHighscoreList(List<Highscore> highscoreList) {
        this.highscoreList = highscoreList;
    }
}
