package mtom.jumpnbump.objects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import mtom.jumpnbump.game.GameSurface;

public class Obstacle extends GameObject {
    private float speed;
    private int screenWidth;

    public Obstacle(GameSurface gameSurface, Bitmap image, int x, int y, float speed) {
        super(image, x, y);
        this.image = image;
        this.speed = speed;
        screenWidth = gameSurface.getWidth();
    }

    public void update() {
        this.x -= speed;
        if (x < 0 - getWidth()) {
            x = screenWidth + getWidth();
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }
}