package com.example.admin.jumpnbump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.Random;

public class Obstacle extends GameObject {

    private Bitmap image;

    private long lastDrawNanoTime = -1;
    private GameSurface gameSurface;
    private Random rd;
    private float speed;

    public Obstacle(GameSurface gameSurface, Bitmap image, int x, int y, float speed) {
        super(image, x, y);
        this.gameSurface = gameSurface;
        this.image = image;
        this.speed = speed;
        rd = new Random();
    }

    public void update() {
        this.x -= speed;
        if (x < 0 - getWidth() - 100) {
            x = gameSurface.getWidth() + 100;
        }
    }

    public void draw(Canvas canvas) {
        Bitmap image = this.image;
        canvas.drawBitmap(image, x, y, null);
        this.lastDrawNanoTime = System.nanoTime();
    }
}