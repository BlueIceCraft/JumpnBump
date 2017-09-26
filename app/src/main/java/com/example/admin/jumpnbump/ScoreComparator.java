package com.example.admin.jumpnbump;

import java.util.Comparator;

public class ScoreComparator implements Comparator<Highscore> {

    @Override
    public int compare(Highscore hs1, Highscore hs2) {
        if(hs1.getScore() == hs2.getScore()) {
            return 0;
        }
        if(hs1.getScore() > hs2.getScore()) {
            return -1;
        } else {
            return 1;
        }
    }
}
