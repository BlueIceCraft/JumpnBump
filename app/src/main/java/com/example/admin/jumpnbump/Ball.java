package com.example.admin.jumpnbump;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ball extends GameObject {
    private static final int X_AXIS = 250;
    public static final float GRAVITY = 0.20f;
    private Bitmap image;
    private boolean onGround = false;
    private long lastDrawNanoTime = -1;
    private GameSurface gameSurface;
    private float velocityY = 0;

    public Ball(GameSurface gameSurface, Bitmap image, int y) {
        super(image,X_AXIS, y);
        this.gameSurface= gameSurface;
        this.image = image;
    }

    public void update()  {
        long now = System.nanoTime();
        int newBottom = gameSurface.getHeight() - height;

        if(lastDrawNanoTime == -1) {
            lastDrawNanoTime = now;
        }
        int deltaTime = (int) ((now - lastDrawNanoTime)/ 2000000);
        System.out.println(deltaTime);

        velocityY += GRAVITY * deltaTime;
        y += velocityY * deltaTime;


        if(y < 0 )  {
            y = 0;
            //System.out.println("trying to go above");
        }

        else if(y > newBottom)  {
            y = newBottom;
            onGround = true;
            //System.out.println("trying to go below");
        }
    }

    public void draw(Canvas canvas)  {
        Bitmap image = this.image;
        canvas.drawBitmap(image,X_AXIS, y, null);
        this.lastDrawNanoTime= System.nanoTime();
    }

    public void jump() {
        if(onGround) {
            velocityY = -15f;
            System.out.println("trying to jump");
            onGround = false;
        }
    }
}

