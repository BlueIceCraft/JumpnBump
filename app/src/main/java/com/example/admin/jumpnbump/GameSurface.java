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
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    private Ball ball;
    private List<Obstacle> obsList;
    private List<Highscore> highscoreList;
    private int score;
    private Context context;
    private int counter;
    private long lastTouch;

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
        score = 0;
        lastTouch = System.nanoTime();
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

        for (Obstacle obs : obsList) {
            if(ball.getX() > obs.getX() && ball.getX() < obs.getX() + obs.getWidth() && ball.getY() > obs.getY() && ball.getY() < obs.getY() + obs.getHeight()) {

                Highscore highscore = new Highscore(new Date(), score);
                if(highscoreList.size() == 0) {
                    highscoreList.add(highscore);
                }
                else {
                    int highestScore = 0;
                    for(Highscore hs : highscoreList) {
                        if(hs.getScore() > highestScore) {
                            highestScore = hs.getScore();
                        }
                    }
                    if(score > highestScore) {
                        highscoreList.add(highscore);
                    }
                }
                SaveFileUtils.writeScoresToFile(context, highscoreList);

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
        float obsVelocity = 12f;

        Obstacle obstacleBlue = new Obstacle(this, obstacleBlueBitmap, x1, y1, obsVelocity);
        Obstacle obstacleGreen = new Obstacle(this, obstacleGreenBitmap, x2, y2, obsVelocity);
        obsList.add(obstacleBlue);
        obsList.add(obstacleGreen);

        gameThread = new GameThread(this, holder);
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            gameThread.setRunning(false);
            gameThread.interrupt();
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        long currentTime = System.nanoTime() - lastTouch;
        if(currentTime / 100000000 > 2) {
            System.out.println(currentTime);
            ball.jump();
            lastTouch = System.nanoTime();
        }
        return true;
    }
}
