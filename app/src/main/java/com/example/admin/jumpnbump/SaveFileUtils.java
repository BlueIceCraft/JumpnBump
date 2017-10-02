package com.example.admin.jumpnbump;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class SaveFileUtils {

    private static Gson gson = new Gson();
    private static String filename = "highscores.json";

    private SaveFileUtils() {

    }

    public static List<Highscore> readScoresFromFile(Context context) {
        File saveFile = new File(context.getFilesDir(), filename);
        List<Highscore> highscoreList = new ArrayList<>();
        JsonReader jr;
        try {
            jr = new JsonReader(new FileReader(saveFile));
            Highscores hs = gson.fromJson(jr, Highscores.class);
            if(hs != null) {
                highscoreList = hs.getHighscoreList();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return highscoreList;
    }

    public static void writeScoresToFile(Context context, List<Highscore> highscores) {
        File saveFile = new File(context.getFilesDir(), filename);
        JsonWriter jw;
        try {
            jw = new JsonWriter(new FileWriter(saveFile));
            Highscores hs = new Highscores(highscores);
            gson.toJson(hs, Highscores.class, jw);
            jw.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static File getSaveFile(Context context) {
        File saveFile = new File(context.getFilesDir(), filename);
        return saveFile;
    }
}
