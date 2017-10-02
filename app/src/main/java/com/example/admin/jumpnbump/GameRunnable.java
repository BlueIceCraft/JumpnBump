package com.example.admin.jumpnbump;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameRunnable implements Runnable {
    private volatile boolean running;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;
    private int delay;

    public GameRunnable(GameSurface gameSurface, SurfaceHolder surfaceHolder)  {
        this.gameSurface = gameSurface;
        this.surfaceHolder = surfaceHolder;
        this.delay = 10;
        this.running = false;
    }

    @Override
    public void run()  {
        while(running)  {
            gameSurface.update();
            Canvas canvas = surfaceHolder.lockCanvas();
            if(canvas != null) {
                synchronized (canvas) {
                    gameSurface.draw(canvas);
                }
                surfaceHolder.unlockCanvasAndPost(canvas);
            }

            try {
                Thread.sleep(delay);
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setRunning(boolean running)  {
        this.running = running;
    }
}
