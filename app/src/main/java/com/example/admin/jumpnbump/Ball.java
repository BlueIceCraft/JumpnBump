package com.example.admin.jumpnbump;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ball extends GameObject {
    private static final int X_AXIS = 250;
    private static final float GRAVITY = 0.5f;
    private static final float JUMP_FORCE = -22f;
    private static final float AIRTIME_MULTIPLIER = 0.3f;
    private static final float DOUBLEJUMP_MULTIPLIER = 0.9f;
    private static final float BOUNCE_MULTIPLIER = -0.5F;
    private final int actualBottom;
    private boolean onGround;
    private boolean doubleJump;
    private float velocityY;
    private Bitmap image;
    //private GameSurface gameSurface;



    public Ball(GameSurface gameSurface, Bitmap image, int y) {
        super(image,X_AXIS, y);
        //this.gameSurface = gameSurface;
        this.image = image;
        this.actualBottom = gameSurface.getHeight() - getHeight();
    }

    public void update()  {
        velocityY += GRAVITY * AIRTIME_MULTIPLIER;
        y += velocityY * AIRTIME_MULTIPLIER;

        if(y < 0 ) {
            y = 0;
        }

        if(y > actualBottom)  {
            y = actualBottom - 1;
            if(velocityY < 7f && velocityY > -7f) {
                velocityY = 0;
            } else {
                velocityY = velocityY * BOUNCE_MULTIPLIER;
            }
            onGround = true;
            doubleJump = true;
        }
    }

    public void draw(Canvas canvas)  {
        canvas.drawBitmap(image, X_AXIS, y, null);
    }

    public void jump() {
        if(onGround) {
            onGround = false;
            velocityY = JUMP_FORCE;
        } else if(doubleJump) {
            doubleJump = false;
            velocityY = JUMP_FORCE * DOUBLEJUMP_MULTIPLIER;
        }
    }
}

