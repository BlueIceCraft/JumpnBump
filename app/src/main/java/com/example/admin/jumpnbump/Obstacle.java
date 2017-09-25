package com.example.admin.jumpnbump;

/**
 * Created by admin on 25.09.2017.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Obstacle extends GameObject {

    public static final float VELOCITY = 1.3f;
    private Bitmap image;

    private int movingVectorX = 5;
    private long lastDrawNanoTime = -1;
    private GameSurface gameSurface;

    public Obstacle(GameSurface gameSurface, Bitmap image, int x, int yAchse) {
        super(image, x, yAchse);
        this.gameSurface = gameSurface;
        this.image = image;
    }

    public void update() {
        long now = System.nanoTime();

        if (lastDrawNanoTime == -1) {
            lastDrawNanoTime = now;
        }
        int deltaTime = (int) ((now - lastDrawNanoTime) / 1000000);

        float distance = VELOCITY * deltaTime;
        double movingVectorLength = Math.sqrt(movingVectorX * movingVectorX);
        this.x = x + (int) (distance * movingVectorX / movingVectorLength);

        if (this.x < 0) {
            this.x = 0;
            this.movingVectorX = -this.movingVectorX;
        } else if (this.x > this.gameSurface.getWidth() - width) {
            this.x = this.gameSurface.getWidth() - width;
            this.movingVectorX = -this.movingVectorX;
        }

    }

    public void draw(Canvas canvas) {
        Bitmap image = this.image;
        canvas.drawBitmap(image, 4, y, null);
        this.lastDrawNanoTime = System.nanoTime();
    }
}