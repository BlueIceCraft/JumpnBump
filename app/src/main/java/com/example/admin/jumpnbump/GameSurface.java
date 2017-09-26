package com.example.admin.jumpnbump;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
    private Random rd;
    private int score;
    private List<Highscore> highscoreList;
    private Context context;
    private int counter;

    public GameSurface(Context context)  {
        super(context);
        setFocusable(true);
        getHolder().addCallback(this);
        rd = new Random();
        obsList = new ArrayList<>();
        this.context = context;
        highscoreList = SaveFileUtils.readScoresFromFile(context);
    }

    public void update()  {
        ball.update();
        for (Obstacle obs : obsList) {
            obs.update();
        }
        counter++;
        if(counter % 20 == 0) {
            score += 5;
        }

        int p1y = ball.getY();
        int p1x = ball.getX();
        int p1h = ball.getHeight();
        int p1w = ball.getWidth();
        Obstacle obs1 = obsList.get(0);
        Obstacle obs2 = obsList.get(1);

        int r1y = obs1.getY();
        int r1x = obs1.getX();
        int r1h = obs1.getHeight();
        int r1w = obs1.getWidth();
        int r2y = obs2.getY();
        int r2x = obs2.getX();
        int r2h = obs2.getHeight();
        int r2w = obs2.getWidth();

        if (r2x <= p1x + p1w && r2x >= p1x || r2x + r2w <= p1x + p1w && r2x + r2w >= p1x){

            if (p1y + p1h >= r2y || p1y <= r1h){
                surfaceDestroyed(getHolder());
            }
        }
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        ball.draw(canvas);
        for (Obstacle obs : obsList) {
            obs.draw(canvas);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap ballBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ball);
        ball = new Ball(this, ballBitmap, 0);

        Bitmap obstacleBlueBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.obstacle_blue);
        Bitmap obstacleGreenBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.obstacle_green);

        int x = this.getWidth() + (int) (200 + rd.nextInt(200) * 2f);

        Obstacle obstacleBlue = new Obstacle(this, obstacleBlueBitmap, x, 0, 2f);
        Obstacle obstacleGreen = new Obstacle(this, obstacleGreenBitmap, x, getHeight() - obstacleGreenBitmap.getHeight(), 2f);
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
        Highscore highscore = new Highscore(new Date(), score);
        if(highscoreList.size() == 0) {
            highscoreList.add(highscore);
        }else {
            boolean add = false;
            for(Highscore hs : highscoreList) {
                if(score > hs.getScore()) {
                   add = true;
                }
            }
            if(add) {
                highscoreList.add(highscore);
            }
        }
        SaveFileUtils.writeScoresToFile(context, highscoreList);

        Intent gameOverIntent = new Intent(context, GameOverActivity.class);
        gameOverIntent.putExtra("score", score);
        context.startActivity(gameOverIntent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        ball.jump();
        return true;
    }
}
