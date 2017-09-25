package com.example.admin.jumpnbump;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Obstacle extends GameObject {

    public static final float VELOCITY = 1.3f;
    private Bitmap image;

    private long lastDrawNanoTime = -1;
    private GameSurface gameSurface;

    public Obstacle(GameSurface gameSurface, Bitmap image, int x, int yAchse) {
        super(image, x, yAchse);
        this.gameSurface = gameSurface;
        this.image = image;
    }

    public void update() {
        long now = System.nanoTime();
        int newBottom = gameSurface.getHeight() - height;

        if (lastDrawNanoTime == -1) {
            lastDrawNanoTime = now;
        }
        int deltaTime = (int) ((now - lastDrawNanoTime) / 2000000);

        float distance = VELOCITY * deltaTime;
        this.x -= distance;

        if (this.x < 0) {
            this.x = 0;
        }
        else if (this.x > newBottom) {
            this.x = newBottom;
        }

    }

    public void draw(Canvas canvas) {
        Bitmap image = this.image;
        canvas.drawBitmap(image, 4, y, null);
        this.lastDrawNanoTime = System.nanoTime();
    }
}