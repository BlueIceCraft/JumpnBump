package com.example.admin.jumpnbump;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ball extends GameObject {
    private static final int X_AXIS = 250;
    public static final float VELOCITY = 0.5f;
    private Bitmap image;
    private boolean canJump = false;

    private int yAcc = 1;
    private long lastDrawNanoTime = -1;
    private GameSurface gameSurface;

    public Ball(GameSurface gameSurface, Bitmap image, int y) {
        super(image,X_AXIS, y);
        this.gameSurface= gameSurface;
        this.image = image;
    }

    public void update()  {
        long now = System.nanoTime();
        int newHeight = gameSurface.getHeight() - height;

        if(lastDrawNanoTime == -1) {
            lastDrawNanoTime= now;
        }
        int deltaTime = (int) ((now - lastDrawNanoTime)/ 1000000);
        float distance = VELOCITY * deltaTime;
        double movingVectorLength = Math.sqrt(yAcc*yAcc);

        if(canJump) {
            this.y += (int)(distance* yAcc / movingVectorLength);
            canJump = false;
        }

        if(this.y < 0 )  {
            this.y = 0;
            this.yAcc = - this.yAcc;
        } else if(this.y > this.gameSurface.getHeight()- height)  {
            this.y= this.gameSurface.getHeight()- height;
            this.yAcc = - this.yAcc ;
        }
    }

    public void draw(Canvas canvas)  {
        Bitmap image = this.image;
        canvas.drawBitmap(image,X_AXIS, y, null);
        this.lastDrawNanoTime= System.nanoTime();
    }

    public void jump() {
        canJump = true;
    }
}

