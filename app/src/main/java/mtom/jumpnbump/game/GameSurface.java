package mtom.jumpnbump.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.admin.jumpnbump.R;
import mtom.jumpnbump.objects.Ball;
import mtom.jumpnbump.objects.Highscore;
import mtom.jumpnbump.objects.Obstacle;
import mtom.jumpnbump.utilities.GameFinishedListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private static final float obsVelocity = 3f;
    private GameThread gameThread;
    private Ball ball;
    private List<Obstacle> obsList;
    private GameFinishedListener listener;
    private int score;
    private int counter;

    public GameSurface(Context context)  {
        super(context);
        setFocusable(true);
        getHolder().addCallback(this);
        obsList = new ArrayList<>();
        score = 0;
    }

    public void update() {
        for (Obstacle obs : obsList) {
            obs.update();
        }
        ball.update();

        counter++;
        if(counter % 50 == 0) {
            score += 5;
        }

        for (Obstacle obstacle : obsList) {
            if(ball.getX() <= obstacle.getX() + obstacle.getWidth() && ball.getX() + ball.getWidth() >= obstacle.getX() &&
                    ball.getY() <= obstacle.getY() + obstacle.getHeight() && ball.getY() + ball.getHeight() >= obstacle.getY()) {

                Highscore highscore = new Highscore(new Date(), score);
                listener.onGameFinished(highscore);
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
        Bitmap ballBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        ball = new Ball(this, ballBitmap, getHeight());

        Bitmap obstacleBlueBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.obstacle_blue);
        Bitmap obstacleGreenBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.obstacle_green);

        int x1 = getWidth() * 1/4;
        int x2 = (getWidth() + obstacleGreenBitmap.getWidth()) * 5/6;

        int y1 = 0;
        int y2 = getHeight() - obstacleGreenBitmap.getHeight();

        Obstacle obstacleBlue = new Obstacle(this, obstacleBlueBitmap, x1, y1, obsVelocity);
        Obstacle obstacleGreen = new Obstacle(this, obstacleGreenBitmap, x2, y2, obsVelocity);
        Collections.addAll(obsList, obstacleBlue, obstacleGreen);

        gameThread = new GameThread(this, holder);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.setRunning(false);
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
            ball.jump();
        }
        return true;
    }

    public void setGameFinishedListener(GameFinishedListener listener) {
        this.listener = listener;
    }
}
