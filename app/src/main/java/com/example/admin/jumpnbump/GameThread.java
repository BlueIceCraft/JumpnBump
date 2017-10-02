package com.example.admin.jumpnbump;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private final static int 	MAX_FPS = 60;
    private final static int	MAX_FRAME_SKIPS = 5;
    private final static int	FRAME_PERIOD = 1000 / MAX_FPS;
    private volatile boolean running = true;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;

    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder)  {
        this.gameSurface = gameSurface;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run()  {
        long beginTime;
        long timeDiff;
        int sleepTime;
        int framesSkipped;

        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                synchronized (canvas) {
                    beginTime = System.nanoTime();
                    framesSkipped = 0;
                    gameSurface.update();
                    gameSurface.draw(canvas);
                    timeDiff = System.nanoTime() - beginTime;
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);

                    if (sleepTime > 0) {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        while (framesSkipped < MAX_FRAME_SKIPS) {
                            gameSurface.update();
                            sleepTime += FRAME_PERIOD;
                            framesSkipped++;
                        }
                    }
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
