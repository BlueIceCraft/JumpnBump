package com.example.admin.jumpnbump;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameRunnable gameRunnable;
    private Thread gameThread;
    private Ball ball;
    private List<Obstacle> obsList;
    private List<Highscore> highscoreList;
    private int score;
    private Context context;
    private int counter;
    private long lastTouch;
    private float obsVelocity;

    public GameSurface(Context context) {
        super(context);
        init(context);
    }

    public GameSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameSurface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public void init(Context context)  {
        setFocusable(true);
        getHolder().addCallback(this);
        this.context = context;
        obsList = new ArrayList<>();
        highscoreList = SaveFileUtils.readScoresFromFile(context);
        lastTouch = System.nanoTime();
        obsVelocity = 20f;
        score = 0;
    }

    public void update()  {
        for (Obstacle obs : obsList) {
            obs.update();
        }
        ball.update();

        counter++;
        if(counter % 20 == 0) {
            score += 5;
        }

        for (Obstacle obstacle : obsList) {
            if(ball.getX() <= obstacle.getX() + obstacle.getWidth() && ball.getX() + ball.getWidth() >= obstacle.getX() &&
                    ball.getY() <= obstacle.getY() + obstacle.getHeight() && ball.getY() + ball.getHeight() >= obstacle.getY()) {

                Highscore highscore = new Highscore(new Date(), score);
                int highestScore = 0;
                for(Highscore hs : highscoreList) {
                    if(hs.getScore() > highestScore) {
                        highestScore = hs.getScore();
                    }
                }
                if(score > highestScore) {
                    highscoreList.add(highscore);
                    SaveFileUtils.writeScoresToFile(context, highscoreList);
                }

                Intent gameOverIntent = new Intent(context, GameOverActivity.class);
                gameOverIntent.putExtra("score", score);
                context.startActivity(gameOverIntent);

                surfaceDestroyed(getHolder());
            }
        }
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        for (Obstacle obs : obsList) {
            obs.draw(canvas);
        }
        ball.draw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap ballBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        ball = new Ball(this, ballBitmap, getHeight());

        Bitmap obstacleBlueBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.obstacle_blue);
        Bitmap obstacleGreenBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.obstacle_green);

        int x1 = getWidth() * 1/3;
        int x2 = (getWidth() + obstacleGreenBitmap.getWidth()) * 5/6;

        int y1 = 0;
        int y2 = getHeight() - obstacleGreenBitmap.getHeight();

        Obstacle obstacleBlue = new Obstacle(this, obstacleBlueBitmap, x1, y1, obsVelocity);
        Obstacle obstacleGreen = new Obstacle(this, obstacleGreenBitmap, x2, y2, obsVelocity);
        Collections.addAll(obsList, obstacleBlue, obstacleGreen);

        gameRunnable = new GameRunnable(this, holder);
        gameRunnable.setRunning(true);

        gameThread = new Thread(gameRunnable);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameRunnable.setRunning(false);
        try {
            gameThread.interrupt();
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            long currentTime = System.nanoTime() - lastTouch;
            if(currentTime / 100000000 > 2) {
                ball.jump();
                lastTouch = System.nanoTime();
            }
        }
        return true;
    }
}
