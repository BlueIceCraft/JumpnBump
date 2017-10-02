package com.example.admin.jumpnbump;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ball extends GameObject {
    private static final int X_AXIS = 250;
    private static final float GRAVITY = 0.5f;
    private static final float JUMP_FORCE = -25f;
    private static final float AIRTIME_MULTIPLIER = 2f;
    private static final float DOUBLEJUMP_MULTIPLIER = 0.8f;
    private static final float BOUNCE_MULTIPLIER = -0.5F;
    private Bitmap image;
    private boolean onGround;
    private boolean doubleJump;
    private GameSurface gameSurface;
    private float velocityY;
    private int newBottom;

    public Ball(GameSurface gameSurface, Bitmap image, int y) {
        super(image,X_AXIS, y);
        this.gameSurface= gameSurface;
        this.image = image;
        this.newBottom = gameSurface.getHeight() - getHeight();
    }

    public void update()  {
        velocityY += GRAVITY * AIRTIME_MULTIPLIER;
        y += velocityY * AIRTIME_MULTIPLIER;

        if(y < 0 ) {
            y = 0;
        } else if(y > newBottom)  {
            y = newBottom;
            velocityY = velocityY * BOUNCE_MULTIPLIER;
            if(velocityY < 1 && velocityY > -1) {
                velocityY = 0;
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

