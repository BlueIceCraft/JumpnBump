package com.example.admin.jumpnbump;

import java.util.Date;

public class Highscore {

    private Date date;
    private int score;

    public Highscore(Date date, int score) {
        this.date = date;
        this.score = score;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return score + " : " +  date.toString();
    }
}
