package mtom.jumpnbump.objects;

import android.graphics.Bitmap;

public abstract class GameObject {

    protected Bitmap image;
    protected final int width;
    protected final int height;
    protected int x;
    protected int y;

    public GameObject(Bitmap image, int x, int y)  {
        this.image = image;

        this.x = x;
        this.y = y;

        this.width = image.getWidth();
        this.height = image.getHeight();
    }

    public int getX()  {
        return x;
    }

    public int getY()  {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}