package com.example.admin.jumpnbump;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import static android.content.Context.SENSOR_SERVICE;

public class Ball extends GameObject {
    private static final int X_AXIS = 250;
    public static final float GRAVITY = 0.15f;
    private Bitmap image;
    private boolean onGround;
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
        int newBottom = gameSurface.getHeight() - getHeight();

        if(lastDrawNanoTime == -1) {
            lastDrawNanoTime = now;
        }
        int deltaTime = (int) ((now - lastDrawNanoTime)/ 1000000);

        velocityY += GRAVITY * deltaTime * 0.25;
        y += velocityY * deltaTime * 0.25;


        if(y < 0 )  {
            y = 0;
        }

        else if(y > newBottom)  {
            y = newBottom;
            onGround = true;
        }
    }

    public void draw(Canvas canvas)  {
        Bitmap image = this.image;
        canvas.drawBitmap(image,X_AXIS, y, null);
        this.lastDrawNanoTime= System.nanoTime();
    }

    public void jump() {
        float jumpForce = -20f;
        if(onGround) {
            velocityY = jumpForce;
            onGround = false;
        }
    }
}

