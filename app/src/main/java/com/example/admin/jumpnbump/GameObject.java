package com.example.admin.jumpnbump;

import android.graphics.Bitmap;

public abstract class GameObject {

    private Bitmap image;
    private final int width;
    private final int height;
    private int x;
    private int y;

    public GameObject(Bitmap image, int x, int y)  {

        this.image = image;

        this.x= x;
        this.y= y;

        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public int getX()  {
        return this.x;
    }
    public int getY()  {
        return this.y;
    }


    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }

}