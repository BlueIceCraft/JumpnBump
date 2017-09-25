package com.example.admin.jumpnbump;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Ball ball;
    //private RockObstacle rock1;

    public GameSurface(Context context)  {
        super(context);
        setFocusable(true);
        getHolder().addCallback(this);
    }

    public void update()  {
        ball.update();
        //this.rock1.update();
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        ball.draw(canvas);
        //this.rock1.draw(canvas);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap BallBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        ball = new Ball(this, BallBitmap, 0);

        //Bitmap rockBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.rock_down1);
        //this.rock1 = new RockObstacle(this,rockBitmap1,1000, 0);

        gameThread = new GameThread(this,holder);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.setRunning(false);
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ball.jump();
        return true;
    }
}
